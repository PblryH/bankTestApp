package pro.rgun.banktestapp.screen.main.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pro.rgun.banktestapp.Application;
import pro.rgun.banktestapp.R;
import pro.rgun.banktestapp.screen.ListAdapter;


/**
 * Created by rgun on 03.12.15.
 *
 * <p>Адаптер списка банков</p>
 */
public class BanksListAdapter
        extends ListAdapter<ListItemBankModel, BanksListAdapter.BankItemViewHolder> {

    private OnClickListener
            listener,
            retryBtnClickListener;

    /**
     * Инициализация адаптера
     */
    public BanksListAdapter() {
        super(new ArrayList<ListItemBankModel>());
    }

    @Override
    protected BankItemViewHolder createNormalViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(BankItemViewHolder.layout, parent, false);
        return new BankItemViewHolder(this, v);
    }

    @Override
    protected void bindNormalViewHolder(final BankItemViewHolder vh, final int position) {
        final ListItemBankModel model = getItem(position);
        vh.name.setText(model.shortName);
        Application application = (Application) Application.context;
        vh.bic.setText(String.format(application.getString(R.string.listViewItemBic), model.bic));
        vh.ks.setText(String.format(application.getString(R.string.listViewItemKs), model.ks));
        vh.address.setText(String.format(
                application.getString(R.string.listViewItemAddress),
                model.city,
                model.address));
        vh.phone.setText(String.format(
                application.getString(R.string.listViewItemPhone), model.tel));

        if (model.isExpanded) {
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

        vh.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (retryBtnClickListener != null) {
                    retryBtnClickListener.onItemClick(vh, position, model);
                }
            }
        });

    }

    /**
     * Получение {@link ListItemBankModel} по позиции
     * @param position Позиция элемента
     * @return {@link ListItemBankModel}
     */
    public ListItemBankModel getItem(int position) {
        return getItemAt(position);
    }

    /**
     * Установка листнера на нажатие на элемент списка
     * @param listener {@link OnClickListener}
     */
    public void setOnItemClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Установка листнера на нажатие на кнопку Retry элемента списка
     * @param retryBtnClickListener {@link OnClickListener}
     */
    public void setOnRetryBtnClickListener(OnClickListener retryBtnClickListener) {
        this.retryBtnClickListener = retryBtnClickListener;
    }

    // ============================================================================================
    // INTERFACE
    // ============================================================================================

    /**
     * Интерфейс для отслеживания нажатия на элементе списка
     * @param <T> возвращаемый объект данных
     */
    public interface OnClickListener<T> {
        void onItemClick(BankItemViewHolder vh, int position, T object);
    }


    // ============================================================================================
    // PUBLIC INNER CLASSES
    // ============================================================================================

    /**
     * Вью холдер эелемента списка
     */
    public static class BankItemViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        public static final int layout = R.layout.list_view_item;

        public ImageView expandIcon;
        public TextView name, bic, ks, address, phone;
        public View retryBlock, loadingBlock;
        public Button retryButton;
        private BanksListAdapter mMessageListAdapter;

        public BankItemViewHolder(BanksListAdapter messageListAdapter, View itemView) {
            super(itemView);
            mMessageListAdapter = messageListAdapter;
            expandIcon = (ImageView) itemView.findViewById(R.id.expandIcon);
            name = (TextView) itemView.findViewById(R.id.name);
            bic = (TextView) itemView.findViewById(R.id.bic);
            ks = (TextView) itemView.findViewById(R.id.ks);
            address = (TextView) itemView.findViewById(R.id.address);
            phone = (TextView) itemView.findViewById(R.id.phone);
            loadingBlock = itemView.findViewById(R.id.loadingBlock);
            retryBlock = itemView.findViewById(R.id.retryBlock);
            retryButton = (Button) itemView.findViewById(R.id.retryButton);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mMessageListAdapter.listener != null) {
                itemView.setBackgroundResource(R.drawable.white_touch);
                mMessageListAdapter.listener.onItemClick(
                        this, getLayoutPosition(), mMessageListAdapter.getItem(getLayoutPosition()));
            }
        }
    }

}
