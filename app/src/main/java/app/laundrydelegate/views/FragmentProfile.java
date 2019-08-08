package app.laundrydelegate.views;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import app.laundrydelegate.R;
import app.laundrydelegate.databinding.FragmentProfileBinding;
import app.laundrydelegate.filesutils.FileOperations;
import app.laundrydelegate.viewModels.UserProfileViewModel;

public class FragmentProfile extends Fragment {
    FragmentProfileBinding profileBinding;
    UserProfileViewModel profileViewModel;
    private final int SELECT_PHOTO = 1;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment__profile, container, false);
        profileViewModel = new UserProfileViewModel(getActivity());
        profileBinding.setUserProfileViewModel(profileViewModel);
        profileViewModel.ImgeResult.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        // start picker to get image for cropping and then use the image in cropping activity
                        bringImagePicker();
                    }
                } else {
                    bringImagePicker();

                }

            }
        });
        return profileBinding.getRoot();
    }

    void bringImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                // Set the image in ImageView
                String img = FileOperations.getVolleyFileObject(getActivity(), data, "IMAGENAME", FileOperations.FILE_TYPE_IMAGE).getFilePath();
                profileViewModel.imgUri.setValue(Uri.parse(img));
                profileViewModel.imgeResp = img;
                profileViewModel.notifyChange();

            }

        }
    }
}

