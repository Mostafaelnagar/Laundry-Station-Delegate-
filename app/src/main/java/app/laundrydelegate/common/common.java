package app.laundrydelegate.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import app.laundrydelegate.HomeActivity;
import app.laundrydelegate.R;
import app.laundrydelegate.SplashActivity;

public class common {
    public static final String chanePasswordMsg = "تم تغير كلمه المرور بنجاح";
    public static final String Login__Succuss_msg = "تم تسجيل الدخول بنجاح";
    public static final String confirmation_Login_msg = "الحساب غير مفعل";
    public static String phone_Number;
    public static String code;
    public static String device_Id;
    public static String token;
    public static int NAVIGATE_TO_LAUNDRY = 1;
    public static int NAVIGATE_TO_USER = 2;


    public static void direct_To_Home(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("type", "");
        intent.putExtra("order_id", "");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
        ((Activity) context).finish();
    }

    //take three paramter context and type of confirmation (activation or forget password) and phone
    public static void confirmation_Page(Context context, int layout_Id, Fragment fragment, String name) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(layout_Id, fragment);
        if (name.equals("")) {
            fragmentTransaction.addToBackStack(null);
        } else {
            fragmentTransaction.addToBackStack(name);
        }
        fragmentTransaction.commit();
    }

    public static void removeFragment(Fragment fragment, Context context) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(fragment);
        trans.commit();
        manager.popBackStack();
    }

    //take three paramter context and type of confirmation (activation or forget password) and phone
    public static void replaceFragment(Context context, int layout_Id, Fragment fragment) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentManager manager = activity.getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(layout_Id, fragment);
        fragmentTransaction.commit();
    }

    public static void popStack(Context context, Fragment fragment, Fragment refreshed) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentManager manager = activity.getSupportFragmentManager();
        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction trans = manager.beginTransaction();
//        trans.detach(refreshed);
//        trans.attach(refreshed);
//        trans.commit();
//        manager.popBackStack();
    }

    public static void restart(Context context) {
        context.startActivity(new Intent(context, SplashActivity.class));
    }

    public static int fragmentCount(Context context) {
        FragmentActivity activity = (FragmentActivity) context;
        FragmentManager fm = activity.getSupportFragmentManager();
        return fm.getBackStackEntryCount();
    }

    public static void navigateToMaps(double lat, double lng,Context context) {
//        Intent intent = new Intent(Intent.ACTION_VIEW,
//
//                Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d" +
//                         "&daddr=" +
//                        destination.latitude +
//                        "," + destination.longitude +
//                        "&hl=zh&t=m&dirflg=d"));
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//        context.startActivityForResult(intent, 1);

    }
}
