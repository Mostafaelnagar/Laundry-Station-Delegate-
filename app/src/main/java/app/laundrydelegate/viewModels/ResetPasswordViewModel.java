package app.laundrydelegate.viewModels;

import android.content.Context;
import android.text.TextUtils;
 import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import app.laundrydelegate.R;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.common.common;
import app.laundrydelegate.models.users.LoginRequest;
import app.laundrydelegate.services.ServiceGenerator;
import app.laundrydelegate.views.FragmentResetPassword;
import app.laundrydelegate.views.OrderDetailFragment;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordViewModel extends BaseObservable {
    public String profileOldPassword;
    public String validationNewPassword;
    public String validationConfirmNewPassword;
    private Context context;


    public ResetPasswordViewModel(Context context) {
        this.context = context;
    }


    public void userResetPassword() {
        if (!TextUtils.isEmpty(profileOldPassword) && !TextUtils.isEmpty(validationNewPassword) && !TextUtils.isEmpty(validationConfirmNewPassword)) {
            final SpotsDialog spotsDialog = new SpotsDialog(context);
            spotsDialog.show();
            ServiceGenerator.getRequestApi().updateUserPassword(SharedPrefManager.getInstance(context).getUserData().getPhone(), validationNewPassword, validationConfirmNewPassword).enqueue(new Callback<LoginRequest>() {
                @Override
                public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                     if (response.body().getStatus() == 200) {
                        spotsDialog.dismiss();
                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        common.removeFragment(new FragmentResetPassword(), context);
                    } else {
                        spotsDialog.dismiss();
                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginRequest> call, Throwable t) {
                    spotsDialog.dismiss();
                 }
            });
        } else {
            Toast.makeText(context, "" + context.getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();
        }
    }

    @Bindable
    public String getProfileOldPassword() {
        return profileOldPassword;
    }

    public void setProfileOldPassword(String profileOldPassword) {
        this.profileOldPassword = profileOldPassword;
        notifyChange();
    }

    public void back() {
        common.removeFragment(new OrderDetailFragment(), context);

    }

    @Bindable

    public String getValidationNewPassword() {
        return validationNewPassword;
    }

    public void setValidationNewPassword(String validationNewPassword) {
        this.validationNewPassword = validationNewPassword;
        notifyChange();

    }

    @Bindable

    public String getValidationConfirmNewPassword() {
        return validationConfirmNewPassword;
    }

    public void setValidationConfirmNewPassword(String validationConfirmNewPassword) {
        this.validationConfirmNewPassword = validationConfirmNewPassword;
        notifyChange();

    }
}
