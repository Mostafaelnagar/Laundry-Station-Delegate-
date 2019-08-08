package app.laundrydelegate.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import app.laundrydelegate.R;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.common.common;
import app.laundrydelegate.databinding.FragmentLoginBinding;
import app.laundrydelegate.models.users.LoginRequest;
import app.laundrydelegate.viewModels.AuthViewModel;


public class LoginFragment extends Fragment {
    AuthViewModel authViewModel;
    FragmentLoginBinding loginBinding;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        authViewModel = new AuthViewModel();
        loginBinding.setAuthViewModel(authViewModel);
        authViewModel.contextMutableLiveData.setValue(getActivity());
        authViewModel.getAuthResponse().observe(this, new Observer<LoginRequest>() {
            @Override
            public void onChanged(LoginRequest request_details) {
                String status = request_details.getMsg();
                if (status.equalsIgnoreCase(common.confirmation_Login_msg)) {
                    common.code = "confirm";
                    common.confirmation_Page(getActivity(), R.id.validation_FrameLayout, new CodeConfirmationFragment(), "");
                } else if (status.equalsIgnoreCase(common.Login__Succuss_msg)) {
                    SharedPrefManager.getInstance(getContext()).userLogin(request_details.getData());
                    common.direct_To_Home(getContext());
                    authViewModel.updateUserToken();
                } else {
                    Toast.makeText(getContext(), "" + request_details.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return loginBinding.getRoot();
    }

}
