package com.luncher.santanu.ais;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ringlerr.callplus.CallPlus;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class TwoFragment extends Fragment {

    String ringlerr_key = "54fdf887b7418a41fa99db39251c1726ff13fda38b2589509e1c6d4f3533emt";
    String message = "";
    String type = "call";
    String image_url = "";
    String phone_from;
    String phone_number;
    String caller_name = "AIS";
    String recever_name = "Customer";
    String user_image = "";
    String cCode = "";
    String ServerToken = "AAAAOzEVZz4:APA91bH6MSh2-JrebUcRqq6ASnflRNkTiH11IZ64ckgXoWK3vs3y2sVkzgHt_jm1veua-eziOcmzpQVUit5-pTiwHapLjGG8RljMPA9z2mKlK2zePA1bTuwVADPOavNNpKXzBqr-T49m"; //not necessary for IndiaMart

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.activity_two, container, false);

        SessionManager session = new SessionManager(getActivity().getApplicationContext());
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        final String mPhone = user.get(SessionManager.KEY_PHONE);
        cCode = user.get(SessionManager.KEY_CC);
        phone_from = mPhone;



        LinearLayout callA = v.findViewById(R.id.call_a);
        callA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Hi Kosit, Calling you to resolve your complaint of Failed Billing Payment Transaction";
                CallPlus.sendContext(ringlerr_key, phone_from, message, type, image_url, "12057820998",
                        caller_name,recever_name,"AIS", user_image, ServerToken, "Shoe", "", false);
                call_phone(phone_from);
            }
        });

        LinearLayout callD = v.findViewById(R.id.call_d);
        callD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Hello, AIS DELIVERY is calling to confirm your delivery today at the address provided.";
                CallPlus.sendContext(ringlerr_key, phone_from, message, "slide", image_url, "12057820998",
                        caller_name,recever_name,"AIS", user_image, ServerToken, "Shoe", "", false);
                call_phone(phone_from);
            }
        });

        LinearLayout callB = v.findViewById(R.id.call_b);
        callB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Hi K. Pao, Your bill payment TB 500 is due today. Kindly pay to avoid late payment fee";
                CallPlus.sendContext(ringlerr_key, phone_from, message, "payment", image_url, "12057820998",
                        caller_name,recever_name,"AIS", user_image, ServerToken, "Shoe", "", false);
                call_phone(phone_from);
            }
        });


        LinearLayout callC = v.findViewById(R.id.call_c);
        callC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "This call is about AIS offer and promition";
                CallPlus.sendContext(ringlerr_key, phone_from, message, "image", image_url, "12057820998",
                        caller_name,recever_name,"AIS", user_image, ServerToken, "Shoe", "", false);
                call_phone(phone_from);
            }
        });

        return v;
    }

    private void call_phone(String phone) {
        phone_number = null;
        Toast.makeText(getActivity().getApplicationContext(), "You will soon receive a call from Ringlerr", Toast.LENGTH_SHORT).show();
        makeCall(phone);
    }

    @SuppressLint({"MissingPermission", "StaticFieldLeak"})
    private void makeCall(final String phone) {

        new AsyncTask<String, Integer, Boolean>(){

            @Override
            protected Boolean doInBackground(String... strings) {

                String countryCode = cCode.replace("+","");
                Long tsLong = System.currentTimeMillis()/1000;
                // Create data variable for sent values to server

                String data = null;
                try {
                    data = URLEncoder.encode("to", "UTF-8")
                            + "=" + URLEncoder.encode(countryCode+""+phone, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Bitmap bmp = null;
                String urlImage = "https://api.ringlerr.com/twilio/voice/make_call.php";
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL(urlImage).openConnection();
                    connection.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                    wr.write( data );
                    wr.flush();

                    int responseCode=connection.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK) {

                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    if(connection != null) // Make sure the connection is not null.
                        connection.disconnect();
                }
                return true;
            }

            protected void onPostExecute(Boolean result) {

                //Add image to ImageView

            }


        }.execute();
    }
}
