package app.laundrydelegate.viewModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
 import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URL;

import app.laundrydelegate.R;
import app.laundrydelegate.common.SharedPrefManager;
import app.laundrydelegate.common.common;
import app.laundrydelegate.models.users.LoginRequest;
import app.laundrydelegate.services.ServiceGenerator;
import app.laundrydelegate.views.FragmentProfile;
import app.laundrydelegate.views.FragmentResetPassword;
import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileViewModel extends BaseObservable {
    public String userName;
    public String userEmail;
    public String userLicence;
    private Context context;
    public MutableLiveData<Integer> ImgeResult = new MutableLiveData<>();
    public MutableLiveData<Uri> imgUri = new MutableLiveData<Uri>();
    public String ProfileImage, imgeResp;

    public UserProfileViewModel(Context context) {
        this.context = context;
        this.userName = SharedPrefManager.getInstance(context).getUserData().getName();
        this.userEmail = SharedPrefManager.getInstance(context).getUserData().getEmail();
        this.userLicence = SharedPrefManager.getInstance(context).getUserData().getPhone();
        this.ProfileImage = SharedPrefManager.getInstance(context).getUserData().getImg();
        notifyChange();
    }


    public void userChangePassword() {
        common.confirmation_Page(context, R.id.home_Main_Container, new FragmentResetPassword(), "");
    }

    public void saveChanges() {
        notifyChange();
        if (!TextUtils.isEmpty(userLicence) && !TextUtils.isEmpty(userName)
                && !TextUtils.isEmpty(userEmail)) {
            updateUserInfo();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.auth_empty_warring), Toast.LENGTH_SHORT).show();

        }
    }

    private void updateUserInfo() {
        final SpotsDialog spotsDialog = new SpotsDialog(context);
        spotsDialog.show();
        File file = null;
        MultipartBody.Part fileReqBody = null;
        if (imgeResp != null) {
            file = new File(imgeResp);
            // Create a request body with file and image media type
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            fileReqBody = MultipartBody.Part.createFormData("img", file.getName(), requestBody);
        }
        //Create request body with text description and text media type
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), getUserName());
        RequestBody userEmail = RequestBody.create(MediaType.parse("text/plain"), getUserEmail());

        ServiceGenerator.getRequestApi().updateUserInfo(username, userEmail, fileReqBody
        ).enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        spotsDialog.dismiss();
                        SharedPrefManager.getInstance(context).userLogin(response.body().getData());
                        Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        spotsDialog.dismiss();
                        Toast.makeText(context, "" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Try again Later", Toast.LENGTH_SHORT).show();
                 }
            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable t) {
                spotsDialog.dismiss();
             }
        });
    }

    public void back() {
        common.removeFragment(new FragmentProfile(), context);
    }


    public String getProfileImage() {
        if (imgUri.getValue() == null) {
            return String.valueOf(!TextUtils.isEmpty(ProfileImage) ? ProfileImage : context.getResources().getDrawable(R.drawable.profile_holder));
        } else {
            return imgUri.getValue().toString();
        }
    }

    @BindingAdapter({"profileImage"})
    public static void loadImage(ImageView view, String profileImage) {
        if (imagePathValid(profileImage)) {
            Picasso.get()
                    .load(profileImage)
                    .placeholder(R.drawable.profile_holder)
                    .into(view);
        } else {
            File imgFile = new File(profileImage);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                view.setImageBitmap(myBitmap);

            }
        }
    }

    public void addImage() {
        ImgeResult.setValue(1);

    }


    @Bindable
    public String getUserName() {
        return userName;
    }

    @Bindable

    public String getUserEmail() {
        return userEmail;
    }


    @Bindable

    public String getUserLicence() {
        return userLicence;
    }


    public void setUserName(String userName) {
        this.userName = userName;
        notifyChange();
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        notifyChange();

    }

    public void setUserLicence(String userPhone) {
        this.userLicence = userPhone;
        notifyChange();

    }

    public static boolean imagePathValid(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

}
