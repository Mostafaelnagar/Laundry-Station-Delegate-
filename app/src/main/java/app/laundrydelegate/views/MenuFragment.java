package app.laundrydelegate.views;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.internal.service.Common;

import java.util.Locale;

import app.laundrydelegate.R;
import app.laundrydelegate.common.MovementManager;
import app.laundrydelegate.common.MyApplication;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.common.common;
import app.laundrydelegate.databinding.FragmentMenuBinding;
import app.laundrydelegate.viewModels.MenuViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    FragmentMenuBinding menuBinding;
    MenuViewModel menuViewModel;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        menuBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        menuViewModel = new MenuViewModel(getActivity());
        menuBinding.setMenuViewModel(menuViewModel);
        menuViewModel.getLogoutMutableLiveData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    waring_Message();
                } else if (integer == 2) {
                    languageChange();
                } else {
                    showGrandDialog();
                }
            }
        });
        return menuBinding.getRoot();
    }

    private void showGrandDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.grand_dialog);
        ImageView dialogClose = dialog.findViewById(R.id.dialogClose);
        TextView whatsAppClick = dialog.findViewById(R.id.whatsApp);
        final TextView grandPhone = dialog.findViewById(R.id.grandPhone);
        final TextView grandCall = dialog.findViewById(R.id.grandCall);
        final TextView grandSite = dialog.findViewById(R.id.grandSite);
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        grandCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovementManager.startCall(getActivity(), grandPhone.getText().toString());
            }
        });
        whatsAppClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovementManager.startWhatsApp(getActivity(), grandPhone.getText().toString());
            }
        });
        grandSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovementManager.startWebPage(getActivity(), grandCall.getText().toString());
            }
        });

        dialog.show();
    }

    public void waring_Message() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.log_out_item);
        TextView negative_Button = dialog.findViewById(R.id.reject_LogOut);
        TextView postive_Button = dialog.findViewById(R.id.accept_LogOut);
        negative_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        postive_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AuthActivity.class));
                getActivity().finish();
                SharedPrefManager.getInstance(getActivity()).loggout();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void languageChange() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.language_item);
        Button arButton = dialog.findViewById(R.id.arBtn);
        Button enButton = dialog.findViewById(R.id.enbtn);
        arButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLan("ar");
                dialog.dismiss();
            }
        });
        enButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLan("en");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void changeLan(String lang) {
        Resources res = MyApplication.getInstance().getResources();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);
        SharedPrefManager.getInstance(MyApplication.getInstance()).setLanguage(MyApplication.getInstance(), lang);
        ((Activity) getActivity()).finish();
        common.restart(getActivity());
    }
}
