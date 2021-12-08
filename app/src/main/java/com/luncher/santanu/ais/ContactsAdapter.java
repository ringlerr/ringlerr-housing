package com.luncher.santanu.ais;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ringlerr.callplus.CallPlus;

import java.util.HashMap;
import java.util.List;

public class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView numberTextViewA;
        public TextView numberTextViewB;
        public TextView numberTextViewC;
        public TextView numberTextViewD;
        public LinearLayout callA;
        public LinearLayout callB;
        public LinearLayout callC;
        public LinearLayout callD;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            numberTextViewA = (TextView) itemView.findViewById(R.id.message_a);
            numberTextViewB = (TextView) itemView.findViewById(R.id.message_b);
            numberTextViewC = (TextView) itemView.findViewById(R.id.message_c);
            numberTextViewD = (TextView) itemView.findViewById(R.id.message_d);

            callA = itemView.findViewById(R.id.call_a);
            callB = itemView.findViewById(R.id.call_b);
            callC = itemView.findViewById(R.id.call_c);
            callD = itemView.findViewById(R.id.call_d);
        }
    }

    // Store a member variable for the contacts
    private List<Contact> mContacts;
    private Context context;
    private static final int PERMISSION_REQUEST_CALL_PHONE = 124;

    // Pass in the contact array into the constructor
    public ContactsAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.activity_one_content, parent, false);

        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Contact contact = mContacts.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.numberTextViewA;
        TextView textViewb = holder.numberTextViewB;
        TextView textViewc = holder.numberTextViewC;
        TextView textViewd = holder.numberTextViewD;
        LinearLayout callA = holder.callA;
        LinearLayout callB = holder.callB;
        LinearLayout callC = holder.callC;
        LinearLayout callD = holder.callD;

        final String name = contact.getName();
        final String number = contact.getNumber();
        textView.setText(number+" ("+name+")");
        textViewb.setText(number+" ("+name+")");
        textViewc.setText(number+" ("+name+")");
        textViewd.setText(number+" ("+name+")");

        final String ringlerr_key = "54fdf887b7418a41fa99db39251c1726ff13fda38b2589509e1c6d4f3533emt";
        final String type = "call";
        final String image_url = "";
        final String caller_name = "AIS";
        final String recever_name = "Customer";
        final String user_image = "";
        final String ServerToken = "AAAAOzEVZz4:APA91bH6MSh2-JrebUcRqq6ASnflRNkTiH11IZ64ckgXoWK3vs3y2sVkzgHt_jm1veua-eziOcmzpQVUit5-pTiwHapLjGG8RljMPA9z2mKlK2zePA1bTuwVADPOavNNpKXzBqr-T49m"; //not necessary for IndiaMart

        SessionManager session = new SessionManager(context);
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        final String mPhone = user.get(SessionManager.KEY_PHONE);
        final String phone_from = mPhone;

        callA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = "Hi "+name+", Calling you to resolve your complaint of Failed Billing Payment Transaction";
                //String message = "Hi, Thomascook has great deals on Holiday plans. Pls answer the phone to avail amazing discount.";

                final String phone_number = number;
                if(phone_number.equals("")){
                    final EditText taskEditText = new EditText(context);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("AIS")
                            .setMessage("Please enter called person number")
                            .setIcon(R.drawable.property_imgc)
                            .setView(taskEditText)
                            .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String type_number = String.valueOf(taskEditText.getText());
                                    CallPlus.sendContext(ringlerr_key, type_number, message, type, image_url, phone_from,
                                            caller_name, recever_name, "AIS", user_image, ServerToken, "Shoe", "", false);
                                    call_phone(type_number);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                }else {
                    CallPlus.sendContext(ringlerr_key, phone_number, message, type, image_url, phone_from,
                            caller_name, recever_name, "AIS", user_image, ServerToken, "Shoe", "", false);
                    call_phone(phone_number);
                }
            }
        });

        callD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String message = "Hi "+name+", Your bill payment TB 500 is due today. Kindly pay to avoid late payment fee";
                final String phone_number = number;

                if(phone_number.equals("")) {
                    final EditText taskEditText = new EditText(context);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("AIS")
                            .setMessage("Please enter called person number")
                            .setIcon(R.drawable.property_imgc)
                            .setView(taskEditText)
                            .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String type_number = String.valueOf(taskEditText.getText());
                                    final String cvc = CallPlus.sendContext(ringlerr_key, type_number, message, "payment", image_url, phone_from,
                                            caller_name, recever_name, "AIS", user_image, ServerToken, "Shoe", "", false);
                                    call_phone(type_number);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                }else{
                    final String cvc = CallPlus.sendContext(ringlerr_key, phone_number, message, "payment", image_url, phone_from,
                            caller_name, recever_name, "AIS", user_image, ServerToken, "Shoe", "", false);

                    call_phone(phone_number);
                }

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //Do something after 100ms
//                        AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
//                        alertbox.setMessage(cvc);
//                        alertbox.setTitle("This is your CV code");
//                        //alertbox.setIcon(R.drawable.ic_launcher_foreground);
//
//                        alertbox.setNeutralButton("OK",
//                                new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface arg0,
//                                                        int arg1) {
//
//                                    }
//                                });
//                        alertbox.show();
//
//                        //notification
//                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//                        int notificationId = 1;
//                        String channelId = "channel-01";
//                        String channelName = "Channel Name";
//                        int importance = NotificationManager.IMPORTANCE_HIGH;
//
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                            NotificationChannel mChannel = new NotificationChannel(
//                                    channelId, channelName, importance);
//                            notificationManager.createNotificationChannel(mChannel);
//                        }
//
//                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
//                                .setSmallIcon(R.mipmap.ic_launcher)
//                                .setContentTitle("This is your CV Code")
//                                .setContentText(cvc);
//
//                        notificationManager.notify(notificationId, mBuilder.build());
//                        //notification
//                    }
//                }, 2000);
            }
        });

        callB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = "Hello "+name+", AIS DELIVERY is calling to confirm your delivery today at the address provided.";
                String phone_number = number;

                if(phone_number.equals("")) {
                    final EditText taskEditText = new EditText(context);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("AIS")
                            .setMessage("Please enter called person number")
                            .setIcon(R.drawable.property_imgc)
                            .setView(taskEditText)
                            .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String type_number = String.valueOf(taskEditText.getText());
                                    final String cvc = CallPlus.sendContext(ringlerr_key, type_number, message, "slide", image_url, phone_from,
                                            caller_name, recever_name, "AIS", user_image, ServerToken, "Shoe", "", false);
                                    call_phone(type_number);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();

                }else{
                    CallPlus.sendContext(ringlerr_key, phone_number, message, "slide", image_url, phone_from,
                            caller_name,recever_name,"AIS", user_image, ServerToken, "Shoe", "", false);
                    call_phone(phone_number);
                }
            }
        });

        callC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = "This call is about AIS offer and promition";
                String phone_number = number;

                if(phone_number.equals("")) {
                    final EditText taskEditText = new EditText(context);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("AIS")
                            .setMessage("Please enter called person number")
                            .setIcon(R.drawable.property_imgc)
                            .setView(taskEditText)
                            .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String type_number = String.valueOf(taskEditText.getText());
                                    final String cvc = CallPlus.sendContext(ringlerr_key, type_number, message, "image", image_url, phone_from,
                                            caller_name, recever_name, "AIS", user_image, ServerToken, "Shoe", "", false);
                                    call_phone(type_number);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();

                }else{
                    CallPlus.sendContext(ringlerr_key, phone_number, message, "image", image_url, phone_from,
                            caller_name,recever_name,"AIS", user_image, ServerToken, "Shoe", "", false);
                    call_phone(phone_number);
                }
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    private void call_phone(String phone) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.CALL_PHONE};

                ActivityCompat.requestPermissions((Activity)context, permissions, PERMISSION_REQUEST_CALL_PHONE);

            }else{
                makeCall(phone);
            }
        }else{
            makeCall(phone);
        }
    }

    @SuppressLint("MissingPermission")
    private void makeCall(String phone) {
        if(phone.equals("0614022162") || phone.equals("0614050913")){
            phone = "*18415"+phone;
        }
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        context.startActivity(callIntent);
    }
}