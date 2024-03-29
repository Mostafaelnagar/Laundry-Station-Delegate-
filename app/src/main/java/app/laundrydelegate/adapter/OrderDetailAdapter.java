package app.laundrydelegate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrydelegate.R;
import app.laundrydelegate.databinding.OrderDetailItemBinding;
import app.laundrydelegate.models.orders.OrderObject;
import app.laundrydelegate.viewModels.OrderDetailItemViewModel;
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    List<OrderObject> orderDetails;
    Context context;

    public OrderDetailAdapter() {
        orderDetails = new ArrayList<>();
        Log.i("MyOrderDetailAdapter", "MyOrderDetailAdapter: " + orderDetails);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        OrderObject details = orderDetails.get(position);
        final OrderDetailItemViewModel itemViewModel = new OrderDetailItemViewModel(details);
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

    public void updateData(@Nullable List<OrderObject> orderDetails) {
        this.orderDetails.clear();
        this.orderDetails.addAll(orderDetails);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OrderDetailItemBinding itemBinding;

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

        void setViewModel(OrderDetailItemViewModel viewModel) {
            if (itemBinding != null) {
                itemBinding.setOrderDetailItemViewModel(viewModel);
            }
        }
    }
}