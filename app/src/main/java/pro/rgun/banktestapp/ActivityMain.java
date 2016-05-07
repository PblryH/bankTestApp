package pro.rgun.banktestapp;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private VHMain vh;
    private LinearLayoutManager mLayoutManager;
    private BanksListAdapter mAdapter;
    private MenuItem mActionMenuItem;
    private SearchView mSearchView;
    private ArrayList<ListItemBankModel> mockedArray;
    private String[] names = new String[]{"новый","второй","третий", "Четвертый", "Четвертый", "шестой"};
    private Integer[] bics = new Integer[]{12312,154676,78679,3444777,5565773,234324};


    ///////////////////////////////////////////////////////////////////////////
    // Activity Lifecycle
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(VHMain.layout);
        vh = new VHMain(this);
        setSupportActionBar(vh.toolbar);
        initAdapter();
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
            list = mockedArray;
        }
        else {
            list = filter(mockedArray, query);
        }
        mAdapter.clear();
        addDataToRecyclerView(list);
        vh.recyclerView.scrollToPosition(0);
    }


    private ListItemBankModel createCbrBankModel(String name, Integer bic) {
        ListItemBankModel cbrBankModel = new ListItemBankModel();
        cbrBankModel.ShortName = name;
        cbrBankModel.Bic = String.valueOf(bic);
        return cbrBankModel;
    }

    private void createMock() {
        mockedArray = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            mockedArray.add(createCbrBankModel(names[i],bics[i]));
        }
        addDataToRecyclerView(mockedArray);
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
        // ...
        createMock();
        // Load complete
        onItemsLoadComplete();
    }


    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

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

}
