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

    private CbrBankModel createCbrBankModel() {
        CbrBankModel cbrBankModel = new CbrBankModel();
        cbrBankModel.ShortName = "Банк";
        cbrBankModel.Bic = "34534546";
        return cbrBankModel;
    }

    private void createMock() {
        ArrayList<CbrBankModel> mockedArray = new ArrayList<>();
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
        mAdapter.setOnItemClickListener(new BanksListAdapter.OnItemClickListener<CbrBankModel>() {
            @Override
            public void onItemClick(View view, final int position, CbrBankModel object) {
                final BanksListAdapter.BankItemViewHolder vh = new BanksListAdapter.BankItemViewHolder(view);

                object.isExpanded = !object.isExpanded;


//                ObjectAnimator anim = ObjectAnimator.ofFloat(vh.expandIcon, "rotation", 0f, 180f);
//                anim.setDuration(2500);                  // Duration in milliseconds
//                anim.setInterpolator(new LinearInterpolator());  // E.g. Linear, Accelerate, Decelerate
//                anim.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        vh.expandIcon.clearAnimation();
//                        mAdapter.notifyItemChanged(position);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                if(object.isExpanded)
//                anim.reverse();
//                anim.start();


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

//                            vh.expandIcon.setImageDrawable(
//                                    ActivityMain.this.getResources()
//                                            .getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    vh.expandIcon.startAnimation(anim);
                    anim.setFillAfter(true);
                    anim.start();                             // Begin the animation

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

    public void addDataToRecyclerView(ArrayList<CbrBankModel> data) {
        mAdapter.addAll(data);
    }
}
