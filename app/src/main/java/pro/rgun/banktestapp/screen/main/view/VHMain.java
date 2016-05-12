package pro.rgun.banktestapp.screen.main.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import pro.rgun.banktestapp.R;
import pro.rgun.banktestapp.screen.RecyclerViewEmptySupport;

/**
 * Created by rgun on 25.04.16.
 *
 * <p>ViewHolder activity_main</p>
 */
public class VHMain {

    public static final int layout = R.layout.activity_main;

    public Toolbar toolbar;
    public RecyclerViewEmptySupport recyclerView;
    public View empty;
    public SwipeRefreshLayout swipeRefreshLayout;
    public ProgressBar progressBar;

    public VHMain(AppCompatActivity activity) {
        toolbar = (Toolbar) activity.findViewById(R.id.tool_bar);
        recyclerView = (RecyclerViewEmptySupport) activity.findViewById(R.id.list);
        empty = activity.findViewById(R.id.list_empty);
        swipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swipeRefreshLayout);
        progressBar = (ProgressBar) activity.findViewById(R.id.progressBarToolbar);
    }
}
