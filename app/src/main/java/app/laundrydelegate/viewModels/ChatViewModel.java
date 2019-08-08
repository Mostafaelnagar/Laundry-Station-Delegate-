package app.laundrydelegate.viewModels;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.laundrydelegate.BR;
import app.laundrydelegate.R;
import app.laundrydelegate.adapter.ChatAdapter;
import app.laundrydelegate.common.common;
import app.laundrydelegate.models.chat.ChatRequest;
import app.laundrydelegate.models.chat.Message;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.services.ServiceGenerator;
import app.laundrydelegate.views.ChatFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatViewModel extends BaseObservable {
    public MutableLiveData<Context> contextMutableLiveData;
    public MutableLiveData<ChatRequest> chatRequest;
    public List<Message> chatDetails;
    public ChatAdapter chatAdapter;
    OrderRequestDetail orderDetail;
    public String message, messageHint;

    public ChatViewModel(OrderRequestDetail orderDetail) {
        this.orderDetail = orderDetail;
        contextMutableLiveData = new MutableLiveData<>();
        chatDetails = new ArrayList<>();
        if (orderDetail.getChat() != null)
            setChatDetails(orderDetail.getChat().getMessages());
        chatAdapter = new ChatAdapter();
        chatRequest = new MutableLiveData<>();
    }

    @BindingAdapter({"app:chatAdapter", "app:chatDetails"})
    public static void bind(RecyclerView recyclerView, ChatAdapter chatAdapter, List<Message> chatDetails) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatAdapter);
        chatAdapter.updateData(chatDetails);
        if (chatDetails != null) {
            recyclerView.scrollToPosition(chatDetails.size() - 1);
        }
    }

    public MutableLiveData<ChatRequest> sendMessageRequest() {
        ServiceGenerator.getRequestApi().sendMessage(orderDetail.getId(), getMessage()).enqueue(new Callback<ChatRequest>() {
            @Override
            public void onResponse(Call<ChatRequest> call, Response<ChatRequest> response) {
                if (response.body().getStatus() == 200) {
                    setMessage("");
                    chatRequest.setValue(response.body());
                    chatDetails = chatRequest.getValue().getData().getMessages();
                    notifyChange();

                } else {
                    Toast.makeText(contextMutableLiveData.getValue(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatRequest> call, Throwable t) {
            }
        });
        return chatRequest;
    }

    public void back() {
        common.removeFragment(new ChatFragment(), contextMutableLiveData.getValue());
    }

    public void sendMessage() {
        notifyPropertyChanged(BR.message);
        sendMessageRequest();
    }

    @Bindable
    public List<Message> getChatDetails() {
        return chatDetails;
    }

    public void setChatDetails(List<Message> chatDetails) {
        this.chatDetails = chatDetails;
    }

    public ChatAdapter getChatAdapter() {
        return chatAdapter;
    }

    public void setChatAdapter(ChatAdapter chatAdapter) {
        this.chatAdapter = chatAdapter;
    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }

    @Bindable
    public String getMessageHint() {
        return contextMutableLiveData.getValue().getResources().getString(R.string.chatHint);
    }

    public void setMessageHint(String messageHint) {
        this.messageHint = messageHint;
    }
}
