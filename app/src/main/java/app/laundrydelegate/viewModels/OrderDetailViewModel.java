package app.laundrydelegate.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.laundrydelegate.R;
import app.laundrydelegate.adapter.OrderDetailAdapter;
import app.laundrydelegate.common.common;
import app.laundrydelegate.models.orders.AcceptOrderRequest;
import app.laundrydelegate.models.orders.OrderObject;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.services.ServiceGenerator;
import app.laundrydelegate.views.HomeFragment;
import app.laundrydelegate.views.OrderDetailFragment;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailViewModel extends BaseObservable {
    public List<OrderObject> orderDetail;
    public OrderDetailAdapter orderDetailAdapter;
    public MutableLiveData<Context> contextMutableLiveData;
    OrderRequestDetail orderRequestDetail;
    public String ClientName, ClientPhone, ClientAddress, LaundryName, LaundryAddress;
    public MutableLiveData<Integer> mapsLiveData;

    public OrderDetailViewModel(OrderRequestDetail orderRequestDetail) {
        this.orderRequestDetail = orderRequestDetail;
        orderDetail = new ArrayList<>();
        orderDetail = orderRequestDetail.getServices();
        orderDetailAdapter = new OrderDetailAdapter();
        contextMutableLiveData = new MutableLiveData<>();
        mapsLiveData = new MutableLiveData<>();
    }

    @BindingAdapter({"app:orderDetailAdapter", "app:orderDetail"})
    public static void bind(RecyclerView recyclerView, OrderDetailAdapter myOrderDetailAdapter, List<OrderObject> orderDetail) {
        recyclerView.setAdapter(myOrderDetailAdapter);
        myOrderDetailAdapter.updateData(orderDetail);
    }

    public String getLaundryImage() {
        return String.valueOf(!TextUtils.isEmpty(orderRequestDetail.getLaundry().getImg()) ? orderRequestDetail.getLaundry().getImg() : contextMutableLiveData.getValue().getResources().getDrawable(R.mipmap.background));
    }

    @BindingAdapter({"bind:laundryImage"})
    public static void loadImage(ImageView view, String laundryImage) {
        Picasso.get()
                .load(laundryImage)
                .placeholder(R.color.overlayBackground)
                .into(view);
    }

    public void back() {
        common.removeFragment(new OrderDetailFragment(), contextMutableLiveData.getValue());
    }

    public void acceptOrder() {
        final SpotsDialog dialog = new SpotsDialog(contextMutableLiveData.getValue());
        dialog.show();
        ServiceGenerator.getRequestApi().createOrder(orderRequestDetail.getId()).enqueue(new Callback<AcceptOrderRequest>() {
            @Override
            public void onResponse(Call<AcceptOrderRequest> call, Response<AcceptOrderRequest> response) {
                dialog.dismiss();
                if (response.body().getStatus() == 200) {
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    common.replaceFragment(contextMutableLiveData.getValue(), R.id.frame_Home_Container, new HomeFragment());
                } else {
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AcceptOrderRequest> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @Bindable
    public List<OrderObject> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderObject> orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Bindable
    public OrderDetailAdapter getOrderDetailAdapter() {
        return orderDetailAdapter;
    }

    public void setOrderDetailAdapter(OrderDetailAdapter orderDetailAdapter) {
        this.orderDetailAdapter = orderDetailAdapter;
    }

    @Bindable
    public String getClientName() {
        return !TextUtils.isEmpty(orderRequestDetail.getUser().getName()) ? orderRequestDetail.getUser().getName() : "";
    }

    @Bindable
    public String getClientAddress() {
        return !TextUtils.isEmpty(orderRequestDetail.getUser().getAddresses().get(0).getAddress()) ? orderRequestDetail.getUser().getAddresses().get(0).getAddress() : "";
    }

    @Bindable
    public String getClientPhone() {
        return !TextUtils.isEmpty(orderRequestDetail.getUser().getPhone()) ? orderRequestDetail.getUser().getPhone() : "";
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public void setClientPhone(String clientPhone) {
        ClientPhone = clientPhone;
    }


    public void setClientAddress(String clientAddress) {
        ClientAddress = clientAddress;
    }

    @Bindable
    public String getLaundryName() {
        return !TextUtils.isEmpty(orderRequestDetail.getLaundry().getName()) ? orderRequestDetail.getLaundry().getName() : "";
    }

    @Bindable
    public String getLaundryAddress() {
        return !TextUtils.isEmpty(orderRequestDetail.getLaundry().getAddress()) ? orderRequestDetail.getLaundry().getAddress() : "";
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
