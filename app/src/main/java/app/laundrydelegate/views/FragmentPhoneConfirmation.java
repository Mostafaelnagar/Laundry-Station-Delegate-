package app.laundrydelegate.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import app.laundrydelegate.R;
import app.laundrydelegate.common.common;
import app.laundrydelegate.databinding.FragmentPhoneConfirmationBinding;
import app.laundrydelegate.models.users.LoginRequest;
import app.laundrydelegate.viewModels.ValidationViewModels;

public class FragmentPhoneConfirmation extends Fragment {
    FragmentPhoneConfirmationBinding confirmationBinding;
    ValidationViewModels validationViewModels;

    public FragmentPhoneConfirmation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        confirmationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_confirmation, container, false);
        validationViewModels = new ValidationViewModels();
        confirmationBinding.setValidationViewModel(validationViewModels);
        validationViewModels.contextMutableLiveData.setValue(getActivity());
        validationViewModels.getValidationResponse().observe(getActivity(), new Observer<LoginRequest>() {
            @Override
            public void onChanged(LoginRequest loginRequest) {
                if (loginRequest.getStatus() == 200) {
                    common.confirmation_Page(getContext(), R.id.validation_FrameLayout, new CodeConfirmationFragment(),"");
                } else {
                    Toast.makeText(getContext(), loginRequest.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return confirmationBinding.getRoot();
    }


}
