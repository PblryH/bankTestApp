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
import pro.rgun.banktestapp.model.Record;
import pro.rgun.banktestapp.network.BanksListRetrofitSpiceRequest;
import pro.rgun.banktestapp.screen.BaseSpiceActivity;

public class ActivityMain extends BaseSpiceActivity implements SearchView.OnQueryTextListener {


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
        loadBanks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) mActionMenuItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.search_hint));
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

    private void search(String query){
        final List<ListItemBankModel> list;
        if (query.isEmpty()) {
            list = listItemBankModels;
        }
        else {
            list = filter(listItemBankModels, query);
        }
        mAdapter.clear();
        addDataToRecyclerView(list);
        vh.recyclerView.scrollToPosition(0);
    }


    private void loadBanks(){
        getSpiceManager().execute(
                banksListRetrofitSpiceRequest,
                "banksListRetrofitSpiceRequest",
                DurationInMillis.ONE_MINUTE,
                new BanksRequestListener());
    }

    private ListItemBankModel createCbrBankModel(String name, Integer bic) {
        ListItemBankModel cbrBankModel = new ListItemBankModel();
        cbrBankModel.ShortName = name;
        cbrBankModel.Bic = String.valueOf(bic);
        return cbrBankModel;
    }

    private void fillList(List<Record> list){
        mAdapter.clear();
        listItemBankModels = new ArrayList<>();
        for (Record record :
                list) {
            listItemBankModels.add(createCbrBankModel(record.ShortName, record.Bic));
        }
        addDataToRecyclerView(listItemBankModels);
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
                refreshItems();
            }
        });
        mAdapter.setOnItemClickListener(new BanksListAdapter.OnItemClickListener<ListItemBankModel>() {
            @Override
            public void onItemClick(View view, final int position, ListItemBankModel object) {
                final BanksListAdapter.BankItemViewHolder vh = new BanksListAdapter.BankItemViewHolder(view);


                boolean animate = false;
                switch (object.state) {
                    case SHORT:
                        object.state = ListItemBankModel.State.FULL;
                        object.isExpanded = true;
                        animate = true;
                        break;
                    case FULL:
                        object.state = ListItemBankModel.State.IN_PROGRESS;
                        object.isExpanded = true;
                        break;
                    case IN_PROGRESS:
                        object.state = ListItemBankModel.State.REPEAT;
                        object.isExpanded = true;
                        break;
                    case REPEAT:
                        object.state = ListItemBankModel.State.SHORT;
                        object.isExpanded = false;
                        animate = true;
                        break;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && animate) {

                    final Animation anim = AnimationUtils.loadAnimation(
                            ActivityMain.this, R.anim.rotate_around_center_point_to_up);

                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mAdapter.notifyItemChanged(position);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    vh.expandIcon.startAnimation(anim);
                    anim.setFillAfter(true);
                    anim.start();

                } else {
                    mAdapter.notifyItemChanged(position);
                }
            }
        });
    }

    void refreshItems() {
        // Load items
        loadBanks();
    }


    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        fillList(mCbrBankModel.recordList);
        // Stop refresh animation
        vh.swipeRefreshLayout.setRefreshing(false);
    }

    public void addDataToRecyclerView(List<ListItemBankModel> data) {
        mAdapter.addAll(data);
    }


    private List<ListItemBankModel> filter(List<ListItemBankModel> models, String query) {
        query = query.toLowerCase();
        final List<ListItemBankModel> filteredModelList = new ArrayList<>();
        for (ListItemBankModel model : models) {
            final String name = model.ShortName.toLowerCase();
            final String bic = model.Bic;
            if (name.contains(query) || bic.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    // ============================================================================================
    // INNER CLASSES
    // ============================================================================================

    public final class BanksRequestListener implements RequestListener<CbrBankModel> {


        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(ActivityMain.this, "failure", Toast.LENGTH_SHORT).show();
            // Load complete
            onItemsLoadComplete();
        }

        @Override
        public void onRequestSuccess(final CbrBankModel result) {
            mCbrBankModel = result;
            Toast.makeText(ActivityMain.this, "success", Toast.LENGTH_SHORT).show();
            // Load complete
            onItemsLoadComplete();
        }
    }
}
