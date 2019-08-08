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
import app.laundrydelegate.databinding.FragmentCodeConfirmationBinding;
import app.laundrydelegate.models.users.LoginRequest;
import app.laundrydelegate.viewModels.ValidationViewModels;


public class CodeConfirmationFragment extends Fragment {

    public CodeConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCodeConfirmationBinding confirmationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_code_confirmation, container, false);

        ValidationViewModels validationViewModels = new ValidationViewModels();
        confirmationBinding.setValidationViewModel(validationViewModels);
        validationViewModels.contextMutableLiveData.setValue(getActivity());
        validationViewModels.getValidationResponse().observe(getActivity(), new Observer<LoginRequest>() {
            @Override
            public void onChanged(LoginRequest reqDetailsModel) {
                if (common.code.equals("confirm")) {
                    Toast.makeText(getContext(), "" + reqDetailsModel.getMsg(), Toast.LENGTH_LONG).show();
                    SharedPrefManager.getInstance(getContext()).userLogin(reqDetailsModel.getData());
                    common.direct_To_Home(getContext());
                } else {
                    common.confirmation_Page(getContext(), R.id.validation_FrameLayout, new ForgetPasswordFragment(),"");
                }
            }
        });
        return confirmationBinding.getRoot();
    }
}
