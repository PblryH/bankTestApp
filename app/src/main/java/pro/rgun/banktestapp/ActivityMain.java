package pro.rgun.banktestapp;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {

    private VHMain vh;
    private LinearLayoutManager mLayoutManager;
    private BanksListAdapter mAdapter;

    ///////////////////////////////////////////////////////////////////////////
    // Activity Lifecycle
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(VHMain.layout);
        vh = new VHMain(this);
        initAdapter();
        createMock();
    }

    private ListItemBankModel createCbrBankModel() {
        ListItemBankModel cbrBankModel = new ListItemBankModel();
        cbrBankModel.ShortName = "Банк";
        cbrBankModel.Bic = "34534546";
        return cbrBankModel;
    }

    private void createMock() {
        ArrayList<ListItemBankModel> mockedArray = new ArrayList<>();
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
        mockedArray.add(createCbrBankModel());
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

                object.isExpanded = !object.isExpanded;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

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

    public void addDataToRecyclerView(ArrayList<ListItemBankModel> data) {
        mAdapter.addAll(data);
    }
}
