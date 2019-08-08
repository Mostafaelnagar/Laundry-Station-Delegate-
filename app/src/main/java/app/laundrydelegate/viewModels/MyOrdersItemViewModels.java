package app.laundrydelegate.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import app.laundrydelegate.BR;
import app.laundrydelegate.R;
import app.laundrydelegate.common.common;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.views.ChatFragment;

public class MyOrdersItemViewModels extends BaseObservable {
    OrderRequestDetail orderDetail;
    Context context;
    public MutableLiveData<OrderRequestDetail> itemsOperationsLiveListener;
    public String[] orderStatus;

    public MyOrdersItemViewModels(OrderRequestDetail orderDetail, Context context) {
        this.orderDetail = orderDetail;
        this.context = context;
        itemsOperationsLiveListener = new MutableLiveData<>();
        orderStatus = context.getResources().getStringArray(R.array.orderStatus);
    }

    public MutableLiveData<OrderRequestDetail> getItemsOperationsLiveListener() {
        return itemsOperationsLiveListener;
    }

    @Bindable
    public String getLaundryName() {
        return !TextUtils.isEmpty(orderDetail.getLaundry().getName()) ? orderDetail.getLaundry().getName() : "";
    }

    @Bindable
    public String getOrderTotal() {
        return !TextUtils.isEmpty(String.valueOf(orderDetail.getServices().get(0).getServiceTotal())) ? orderDetail.getServices().get(0).getServiceTotal() + context.getResources().getString(R.string.coin) : "";
    }

    @Bindable
    public String getOrderNumber() {
        return String.valueOf(orderDetail.getId());
    }

    @Bindable
    public String getOrderStatus() {
        String status = null;
        if (orderDetail.getOrderStatus() == 0)
            status = orderStatus[0];
        else if (orderDetail.getOrderStatus() <= 5)
            status = orderStatus[orderDetail.getOrderStatus()];
        return !TextUtils.isEmpty(status) ? status : orderStatus[5];
    }

    @Bindable
    public String getOrderDate() {
        return !TextUtils.isEmpty(orderDetail.getCreatedAt()) ? orderDetail.getCreatedAt() : "";
    }

    public OrderRequestDetail getMyOrders() {
        return orderDetail;
    }

    public void performClickAction(OrderRequestDetail orderDetail) {
        notifyChange();
        itemsOperationsLiveListener.setValue(orderDetail);
    }

    public void startChat() {
        common.confirmation_Page(context, R.id.home_Main_Container, new ChatFragment(orderDetail), "");
    }

    public void setOrderStatus(String[] orderStatus) {
        this.orderStatus = orderStatus;
        notifyPropertyChanged(BR.orderStatus);
    }
}
