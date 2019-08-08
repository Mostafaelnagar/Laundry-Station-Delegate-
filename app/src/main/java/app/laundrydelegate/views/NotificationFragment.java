package app.laundrydelegate.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import app.laundrydelegate.R;
import app.laundrydelegate.databinding.FragmentNotificationBinding;
import app.laundrydelegate.models.notifications.NotificationsRequest;
import app.laundrydelegate.viewModels.NotificationsViewModels;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    FragmentNotificationBinding notificationsBinding;
    NotificationsViewModels viewModels;
    RecyclerView recyclerViewNotifications;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        notificationsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        viewModels = new NotificationsViewModels();
        notificationsBinding.setNotificationsViewModels(viewModels);
        initViews();
        viewModels.contextMutableLiveData.setValue(getActivity());
        viewModels.getNotifications();
        viewModels.notificationsRequest.observe(getActivity(), new Observer<NotificationsRequest>() {
            @Override
            public void onChanged(NotificationsRequest notificationsRequest) {
                viewModels.notifyChange();
            }
        });
        return notificationsBinding.getRoot();

    }

    private void initViews() {

        recyclerViewNotifications = notificationsBinding.recNotifications;
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNotifications.setHasFixedSize(true);
    }
}
