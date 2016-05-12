package pro.rgun.banktestapp.screen.main.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import pro.rgun.banktestapp.R;

/**
 * Created by rgun on 12.05.16.
 */
public class ViewMainMenuImpl implements ViewMainMenu, SearchView.OnQueryTextListener {

    private MenuItem mActionMenuItem;
    private SearchView mSearchView;
    private List<ListItemBankModel> listItemBankModels;
    private Listener listener;

    public ViewMainMenuImpl(AppCompatActivity activity, Menu menu) {

        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) mActionMenuItem.getActionView();
        mSearchView.setQueryHint(activity.getString(R.string.searchHint));
        mSearchView.setOnQueryTextListener(this);
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
        if (listener != null) {
            listener.onNeedClearList();
        }
        if (list != null && !list.isEmpty()) {
            if (listener != null) {
                listener.onNeedAddNewDataToList(list);
            }
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


    @Override
    public void setListItemBankModels(List<ListItemBankModel> list) {
        listItemBankModels = list;
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
