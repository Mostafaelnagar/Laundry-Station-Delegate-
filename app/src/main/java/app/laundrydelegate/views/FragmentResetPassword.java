package app.laundrydelegate.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import app.laundrydelegate.R;
import app.laundrydelegate.databinding.FragmentResetPasswordBinding;
import app.laundrydelegate.viewModels.ResetPasswordViewModel;

public class FragmentResetPassword extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentResetPasswordBinding resetPasswordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, container, false);
        ResetPasswordViewModel viewModel = new ResetPasswordViewModel(getActivity());
        resetPasswordBinding.setResetPasswordViewModel(viewModel);
        return resetPasswordBinding.getRoot();
    }
}
