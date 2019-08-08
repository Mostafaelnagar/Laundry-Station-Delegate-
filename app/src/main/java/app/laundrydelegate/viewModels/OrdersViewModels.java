package app.laundrydelegate.viewModels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrydelegate.adapter.OrdersAdapter;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.models.myOrders.OrderSRequest;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.services.ServiceGenerator;
import app.laundrydelegate.views.AuthActivity;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersViewModels extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    private List<OrderRequestDetail> orderDetails;
    private OrdersAdapter orderAdapter;
    public MutableLiveData<OrderSRequest> orderRequest;
    public int empty;

    public OrdersViewModels() {
        contextMutableLiveData = new MutableLiveData<>();
        orderDetails = new ArrayList<>();
        orderAdapter = new OrdersAdapter();
        orderRequest = new MutableLiveData<>();
    }

    @BindingAdapter({"app:orderAdapter", "app:orderDetails"})
    public static void bind(RecyclerView recyclerView, OrdersAdapter orderAdapter, List<OrderRequestDetail> orderDetails) {
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.updateData(orderDetails);
    }

    public MutableLiveData<OrderSRequest> getOrdersRequest(double lat, double lng) {
        final SpotsDialog spotsDialog = new SpotsDialog(contextMutableLiveData.getValue());
        spotsDialog.show();
        ServiceGenerator.getRequestApi().getMyOrders(lat, lng).enqueue(new Callback<OrderSRequest>() {
            @Override
            public void onResponse(Call<OrderSRequest> call, Response<OrderSRequest> response) {
                if (response.body() != null && response.body().getStatus() == 200) {
                    spotsDialog.dismiss();
                    orderRequest.setValue(response.body());
                    orderDetails = orderRequest.getValue().getData().getData();
                    Log.i("onResponse", "onResponse: "+response.body());
                    setEmpty(orderDetails.size());
                } else {
                    if (response.body().getMsg().equals("please enter a valid jwt")) {
                        SharedPrefManager.getInstance(contextMutableLiveData.getValue()).loggout();
                        contextMutableLiveData.getValue().startActivity(new Intent(contextMutableLiveData.getValue(), AuthActivity.class));
                        ((Activity) contextMutableLiveData.getValue()).finish();
                    }
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<OrderSRequest> call, Throwable t) {
                spotsDialog.dismiss();
                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
        return orderRequest;
    }

    @Bindable
    public List<OrderRequestDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderRequestDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrdersAdapter getOrderAdapter() {
        return orderAdapter;
    }

    public void setOrderAdapter(OrdersAdapter orderAdapter) {
        this.orderAdapter = orderAdapter;
    }

    public int getEmpty() {
        return empty == 0 ? View.VISIBLE : View.GONE;

    }

    public void setEmpty(int empty) {
        this.empty = empty;
        notifyPropertyChanged(empty);
    }
}
