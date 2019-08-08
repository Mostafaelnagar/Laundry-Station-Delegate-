package app.laundrydelegate.views;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import app.laundrydelegate.R;
import app.laundrydelegate.databinding.FragmentHomeBinding;
import app.laundrydelegate.models.myOrders.OrderSRequest;
import app.laundrydelegate.models.orders.MyOrderRequest;
import app.laundrydelegate.viewModels.OrdersViewModels;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;
    OrdersViewModels ordersViewModels;
    RecyclerView recyclerViewOrders;
    private FusedLocationProviderClient client;
    private LocationManager service;
    private boolean enabled;
    private double lat, lng;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        ordersViewModels = new OrdersViewModels();
        fragmentHomeBinding.setOrdersViewModel(ordersViewModels);
        ordersViewModels.contextMutableLiveData.setValue(getActivity());
        initViews();
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        service = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        //return the GPS Status if Enabled or not
        enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GPS settings
        if (!enabled) {
            getLocationRequest();
        } else {
            getCurrentLocation();
        }
        ordersViewModels.orderRequest.observe(getActivity(), new Observer<OrderSRequest>() {
            @Override
            public void onChanged(OrderSRequest orderRequest) {
                ordersViewModels.notifyChange();
            }
        });

        return fragmentHomeBinding.getRoot();
    }

    private void initViews() {
        recyclerViewOrders = fragmentHomeBinding.recOrders;
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOrders.setHasFixedSize(true);
    }

    private void getCurrentLocation() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                     ordersViewModels.getOrdersRequest(lat, lng);

                }


            }
        });
    }

    private void getLocationRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Enable Your GPS?!");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Enable GPS
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCurrentLocation();
        ordersViewModels.getOrdersRequest(lat, lng);

    }
}
