package app.laundrydelegate.views;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.laundrydelegate.R;
import app.laundrydelegate.common.common;
import app.laundrydelegate.databinding.FragmentOrderDetailBinding;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.viewModels.OrderDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends Fragment {
    OrderRequestDetail orderRequestDetail;
    FragmentOrderDetailBinding detailsBinding;
    OrderDetailViewModel detailViewModel;
    RecyclerView recyclerViewDetail;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    public OrderDetailFragment(OrderRequestDetail orderRequestDetail) {
        this.orderRequestDetail = orderRequestDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        detailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false);
        detailViewModel = new OrderDetailViewModel(orderRequestDetail);
        detailsBinding.setOrderDetailViewModel(detailViewModel);
        detailViewModel.contextMutableLiveData.setValue(getActivity());
        initViews();
        liveDataListener();
        return detailsBinding.getRoot();
    }

    private void liveDataListener() {
        detailViewModel.mapsLiveData.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == common.NAVIGATE_TO_LAUNDRY) {
                    mapNavigate(orderRequestDetail.getLaundry().getLat(), orderRequestDetail.getLaundry().getLng());
                } else if (integer == common.NAVIGATE_TO_USER) {
                    mapNavigate(orderRequestDetail.getUser().getAddresses().get(0).getLat(), orderRequestDetail.getUser().getAddresses().get(0).getLng());
                }
            }
        });
    }

    private void initViews() {
        recyclerViewDetail = detailsBinding.recOrderDetail;
        recyclerViewDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDetail.setHasFixedSize(true);
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
