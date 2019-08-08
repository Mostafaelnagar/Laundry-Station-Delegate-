package app.laundrydelegate;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import app.laundrydelegate.common.MyApplication;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.views.AuthActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPrefManager.getInstance(SplashActivity.this).getCurrentLanguage(SplashActivity.this) != null)
                    changeLan(SharedPrefManager.getInstance(SplashActivity.this).getCurrentLanguage(SplashActivity.this));
                startActivity(new Intent(SplashActivity.this, AuthActivity.class));
                finish();
            }
        }, 3000);
    }

    private void changeLan(String lang) {

        Resources res = MyApplication.getInstance().getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);

        conf.setLocale(new Locale(lang)); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);

    }
}
