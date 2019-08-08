package app.laundrydelegate.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import app.laundrydelegate.HomeActivity;
import app.laundrydelegate.R;
import app.laundrydelegate.common.ConnectivityReceiver;
import app.laundrydelegate.common.MyApplication;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.common.common;

public class AuthActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        checkConnection();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.validation_FrameLayout, new LoginFragment());
        fragmentTransaction.commit();
        // get device Id
        common.device_Id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //get Token Id
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                SharedPrefManager.getInstance(AuthActivity.this).saveToken(instanceIdResult.getToken());

            }
        });
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = getBaseContext().getString(R.string.connection_vaild_msg);
            Toast.makeText(this, "" + message, Toast.LENGTH_LONG).show();

        } else {
            message = getBaseContext().getString(R.string.connection_invaild_msg);
            color = Color.RED;
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.main_Auth), message, Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();
        }


    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(getBaseContext()).getUserData() != null) {
            Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
            intent.putExtra("type", "");
            intent.putExtra("order_id", "");
            startActivity(intent);
            finish();
        }
    }
}
