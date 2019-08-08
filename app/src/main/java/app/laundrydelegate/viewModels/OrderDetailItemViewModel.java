package app.laundrydelegate.viewModels;

import android.content.res.Resources;
import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import app.laundrydelegate.R;
import app.laundrydelegate.common.MyApplication;
import app.laundrydelegate.models.orders.OrderObject;

public class OrderDetailItemViewModel extends BaseObservable {
    private OrderObject orderDetail;

    public OrderDetailItemViewModel(OrderObject orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Bindable
    public String getItemName() {
        return !TextUtils.isEmpty(orderDetail.getServiceName()) ? orderDetail.getServiceName() : "";
    }

    @Bindable
    public String getItemCount() {
        String itemCount = "X" + orderDetail.getServiceCount();
        return !TextUtils.isEmpty(itemCount) ? itemCount : "";
    }

    @Bindable
    public String getServiceType() {
        String type = orderDetail.getServiceType();
        Resources resources = MyApplication.getInstance().getResources();
        if (type.equals("Wash") || type.equals("غسيل")) {
            return resources.getString(R.string.cat_wash);
        } else if (type.equals("Ironing") || type.equals("كى")) {
            return resources.getString(R.string.cat_ironing);
        } else {
            return resources.getString(R.string.cat_wash_ironing);
        }
    }

    @Bindable
    public String getItemPrice() {
        return !TextUtils.isEmpty(String.valueOf(orderDetail.getServiceTotal())) ? orderDetail.getServiceTotal() + MyApplication.getInstance().getResources().getString(R.string.coin) : "";
    }

    public OrderObject getOrderDetail() {
        return orderDetail;
    }

}
