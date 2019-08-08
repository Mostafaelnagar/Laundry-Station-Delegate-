package app.laundrydelegate.views;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.service.Common;
import com.rengwuxian.materialedittext.MaterialEditText;

import app.laundrydelegate.R;
import app.laundrydelegate.common.common;
import app.laundrydelegate.databinding.ChangeOrderStatusItemBinding;
import app.laundrydelegate.databinding.FragmentMyOrderDetailBinding;
import app.laundrydelegate.models.orders.AcceptOrderRequest;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.services.ServiceGenerator;
import app.laundrydelegate.viewModels.MyOrderDetailViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderDetailFragment extends Fragment {

    OrderRequestDetail orderDetail;
    FragmentMyOrderDetailBinding detailsBinding;
    MyOrderDetailViewModel detailViewModel;
    RecyclerView recyclerViewDetail;
    int postion;

    public MyOrderDetailFragment() {
        // Required empty public constructor
    }

    public MyOrderDetailFragment(OrderRequestDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        detailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_order_detail, container, false);
        detailViewModel = new MyOrderDetailViewModel(orderDetail);
        detailsBinding.setMyOrderDetailViewModel(detailViewModel);
        initViews();
        detailViewModel.contextMutableLiveData.setValue(getActivity());
        int status = orderDetail.getOrderStatus();
        if (status == 0) {
            detailsBinding.stepView.go(3, true);
        }
        detailsBinding.stepView.go(status, false);
        detailViewModel.cancelMutableLiveData.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                showCancelOrder();
            }
        });
        detailViewModel.backMutableLiveData.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
//                common.replaceFragment(getActivity(),R.id.frame_Home_Container,new MyOrdersFragment());
//                MyOrdersFragment ordersFragment = (MyOrdersFragment) getActivity().getSupportFragmentManager().findFragmentByTag("MyOrders");
//                common.popStack(getActivity(), new MyOrderDetailFragment(), ordersFragment);
                common.replaceFragment(getActivity(), R.id.home_Main_Container, new MyOrdersFragment());
            }
        });
        liveDataListener();
        return detailsBinding.getRoot();
    }

    private void initViews() {
        recyclerViewDetail = detailsBinding.recOrderDetail;
        recyclerViewDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDetail.setHasFixedSize(true);
    }

    private void showCancelOrder() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final ChangeOrderStatusItemBinding typeItemBinding = (ChangeOrderStatusItemBinding) DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.change_order_status_item, null, false);
        dialog.setContentView(typeItemBinding.getRoot());
        final MaterialEditText cancelOrderReason = dialog.findViewById(R.id.cancelOrderReason);
        final Spinner statusSpinner = dialog.findViewById(R.id.statusSpinner);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                postion = statusSpinner.getSelectedItemPosition();
                if (postion == 5) {
                    typeItemBinding.cancelOrderReason.setVisibility(View.VISIBLE);
                } else {
                    typeItemBinding.cancelOrderReason.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button cancelOrderBttun = dialog.findViewById(R.id.cancelOrderBttun);
        cancelOrderBttun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postion1 = statusSpinner.getSelectedItemPosition();
                if (postion1 != 0) {
                    ServiceGenerator.getRequestApi().changeOrdersStatus(orderDetail.getId(), postion1, cancelOrderReason.getText().toString()).enqueue(new Callback<AcceptOrderRequest>() {
                        @Override
                        public void onResponse(Call<AcceptOrderRequest> call, Response<AcceptOrderRequest> response) {
                            if (response.body().getStatus() == 200) {
                                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                detailsBinding.stepView.go(postion, true);
                                orderDetail.setOrderStatus(postion);
                            } else {
                                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AcceptOrderRequest> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });
                } else
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void liveDataListener() {
        detailViewModel.mapsLiveData.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == common.NAVIGATE_TO_LAUNDRY) {
                    mapNavigate(orderDetail.getLaundry().getLat(), orderDetail.getLaundry().getLng());
                } else if (integer == common.NAVIGATE_TO_USER) {
                    mapNavigate(orderDetail.getUser().getAddresses().get(0).getLat(), orderDetail.getUser().getAddresses().get(0).getLng());
                }
            }
        });
    }

    private void mapNavigate(String lat, String lng) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d" +
                        "&daddr=" +
                        lat +
                        "," + lng +
                        "&hl=zh&t=m&dirflg=d"));


        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivityForResult(intent, 1);
    }

}
