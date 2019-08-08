package app.laundrydelegate.viewModels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;

import app.laundrydelegate.R;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.common.common;
import app.laundrydelegate.models.orders.AcceptOrderRequest;
import app.laundrydelegate.models.users.LoginRequest;
import app.laundrydelegate.services.ServiceGenerator;
import app.laundrydelegate.views.FragmentPhoneConfirmation;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    private MutableLiveData<LoginRequest> authResponse = new MutableLiveData<>();
    public String userPhone;
    public String userPassword;

    public AuthViewModel() {
        contextMutableLiveData = new MutableLiveData<>();
    }

    //user login
    public void userAuth() {
        //for user login
        notifyChange();
        if (!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPassword)) {
            userLogin(getUserPhone(), getUserPassword());
        } else {
            Toast.makeText(contextMutableLiveData.getValue(), contextMutableLiveData.getValue().getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();

        }
    }

    //forget Password action
    public void userForgetPassword() {
        common.code = "forget";
        common.confirmation_Page(contextMutableLiveData.getValue(), R.id.validation_FrameLayout, new FragmentPhoneConfirmation(), "");
    }

    public void userLogin(String phone, String password) {
        final SpotsDialog spotsDialog = new SpotsDialog(contextMutableLiveData.getValue());
        spotsDialog.show();
        ServiceGenerator.getRequestApi().signIn(phone, password, SharedPrefManager.getInstance(contextMutableLiveData.getValue()).getToken(), common.device_Id).enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                spotsDialog.dismiss();
                common.phone_Number = userPhone;
                authResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
                spotsDialog.dismiss();
            }
        });
    }

    public void updateUserToken() {
        ServiceGenerator.getRequestApi().updateToken(SharedPrefManager.getInstance(contextMutableLiveData.getValue()).getToken()).enqueue(new Callback<AcceptOrderRequest>() {
            @Override
            public void onResponse(Call<AcceptOrderRequest> call, Response<AcceptOrderRequest> response) {
                Toast.makeText(contextMutableLiveData.getValue(), "Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AcceptOrderRequest> call, Throwable t) {
                Log.i("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }

    public MutableLiveData<LoginRequest> getAuthResponse() {
        return authResponse;
    }

    @Bindable
    public String getUserPhone() {
        return userPhone;
    }


    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Bindable
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
