package pro.rgun.banktestapp.screen.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import pro.rgun.banktestapp.R;
import pro.rgun.banktestapp.model.CbrBankModel;
import pro.rgun.banktestapp.model.HtmlwebruBankModel;
import pro.rgun.banktestapp.screen.BaseSpiceActivity;
import pro.rgun.banktestapp.screen.main.model.ModelMain;
import pro.rgun.banktestapp.screen.main.model.ModelMainImpl;
import pro.rgun.banktestapp.screen.main.view.ListItemBankModel;
import pro.rgun.banktestapp.screen.main.view.ViewMain;
import pro.rgun.banktestapp.screen.main.view.ViewMainImpl;
import pro.rgun.banktestapp.screen.main.view.ViewMainMenu;
import pro.rgun.banktestapp.screen.main.view.ViewMainMenuImpl;

public class ActivityMain
        extends BaseSpiceActivity
        implements
        ViewMain.Listener,
        ViewMainMenu.Listener,
        SwipeRefreshLayout.OnRefreshListener {

    private ViewMain viewMain;
    private ViewMainMenu viewMainMenu;
    private ModelMain modelMain;


    ///////////////////////////////////////////////////////////////////////////
    // Activity Lifecycle
    ///////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMain = new ViewMainImpl(this);
        viewMain.setListener(this);
        viewMain.setPullToRefreshListener(this);
        modelMain = new ModelMainImpl(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadBanksFromCache();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        viewMainMenu = new ViewMainMenuImpl(this, menu);
        viewMainMenu.setListener(this);
        return true;
    }

    private void loadBanksFromCache(){
        modelMain.loadBanksFromCache(new BanksRequestListener());
        viewMain.showProgress();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ViewMain.Listener impl
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onItemClick(ListItemBankModel model, int position) {
        modelMain.loadBankDetail(model.bic, new BankDetailRequestListener(model,position));
    }

    @Override
    public void onRetryClick(ListItemBankModel model, int position) {
        modelMain.loadBankDetail(model.bic, new BankDetailRequestListener(model,position));
    }

    ///////////////////////////////////////////////////////////////////////////
    // SwipeRefreshLayout.OnRefreshListener impl
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onRefresh() {
        modelMain.loadBanksFromNetwork(new BanksRequestListener());
        viewMain.showProgress();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ViewMainMenu.Listener impl
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onNeedClearList() {
        viewMain.clearList();
    }

    @Override
    public void onNeedAddNewDataToList(List<ListItemBankModel> list) {
        viewMain.addDataToRecyclerView(list);
        viewMain.scrollToPosition(0);
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
            viewMain.hidePullToRefreshProgress();
            viewMain.hideProgress();
            Toast.makeText(ActivityMain.this, R.string.networkRequestFailure, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(final CbrBankModel result) {
            viewMain.hidePullToRefreshProgress();
            viewMain.hideProgress();
            viewMain.fillList(result.recordList);
            viewMainMenu.setListItemBankModels(viewMain.getListItemBankModels());
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
            viewMain.notifyItemChanged(position);
        }

        @Override
        public void onRequestSuccess(final HtmlwebruBankModel result) {
            model.fillFromHtmlwebruBankModel(result);
            model.state = ListItemBankModel.State.FULL;
            viewMain.notifyItemChanged(position);
        }
    }
}
