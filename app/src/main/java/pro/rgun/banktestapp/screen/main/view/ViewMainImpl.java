package pro.rgun.banktestapp.screen.main.view;

import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import pro.rgun.banktestapp.R;
import pro.rgun.banktestapp.model.Record;

/**
 * Created by rgun on 12.05.16.
 */
public class ViewMainImpl implements ViewMain {

    private VHMain vh;
    private AppCompatActivity mActivity;
    private LinearLayoutManager mLayoutManager;
    private BanksListAdapter mAdapter;
    private ArrayList<ListItemBankModel> listItemBankModels;
    private Listener listener;

    public ViewMainImpl(AppCompatActivity activity) {
        mActivity = activity;
        mActivity.setContentView(VHMain.layout);
        vh = new VHMain(activity);
        mActivity.setSupportActionBar(vh.toolbar);
        initAdapter();
    }


    private void initAdapter() {
        vh.recyclerView.setEmptyView(vh.empty);
        mLayoutManager = new LinearLayoutManager(mActivity);
        vh.recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BanksListAdapter();
        vh.recyclerView.setAdapter(mAdapter);
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
                            mActivity, R.anim.rotate_around_center_point_to_up);

                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (object.state == ListItemBankModel.State.IN_PROGRESS) {
                                if (listener != null) {
                                    listener.onItemClick(object, position);
                                }
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
                        if (listener != null) {
                            listener.onItemClick(object, position);
                        }
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

                if (listener != null) {
                    listener.onRetryClick(object, position);
                }
            }
        });
    }

    @Override
    public void addDataToRecyclerView(List<ListItemBankModel> data) {
        mAdapter.addAll(data);
    }

    @Override
    public List<ListItemBankModel> getListItemBankModels() {
        return listItemBankModels;
    }


    private ListItemBankModel createCbrBankModel(String name, String bic) {
        ListItemBankModel cbrBankModel = new ListItemBankModel();
        cbrBankModel.shortName = name;
        cbrBankModel.bic = bic;
        return cbrBankModel;
    }

    @Override
    public void fillList(List<Record> list) {
        mAdapter.clear();
        listItemBankModels = new ArrayList<>();
        for (Record record :
                list) {
            listItemBankModels.add(createCbrBankModel(record.ShortName, record.Bic));
        }
        addDataToRecyclerView(listItemBankModels);
    }

    @Override
    public void hidePullToRefreshProgress() {
        vh.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void clearList() {
        mAdapter.clear();
    }

    @Override
    public void scrollToPosition(int position) {
        vh.recyclerView.scrollToPosition(position);
    }

    @Override
    public void showProgress() {
        vh.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        vh.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setPullToRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        vh.swipeRefreshLayout.setOnRefreshListener(listener);
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void notifyItemChanged(int position) {
        mAdapter.notifyItemChanged(position);
    }
}
