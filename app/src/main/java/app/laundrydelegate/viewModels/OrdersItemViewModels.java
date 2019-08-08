package app.laundrydelegate.viewModels;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import app.laundrydelegate.R;
import app.laundrydelegate.common.MyApplication;
import app.laundrydelegate.models.orders.OrderRequestDetail;

public class OrdersItemViewModels extends BaseObservable {
    OrderRequestDetail orderDetail;
    public MutableLiveData<OrderRequestDetail> itemsOperationsLiveListener;

    public OrdersItemViewModels(OrderRequestDetail orderDetail) {
        this.orderDetail = orderDetail;
        itemsOperationsLiveListener = new MutableLiveData<>();
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
        return !TextUtils.isEmpty(String.valueOf(orderDetail.getServices().get(0).getServiceTotal())) ? orderDetail.getServices().get(0).getServiceTotal() + MyApplication.getInstance().getResources().getString(R.string.coin) : "";
    }

    @Bindable
    public String getOrderNumber() {
        return String.valueOf(orderDetail.getId());
    }


    @Bindable
    public String getOrderDate() {
        return !TextUtils.isEmpty(orderDetail.getCreatedAt()) ? orderDetail.getCreatedAt() : "";
    }

    public OrderRequestDetail getOrderDetail() {
        return orderDetail;
    }

    public void performClickAction(OrderRequestDetail orderDetail) {
        notifyChange();
        itemsOperationsLiveListener.setValue(orderDetail);
    }

}
