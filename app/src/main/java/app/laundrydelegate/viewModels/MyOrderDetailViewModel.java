package app.laundrydelegate.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrydelegate.R;
import app.laundrydelegate.adapter.MyOrderDetailAdapter;
import app.laundrydelegate.common.common;
import app.laundrydelegate.models.orders.OrderObject;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.views.MyOrderDetailFragment;

public class MyOrderDetailViewModel extends BaseObservable {
    public List<OrderObject> myOrderDetail;
    public MyOrderDetailAdapter myOrderDetailAdapter;
    OrderRequestDetail orderDetail;
    public String ClientName, OrderTotal, ClientPhone, LaundryName, LaundryAddress;
    public MutableLiveData<Context> contextMutableLiveData;
    public MutableLiveData<Integer> cancelMutableLiveData;
    public MutableLiveData<Integer> backMutableLiveData;
    public MutableLiveData<Integer> mapsLiveData;

    public MyOrderDetailViewModel(OrderRequestDetail orderDetail) {
        this.orderDetail = orderDetail;
        myOrderDetail = new ArrayList<>();
        myOrderDetail = orderDetail.getServices();
        myOrderDetailAdapter = new MyOrderDetailAdapter();
        contextMutableLiveData = new MutableLiveData<>();
        cancelMutableLiveData = new MutableLiveData<>();
        backMutableLiveData = new MutableLiveData<>();
        mapsLiveData = new MutableLiveData<>();

    }

    @BindingAdapter({"app:myOrderDetailAdapter", "app:myOrderDetail"})
    public static void bind(RecyclerView recyclerView, MyOrderDetailAdapter myOrderDetailAdapter, List<OrderObject> myOrderDetail) {
        recyclerView.setAdapter(myOrderDetailAdapter);
        myOrderDetailAdapter.updateData(myOrderDetail);
    }
    public String getLaundryImage() {
        return String.valueOf(!TextUtils.isEmpty(orderDetail.getLaundry().getImg()) ? orderDetail.getLaundry().getImg() : contextMutableLiveData.getValue().getResources().getDrawable(R.mipmap.background));
    }
    @BindingAdapter({"bind:laundryImage"})
    public static void loadImage(ImageView view, String laundryImage) {
        Picasso.get()
                .load(laundryImage)
                .placeholder(R.color.overlayBackground)
                .into(view);
    }

    public void cancelOrder() {
        cancelMutableLiveData.setValue(1);
    }

    @Bindable
    public List<OrderObject> getMyOrderDetail() {
        return myOrderDetail;
    }

    public void back() {
        common.removeFragment(new MyOrderDetailFragment(), contextMutableLiveData.getValue());
    }

    public void setMyOrderDetail(List<OrderObject> myOrderDetail) {
        this.myOrderDetail = myOrderDetail;
    }

    @Bindable
    public MyOrderDetailAdapter getMyOrderDetailAdapter() {
        return myOrderDetailAdapter;
    }

    public void setMyOrderDetailAdapter(MyOrderDetailAdapter myOrderDetailAdapter) {
        this.myOrderDetailAdapter = myOrderDetailAdapter;
    }

    @Bindable
    public String getClientName() {
        return !TextUtils.isEmpty(orderDetail.getUser().getName()) ? orderDetail.getUser().getName() : "";
    }

    @Bindable
    public String getOrderTotal() {
        double orderTotal = 0.0;
        for (int i = 0; i < myOrderDetail.size(); i++) {
            orderTotal = orderTotal + Double.parseDouble(String.valueOf(myOrderDetail.get(i).getServiceTotal()));
        }
        return String.valueOf(orderTotal) + contextMutableLiveData.getValue().getResources().getString(R.string.coin);
    }

    @Bindable
    public String getClientPhone() {
        return !TextUtils.isEmpty(orderDetail.getUser().getPhone()) ? orderDetail.getUser().getPhone() : "";
    }

    public void setOrderDetail(OrderRequestDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public void setClientPhone(String clientPhone) {
        ClientPhone = clientPhone;
    }

    @Bindable
    public String getLaundryName() {
        return !TextUtils.isEmpty(orderDetail.getLaundry().getName()) ? orderDetail.getLaundry().getName() : "";
    }

    @Bindable
    public String getLaundryAddress() {
        return !TextUtils.isEmpty(orderDetail.getLaundry().getAddress()) ? orderDetail.getLaundry().getAddress() : "";
    }

    public void setLaundryName(String laundryName) {
        LaundryName = laundryName;
    }

    public void setLaundryAddress(String laundryAddress) {
        LaundryAddress = laundryAddress;
    }
    public void navigateToLaundry() {
        mapsLiveData.setValue(common.NAVIGATE_TO_LAUNDRY);
    }

    public void navigateToUser() {
        mapsLiveData.setValue(common.NAVIGATE_TO_USER);
    }
}
