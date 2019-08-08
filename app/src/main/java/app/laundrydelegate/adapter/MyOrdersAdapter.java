package app.laundrydelegate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrydelegate.R;
import app.laundrydelegate.common.common;
import app.laundrydelegate.databinding.MyOrdersItemBinding;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.viewModels.MyOrdersItemViewModels;
import app.laundrydelegate.views.MyOrderDetailFragment;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    List<OrderRequestDetail> orderDetails;
    Context context;

    public MyOrdersAdapter() {
        orderDetails = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        OrderRequestDetail details = orderDetails.get(position);
        final MyOrdersItemViewModels itemViewModel = new MyOrdersItemViewModels(details, context);
        itemViewModel.getItemsOperationsLiveListener().observe((LifecycleOwner) context, new Observer<OrderRequestDetail>() {
            @Override
            public void onChanged(OrderRequestDetail orderDetail) {
                common.confirmation_Page(context, R.id.home_Main_Container, new MyOrderDetailFragment(orderDetail),"");
                notifyItemChanged(position);
            }
        });
        holder.setViewModel(itemViewModel);
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.bind();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.unbind();
    }

    public void updateData(@Nullable List<OrderRequestDetail> orderDetails) {
        this.orderDetails.clear();
        this.orderDetails.addAll(orderDetails);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MyOrdersItemBinding itemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bind();
        }

        void bind() {
            if (itemBinding == null) {
                itemBinding = DataBindingUtil.bind(itemView);
            }
        }

        void unbind() {
            if (itemBinding != null) {
                itemBinding.unbind(); // Don't forget to unbind
            }
        }

        void setViewModel(MyOrdersItemViewModels viewModel) {
            if (itemBinding != null) {
                itemBinding.setOrderItemViewModel(viewModel);
            }
        }
    }
}
