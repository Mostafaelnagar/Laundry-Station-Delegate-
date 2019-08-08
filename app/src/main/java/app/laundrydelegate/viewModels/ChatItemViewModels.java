package app.laundrydelegate.viewModels;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import app.laundrydelegate.R;
import app.laundrydelegate.models.chat.Message;

public class ChatItemViewModels extends BaseObservable {
    Message messages;
    public String userImage;

    public ChatItemViewModels(Message messages) {
        this.messages = messages;
    }

    @Bindable
    public String getMessage() {
        return !TextUtils.isEmpty(messages.getContent()) ? messages.getContent() : "";
    }


    @BindingAdapter({"bind:chatUserImage"})
    public static void loadImage(ImageView view, String profileImage) {
        Picasso.get()
                .load(profileImage)
                .placeholder(R.drawable.profile_holder)
                .into(view);
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
