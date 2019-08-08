package app.laundrydelegate.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import app.laundrydelegate.R;
import app.laundrydelegate.common.MyApplication;
import app.laundrydelegate.common.RealTimeReceiver;
import app.laundrydelegate.databinding.FragmentChatBinding;
import app.laundrydelegate.models.chat.Message;
import app.laundrydelegate.models.orders.OrderRequestDetail;
import app.laundrydelegate.services.MyFirebaseMessagingService;
import app.laundrydelegate.viewModels.ChatViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements RealTimeReceiver.MessageReceiverListener {
    FragmentChatBinding chatBinding;
    ChatViewModel chatViewModel;
    OrderRequestDetail orderDetail;

    public ChatFragment() {
        // Required empty public constructor
    }

    public ChatFragment(OrderRequestDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        chatViewModel = new ChatViewModel(orderDetail);
        chatViewModel.contextMutableLiveData.setValue(getActivity());
        chatBinding.setChatViewModel(chatViewModel);
        return chatBinding.getRoot();
    }


    @Override
    public void onMessageChanged(String msg) {
        chatViewModel.chatDetails.add(new Message(msg, "user"));
        chatViewModel.notifyChange();
    }



    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getInstance().setMessageReceiverListener(this);
    }

}
