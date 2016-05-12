package pro.rgun.banktestapp.screen.main;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

import pro.rgun.banktestapp.R;
import pro.rgun.banktestapp.model.CbrBankModel;
import pro.rgun.banktestapp.model.HtmlwebruBankModel;
import pro.rgun.banktestapp.model.Record;
import pro.rgun.banktestapp.network.BankDetailRetrofitSpiceRequest;
import pro.rgun.banktestapp.network.BanksListRetrofitSpiceRequest;
import pro.rgun.banktestapp.screen.BaseSpiceActivity;

public class ActivityMain extends BaseSpiceActivity implements SearchView.OnQueryTextListener {


    public static final String BANKS_REQUEST_CACHE_KEY = "banksListRetrofitSpiceRequest";

    private CbrBankModel mCbrBankModel;
    private BanksListRetrofitSpiceRequest banksListRetrofitSpiceRequest;
    private VHMain vh;
    private LinearLayoutManager mLayoutManager;
    private BanksListAdapter mAdapter;
    private MenuItem mActionMenuItem;
    private SearchView mSearchView;
    private ArrayList<ListItemBankModel> listItemBankModels;


    ///////////////////////////////////////////////////////////////////////////
    // Activity Lifecycle
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(VHMain.layout);
        vh = new VHMain(this);
        setSupportActionBar(vh.toolbar);
        banksListRetrofitSpiceRequest = new BanksListRetrofitSpiceRequest();
        initAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadBanksFromCache();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) mActionMenuItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.searchHint));
        mSearchView.setOnQueryTextListener(this);
        return true;
    }


    ///////////////////////////////////////////////////////////////////////////
    // SearchView.OnQueryTextListener impl
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        search(newText);
        return false;
    }

    private void search(String query) {
        final List<ListItemBankModel> list;
        if (query.isEmpty()) {
            list = listItemBankModels;
        } else {
            list = filter(listItemBankModels, query);
        }
        mAdapter.clear();
        if (list != null && !list.isEmpty()) {
            addDataToRecyclerView(list);
            vh.recyclerView.scrollToPosition(0);
        }
    }

    private List<ListItemBankModel> filter(List<ListItemBankModel> models, String query) {
        query = query.toLowerCase();
        final List<ListItemBankModel> filteredModelList = new ArrayList<>();
        if (models != null) {
            for (ListItemBankModel model : models) {
                final String name = model.shortName.toLowerCase();
                final String bic = model.bic;
                if (name.contains(query) || bic.contains(query)) {
                    filteredModelList.add(model);
                }
            }
        }
        return filteredModelList;
    }

    private void initAdapter() {
        vh.recyclerView.setEmptyView(vh.empty);
        mLayoutManager = new LinearLayoutManager(this);
        vh.recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BanksListAdapter();
        vh.recyclerView.setAdapter(mAdapter);
        vh.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                loadBanksFromNetwork();
            }
        });
        mAdapter.setOnItemClickListener(new BanksListAdapter.OnClickListener<ListItemBankModel>() {
            @Override
            public void onItemClick(
                    final BanksListAdapter.BankItemViewHolder vh,
                    final int position,
                    final ListItemBankModel object) {

                if (object.state == ListItemBankModel.State.SHORT) {
                    object.state = ListItemBankModel.State.IN_PROGRESS;
                    object.isExpanded = true;
                } else {
                    object.state = ListItemBankModel.State.SHORT;
                    object.isExpanded = false;
                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN/* && animate*/) {

                    final Animation anim = AnimationUtils.loadAnimation(
                            ActivityMain.this, R.anim.rotate_around_center_point_to_up);

                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (object.state == ListItemBankModel.State.IN_PROGRESS) {
                                loadBankDetail(object, position);
                            }
                            mAdapter.notifyItemChanged(position);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    vh.expandIcon.startAnimation(anim);
                    anim.setFillAfter(!object.isExpanded
                            || object.state != ListItemBankModel.State.IN_PROGRESS);
                    anim.start();

                } else {
                    if (object.state == ListItemBankModel.State.IN_PROGRESS) {
                        loadBankDetail(object, position);
                    }
                    mAdapter.notifyItemChanged(position);
                }
            }
        });
        mAdapter.setOnRetryBtnClickListener(new BanksListAdapter.OnClickListener<ListItemBankModel>() {
            @Override
            public void onItemClick(
                    BanksListAdapter.BankItemViewHolder vh,
                    int position,
                    ListItemBankModel object) {
                loadBankDetail(object, position);
            }
        });
    }

    private void addDataToRecyclerView(List<ListItemBankModel> data) {
        mAdapter.addAll(data);
    }

    private void loadBanksFromNetwork() {
        getSpiceManager().execute(
                banksListRetrofitSpiceRequest,
                BANKS_REQUEST_CACHE_KEY,
                DurationInMillis.ALWAYS_EXPIRED,
                new BanksRequestListener());
        vh.progressBar.setVisibility(View.VISIBLE);
    }

    private void loadBanksFromCache() {
        getSpiceManager().execute(
                banksListRetrofitSpiceRequest,
                BANKS_REQUEST_CACHE_KEY,
                DurationInMillis.ALWAYS_RETURNED,
                new BanksRequestListener());
        vh.progressBar.setVisibility(View.VISIBLE);
    }

    private void loadBankDetail(ListItemBankModel model, int position) {
        getSpiceManager().execute(
                new BankDetailRetrofitSpiceRequest(model.bic),
                "BankDetail" + model.bic,
                DurationInMillis.ALWAYS_RETURNED,
                new BankDetailRequestListener(model, position));
    }

    private ListItemBankModel createCbrBankModel(String name, String bic) {
        ListItemBankModel cbrBankModel = new ListItemBankModel();
        cbrBankModel.shortName = name;
        cbrBankModel.bic = bic;
        return cbrBankModel;
    }

    private void fillList(List<Record> list) {
        mAdapter.clear();
        listItemBankModels = new ArrayList<>();
        for (Record record :
                list) {
            listItemBankModels.add(createCbrBankModel(record.ShortName, record.Bic));
        }
        addDataToRecyclerView(listItemBankModels);
    }

    // ============================================================================================
    // INNER CLASSES
    // ============================================================================================

    /**
     * Листнер запроса получения списка банков
     */
    public final class BanksRequestListener implements RequestListener<CbrBankModel> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            vh.swipeRefreshLayout.setRefreshing(false);
            vh.progressBar.setVisibility(View.GONE);
            Toast.makeText(ActivityMain.this, R.string.networkRequestFailure, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final CbrBankModel result) {
            vh.swipeRefreshLayout.setRefreshing(false);
            vh.progressBar.setVisibility(View.GONE);
            mCbrBankModel = result;
            fillList(mCbrBankModel.recordList);
        }
    }

    /**
     * Листнер запроса получения детальной информации по банку
     */
    public final class BankDetailRequestListener implements RequestListener<HtmlwebruBankModel> {

        private ListItemBankModel model;
        private int position;

        /**
         * Конструктор
         * @param model модель {@link ListItemBankModel} элемента списка по позиции
         * @param position Позиция
         */
        public BankDetailRequestListener(ListItemBankModel model, int position) {
            this.model = model;
            this.position = position;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(ActivityMain.this, R.string.networkRequestFailure, Toast.LENGTH_SHORT).show();
            model.state = ListItemBankModel.State.REPEAT;
            mAdapter.notifyItemChanged(position);
        }

        @Override
        public void onRequestSuccess(final HtmlwebruBankModel result) {
            model.fillFromHtmlwebruBankModel(result);
            model.state = ListItemBankModel.State.FULL;
            mAdapter.notifyItemChanged(position);
        }
    }
}
