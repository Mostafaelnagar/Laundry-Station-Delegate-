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
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.common.common;
import app.laundrydelegate.databinding.FragmentForgetPasswordBinding;
import app.laundrydelegate.models.users.LoginRequest;
import app.laundrydelegate.viewModels.ValidationViewModels;


public class ForgetPasswordFragment extends Fragment {


    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentForgetPasswordBinding resetPasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_forget_password, container, false);

        ValidationViewModels validationViewModels = new ValidationViewModels();
        resetPasswordBinding.setValidationViewModel(validationViewModels);
        validationViewModels.contextMutableLiveData.setValue(getActivity());
        validationViewModels.getValidationResponse().observe(getActivity(), new Observer<LoginRequest>() {
            @Override
            public void onChanged(LoginRequest reqDetailsModel) {
                if (reqDetailsModel.getMsg().equals(common.chanePasswordMsg)) {
                    SharedPrefManager.getInstance(getContext()).userLogin(reqDetailsModel.getData());
                    common.direct_To_Home(getContext());
                } else {
                    Toast.makeText(getContext(), reqDetailsModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return resetPasswordBinding.getRoot();
    }


}
