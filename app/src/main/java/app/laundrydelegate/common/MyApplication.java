package app.laundrydelegate.common;

/**
 * Created by mos on 6/8/2017.
 */

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public void setMessageReceiverListener(RealTimeReceiver.MessageReceiverListener listener) {
        RealTimeReceiver.messageReceiverListene = listener;
    }
}