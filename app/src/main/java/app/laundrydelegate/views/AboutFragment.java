package app.laundrydelegate.views;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import app.laundrydelegate.R;
import app.laundrydelegate.common.common;
import app.laundrydelegate.databinding.FragmentAboutBinding;
import app.laundrydelegate.models.settings.SettingsResponse;
import app.laundrydelegate.services.ServiceGenerator;
import app.laundrydelegate.viewModels.ChatViewModel;
import app.laundrydelegate.viewModels.SettingsViewModels;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    FragmentAboutBinding fragmentAboutBinding;
    SettingsViewModels settingsViewModels;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentAboutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
        settingsViewModels = new SettingsViewModels();
        settingsViewModels.contextMutableLiveData.setValue(getActivity());
        getSettings();
        fragmentAboutBinding.aboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                common.removeFragment(new AboutFragment(), getActivity());

            }
        });
        return fragmentAboutBinding.getRoot();
    }

    private void getSettings() {
        final SpotsDialog spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.show();
        ServiceGenerator.getRequestApi().getSettings().enqueue(new Callback<SettingsResponse>() {
            @Override
            public void onResponse(Call<SettingsResponse> call, Response<SettingsResponse> response) {
                if (response.body().getStatus() == 200) {
                    spotsDialog.dismiss();
                    fragmentAboutBinding.aboutText.setText(response.body().getData().getAbout());

                } else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SettingsResponse> call, Throwable t) {
                spotsDialog.dismiss();
                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }
}
