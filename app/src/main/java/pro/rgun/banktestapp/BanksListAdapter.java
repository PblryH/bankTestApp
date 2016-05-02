package pro.rgun.banktestapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by rgun on 03.12.15.
 */
public class BanksListAdapter
        extends ListAdapter<CbrBankModel, BanksListAdapter.BankItemViewHolderForRecyclerView> {

    private OnItemClickListener listener;

    public BanksListAdapter() {
        super(new ArrayList<CbrBankModel>());
    }

    @Override
    protected BankItemViewHolderForRecyclerView createNormalViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(BankItemViewHolder.layout, parent, false);
        return new BankItemViewHolderForRecyclerView(this, v);
    }

    @Override
    protected void bindNormalViewHolder(final BankItemViewHolderForRecyclerView vh, int position) {
        CbrBankModel model = getItem(position);
        vh.name.setText(model.ShortName);
        Application application = (Application) Application.context;
        vh.bic.setText(String.format(application.getString(R.string.list_view_item_bic), model.Bic));
        if(model.isExpanded) {
            vh.expandIcon.setImageDrawable(
                    application.getResources()
                            .getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
            vh.ks.setVisibility(View.VISIBLE);
        } else {
            vh.expandIcon.setImageDrawable(
                    application.getResources()
                            .getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
            vh.ks.setVisibility(View.GONE);
        }
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

    public static class BankItemViewHolderForRecyclerView
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ImageView expandIcon;
        private TextView name, bic, ks;
        private BanksListAdapter mMessageListAdapter;

        public BankItemViewHolderForRecyclerView(BanksListAdapter messageListAdapter, View itemView) {
            super(itemView);
            mMessageListAdapter = messageListAdapter;

            BankItemViewHolder vh = new BankItemViewHolder(itemView);
            expandIcon = vh.expandIcon;
            name = vh.name;
            bic = vh.bic;
            ks = vh.ks;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mMessageListAdapter.listener != null) {
                itemView.setBackgroundResource(R.drawable.white_touch);
                mMessageListAdapter.listener.onItemClick(
                        v, getLayoutPosition(), mMessageListAdapter.getItem(getLayoutPosition()));
            }
        }
    }

    public static class BankItemViewHolder {

        public static final int layout = R.layout.list_view_item;

        public ImageView expandIcon;
        public TextView name, bic, ks;

        public BankItemViewHolder(View itemView) {
            expandIcon = (ImageView) itemView.findViewById(R.id.expandIcon);
            name = (TextView) itemView.findViewById(R.id.name);
            bic = (TextView) itemView.findViewById(R.id.bic);
            ks = (TextView) itemView.findViewById(R.id.ks);
        }
    }

}
