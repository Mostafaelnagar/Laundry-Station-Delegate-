package app.laundrydelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.laundrydelegate.common.MyApplication;
import app.laundrydelegate.common.MyContextWrapper;
import app.laundrydelegate.common.RealTimeReceiver;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.common.common;
import app.laundrydelegate.services.MyFirebaseMessagingService;
import app.laundrydelegate.views.ChatFragment;
import app.laundrydelegate.views.HomeFragment;
import app.laundrydelegate.views.MenuFragment;
import app.laundrydelegate.views.MyOrderDetailFragment;
import app.laundrydelegate.views.MyOrdersFragment;
import app.laundrydelegate.views.NotificationFragment;
import app.laundrydelegate.views.OrderDetailFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity implements RealTimeReceiver.MessageReceiverListener {
    RealTimeReceiver broadcastReceiver;

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, SharedPrefManager.getInstance(newBase).getCurrentLanguage(newBase)));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/font1.otf")
//                .setFontAttrId(R.attr.fontPath)
//                .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        broadcastReceiver = new RealTimeReceiver();
        if (getIntent() != null) {
            String type = getIntent().getStringExtra("type");
            String order_id = getIntent().getStringExtra("order_id");
            if (type.equals("new_message")) {
                common.replaceFragment(this, R.id.home_Main_Container, new MyOrdersFragment());
            } else if (type.equals("new_order_status")) {
                common.replaceFragment(this, R.id.home_Main_Container, new MyOrdersFragment());
            } else
                setFragment(new HomeFragment());
//
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.Home) {
                    setFragment(new HomeFragment());
                    return true;
                } else if (id == R.id.Notifications) {
                    setFragment(new NotificationFragment());
                    return true;
                } else if (id == R.id.Menus) {
                    setFragment(new MenuFragment());
                    return true;
                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_Home_Container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();

        } else {
            Fragment homeMain = getSupportFragmentManager().findFragmentById(R.id.frame_Home_Container);
            if (homeMain instanceof HomeFragment || homeMain instanceof NotificationFragment || homeMain instanceof MenuFragment) {
                showExit();
            }
        }
    }

    private void showExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setMessage(getBaseContext().getString(R.string.exitText))
                .setCancelable(false)
                .setPositiveButton(getBaseContext().getString(R.string.logOutAccept), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton(getBaseContext().getString(R.string.logOutReject), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("app.laundrystation.receiver");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setMessageReceiverListener(this);
    }

    @Override
    public void onMessageChanged(String msg) {
    }
}
