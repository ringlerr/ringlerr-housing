package com.luncher.santanu.ais;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class OneFragment extends Fragment {

    String ringlerr_key = "54fdf887b7418a41fa99db39251c1726ff13fda38b2589509e1c6d4f3533emt";
    String message = "";
    String type = "call";
    String image_url = "";
    String phone_from;
    String phone_number;
    String caller_name = "AIS";
    String recever_name = "Customer";
    String user_image = "";
    String ServerToken = "AAAAOzEVZz4:APA91bH6MSh2-JrebUcRqq6ASnflRNkTiH11IZ64ckgXoWK3vs3y2sVkzgHt_jm1veua-eziOcmzpQVUit5-pTiwHapLjGG8RljMPA9z2mKlK2zePA1bTuwVADPOavNNpKXzBqr-T49m"; //not necessary for IndiaMart
    ArrayList<Contact> contacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.activity_one, container, false);

        SessionManager session = new SessionManager(getActivity().getApplicationContext());
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        final String mPhone = user.get(SessionManager.KEY_PHONE);
        phone_from = mPhone;

        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) v.findViewById(R.id.rvContacts);

        // Initialize contacts
        contacts = Contact.createContactsList();
        // Create adapter passing in the sample user data
        ContactsAdapter adapter = new ContactsAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        // That's all!

        return v;
    }
}
