package pro.rgun.banktestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

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

    private void createMock(){
        ArrayList<CbrBankModel> mockedArray = new ArrayList<>();
        CbrBankModel cbrBankModel = new CbrBankModel();
        cbrBankModel.ShortName = "Банк";
        cbrBankModel.Bic = "34534546";
        mockedArray.add(cbrBankModel);
        mockedArray.add(cbrBankModel);
        mockedArray.add(cbrBankModel);
        mockedArray.add(cbrBankModel);
        mockedArray.add(cbrBankModel);
        mockedArray.add(cbrBankModel);
        addDataToRecyclerView(mockedArray);
    }

    private void initAdapter() {
        vh.recyclerView.setEmptyView(vh.empty);
//        vh.recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
        mLayoutManager = new LinearLayoutManager(this);
        vh.recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BanksListAdapter();
        vh.recyclerView.setAdapter(mAdapter);
    }

    public void addDataToRecyclerView(ArrayList<CbrBankModel> data) {
        mAdapter.addAll(data);
    }
}
