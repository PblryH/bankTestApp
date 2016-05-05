package pro.rgun.banktestapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by rgun on 03.12.15.
 */
public class BanksListAdapter
        extends ListAdapter<ListItemBankModel, BanksListAdapter.BankItemViewHolderForRecyclerView> {

    private OnItemClickListener listener;

    public BanksListAdapter() {
        super(new ArrayList<ListItemBankModel>());
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
        ListItemBankModel model = getItem(position);
        vh.name.setText(model.ShortName);
        Application application = (Application) Application.context;
        vh.bic.setText(String.format(application.getString(R.string.list_view_item_bic), model.Bic));
        if(model.isExpanded) {
            vh.expandIcon.setImageDrawable(
                    application.getResources()
                            .getDrawable(R.drawable.ic_keyboard_arrow_up_24dp));
        } else {
            vh.expandIcon.setImageDrawable(
                    application.getResources()
                            .getDrawable(R.drawable.ic_keyboard_arrow_down_24dp));
        }

        switch (model.state) {
            case SHORT:
                vh.ks.setVisibility(View.GONE);
                vh.address.setVisibility(View.GONE);
                vh.phone.setVisibility(View.GONE);
                vh.loadingBlock.setVisibility(View.GONE);
                vh.retryBlock.setVisibility(View.GONE);
                break;
            case FULL:
                vh.ks.setVisibility(View.VISIBLE);
                vh.address.setVisibility(View.VISIBLE);
                vh.phone.setVisibility(View.VISIBLE);
                vh.loadingBlock.setVisibility(View.GONE);
                vh.retryBlock.setVisibility(View.GONE);
                break;
            case IN_PROGRESS:
                vh.ks.setVisibility(View.GONE);
                vh.address.setVisibility(View.GONE);
                vh.phone.setVisibility(View.GONE);
                vh.loadingBlock.setVisibility(View.VISIBLE);
                vh.retryBlock.setVisibility(View.GONE);
                break;
            case REPEAT:
                vh.ks.setVisibility(View.GONE);
                vh.address.setVisibility(View.GONE);
                vh.phone.setVisibility(View.GONE);
                vh.loadingBlock.setVisibility(View.GONE);
                vh.retryBlock.setVisibility(View.VISIBLE);
                break;
        }

    }

    public ListItemBankModel getItem(int position) {
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
        private TextView name, bic, ks, address, phone;
        private View retryBlock, loadingBlock;
        private Button retryButton;
        private BanksListAdapter mMessageListAdapter;

        public BankItemViewHolderForRecyclerView(BanksListAdapter messageListAdapter, View itemView) {
            super(itemView);
            mMessageListAdapter = messageListAdapter;

            BankItemViewHolder vh = new BankItemViewHolder(itemView);
            expandIcon = vh.expandIcon;
            name = vh.name;
            bic = vh.bic;
            ks = vh.ks;
            address = vh.address;
            phone = vh.phone;
            retryBlock = vh.retryBlock;
            loadingBlock = vh.loadingBlock;
            retryButton = vh.retryButton;
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
        public TextView name, bic, ks, address, phone;
        public View retryBlock, loadingBlock;
        public Button retryButton;

        public BankItemViewHolder(View itemView) {
            expandIcon = (ImageView) itemView.findViewById(R.id.expandIcon);
            name = (TextView) itemView.findViewById(R.id.name);
            bic = (TextView) itemView.findViewById(R.id.bic);
            ks = (TextView) itemView.findViewById(R.id.ks);
            address = (TextView) itemView.findViewById(R.id.address);
            phone = (TextView) itemView.findViewById(R.id.phone);
            loadingBlock = itemView.findViewById(R.id.loadingBlock);
            retryBlock = itemView.findViewById(R.id.retryBlock);
            retryButton = (Button) itemView.findViewById(R.id.retryButton);
        }
    }

}
