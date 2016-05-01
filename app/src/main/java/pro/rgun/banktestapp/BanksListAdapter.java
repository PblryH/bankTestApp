package pro.rgun.banktestapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by rgun on 03.12.15.
 */
public class BanksListAdapter extends ListAdapter<CbrBankModel, BanksListAdapter.BankItemViewHolder> {

    private OnItemClickListener listener;

    public BanksListAdapter() {
        super(new ArrayList<CbrBankModel>());
    }

    @Override
    protected BankItemViewHolder createNormalViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(BankItemViewHolder.layout, parent, false);
        return new BankItemViewHolder(this,v);
    }

    @Override
    protected void bindNormalViewHolder(BankItemViewHolder vh, int position) {
        CbrBankModel model = getItem(position);
        vh.name.setText(model.ShortName);
        vh.roles.setText(model.Bic);
    }

    public CbrBankModel getItem(int position) {
        return getItemAt(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // ============================================================================================
    // INTERFACE
    // ============================================================================================

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T object);
    }

    // ============================================================================================
    // PUBLIC INNER CLASSES
    // ============================================================================================

    public static class BankItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public static final int layout = R.layout.list_view_item;

        private TextView name, roles;
        private BanksListAdapter mMessageListAdapter;

        public BankItemViewHolder(BanksListAdapter messageListAdapter, View itemView) {
            super(itemView);
            mMessageListAdapter = messageListAdapter;
            name = (TextView) itemView.findViewById(R.id.name);
            roles = (TextView) itemView.findViewById(R.id.bic);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mMessageListAdapter.listener != null) {
                itemView.setBackgroundResource(R.drawable.white_touch);
                mMessageListAdapter.listener.onItemClick(v, getLayoutPosition(), mMessageListAdapter.getItem(getLayoutPosition()));
            }
        }
    }

}
