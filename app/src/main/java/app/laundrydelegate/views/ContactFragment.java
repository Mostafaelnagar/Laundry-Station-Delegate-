package app.laundrydelegate.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import app.laundrydelegate.R;
import app.laundrydelegate.databinding.FragmentContactBinding;
import app.laundrydelegate.viewModels.ContactViewModel;
/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    FragmentContactBinding contactBinding;
    ContactViewModel contactViewModel;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contactBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false);
        contactViewModel = new ContactViewModel();
        contactViewModel.contextMutableLiveData.setValue(getActivity());
        contactBinding.setContactViewModel(contactViewModel);
        return contactBinding.getRoot();
    }

}
