package app.laundrydelegate.viewModels;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import app.laundrydelegate.R;
import app.laundrydelegate.common.common;
import app.laundrydelegate.views.AboutFragment;
import app.laundrydelegate.views.ContactFragment;
import app.laundrydelegate.views.FragmentProfile;
import app.laundrydelegate.views.MyOrdersFragment;
import app.laundrydelegate.views.TermsFragment;

public class MenuViewModel extends BaseObservable {
    private Context context;
    public MutableLiveData<Integer> logoutMutableLiveData = new MutableLiveData<>();

    public MenuViewModel(Context context) {
        this.context = context;
    }

    public void toProfile() {
        common.confirmation_Page(context, R.id.home_Main_Container, new FragmentProfile(), "");
    }

    public void toMyOrders() {
        common.confirmation_Page(context, R.id.home_Main_Container, new MyOrdersFragment(), "MyOrders");
    }

    public void toAbout() {
        common.confirmation_Page(context, R.id.home_Main_Container, new AboutFragment(), "");
    }

    public void toTerms() {
        common.confirmation_Page(context, R.id.home_Main_Container, new TermsFragment(), "");
    }

    public void toContactForum() {
        common.confirmation_Page(context, R.id.home_Main_Container, new ContactFragment(), "");
    }
    public void toLanguage() {
        notifyChange();
        logoutMutableLiveData.setValue(2);
    }
    public void toLogOut() {
        notifyChange();
        logoutMutableLiveData.setValue(1);
    }
    public void grandInfo() {
        notifyChange();
        logoutMutableLiveData.setValue(3);
    }

    public void toRate() {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            e.getStackTrace();
        }
    }


    public MutableLiveData<Integer> getLogoutMutableLiveData() {
        return logoutMutableLiveData;
    }
}
