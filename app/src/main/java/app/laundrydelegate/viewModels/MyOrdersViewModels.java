package app.laundrydelegate.viewModels;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrydelegate.adapter.MyOrdersAdapter;
import app.laundrydelegate.common.common;
import app.laundrydelegate.models.orders.MyOrderRequest;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.services.ServiceGenerator;
import app.laundrydelegate.views.MyOrdersFragment;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersViewModels extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    private List<OrderRequestDetail> myOrderDetails;
    private MyOrdersAdapter myOrderAdapter;
    public MutableLiveData<MyOrderRequest> orderRequest;
    private int empty;

    public MyOrdersViewModels() {
        contextMutableLiveData = new MutableLiveData<>();
        myOrderDetails = new ArrayList<>();
        myOrderAdapter = new MyOrdersAdapter();
        orderRequest = new MutableLiveData<>();

    }

    @BindingAdapter({"app:myOrderAdapter", "app:myOrderDetails"})
    public static void bind(RecyclerView recyclerView, MyOrdersAdapter myOrderAdapter, List<OrderRequestDetail> orderDetails) {
        recyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.updateData(orderDetails);
    }

    public MutableLiveData<MyOrderRequest> getMyOrders() {
        final SpotsDialog spotsDialog = new SpotsDialog(contextMutableLiveData.getValue());
        spotsDialog.show();
        ServiceGenerator.getRequestApi().getMyOrders().enqueue(new Callback<MyOrderRequest>() {
            @Override
            public void onResponse(Call<MyOrderRequest> call, Response<MyOrderRequest> response) {
                if (response.body().getStatus() == 200) {
                    spotsDialog.dismiss();
                    orderRequest.setValue(response.body());
                    myOrderDetails = orderRequest.getValue().getData();
                    setEmpty(myOrderDetails.size());
                } else {
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyOrderRequest> call, Throwable t) {
                spotsDialog.dismiss();
                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
        return orderRequest;
    }

    public void back() {
        if (common.fragmentCount(contextMutableLiveData.getValue()) == 0)
            ((Activity) contextMutableLiveData.getValue()).finish();
        else
            common.removeFragment(new MyOrdersFragment(), contextMutableLiveData.getValue());

    }


    @Bindable
    public List<OrderRequestDetail> getMyOrderDetails() {
        return myOrderDetails;
    }

    public void setMyOrderDetails(List<OrderRequestDetail> myOrderDetails) {
        this.myOrderDetails = myOrderDetails;

    }

    @Bindable
    public MyOrdersAdapter getMyOrderAdapter() {
        return myOrderAdapter;
    }

    public void setMyOrderAdapter(MyOrdersAdapter myOrderAdapter) {
        this.myOrderAdapter = myOrderAdapter;
    }

    public int getEmpty() {
        return empty == 0 ? View.VISIBLE : View.GONE;

    }

    public void setEmpty(int empty) {
        this.empty = empty;
        notifyPropertyChanged(empty);
    }
}
