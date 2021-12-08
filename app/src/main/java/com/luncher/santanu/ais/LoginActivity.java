package com.luncher.santanu.ais;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;

import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatButton cntBtn = findViewById(R.id.cont);
        final TextInputEditText phone_no = findViewById(R.id.phone_no);
        final TextInputEditText country_code = findViewById(R.id.country_code);

        cntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_No = phone_no.getText().toString();
                String country_Code = country_code.getText().toString();
                String phoneNo = getLastnCharacters(phone_No, 10);
                if(!phoneNo.equals("")){
                    if (country_Code.trim().matches("\\+\\d\\d?\\d?")) {
                        SessionManager session = new SessionManager(getApplicationContext());
                        session.createLoginSession(phoneNo);
                        session.addCountryCode(country_Code);
                        Intent loginActivity = new Intent(LoginActivity.this, MainActivity.class);
                        loginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(loginActivity);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"Invalid Country Code", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public String getLastnCharacters(String inputString, int subStringLength){
        int length = inputString.length();
        if(length <= subStringLength){
            return inputString;
        }
        int startIndex = length-subStringLength;
        return inputString.substring(startIndex);
    }
}
