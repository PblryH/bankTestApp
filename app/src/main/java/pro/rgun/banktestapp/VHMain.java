package pro.rgun.banktestapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by rgun on 25.04.16.
 */
public class VHMain {

    public static final int layout = R.layout.activity_main;

    public RecyclerViewEmptySupport recyclerView;
    public View empty;
    public SwipeRefreshLayout swipeRefreshLayout;

    public VHMain(AppCompatActivity activity) {
        recyclerView = (RecyclerViewEmptySupport) activity.findViewById(R.id.list);
        empty = activity.findViewById(R.id.list_empty);
        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swipeRefreshLayout);
    }
}
