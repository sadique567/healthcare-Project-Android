package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LabTestBookActivity extends AppCompatActivity {
    TextInputLayout name ,aadhar , email , phoneNo;
    Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);
        name = findViewById(R.id.ltb_fullName);
        aadhar = findViewById(R.id.ltb_aadhar_card);
        email = findViewById(R.id.ltb_emailId);
        phoneNo = findViewById(R.id.ltb_phoneNumber);
        bookButton = findViewById(R.id.ltb_book);
        Intent intent = getIntent();
        String [] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs" , Context.MODE_PRIVATE);
                String userName = sharedPreferences.getString("username" , "").toString();
                Log.d("username : " , userName);
                DatabaseConn dbCon = new DatabaseConn(getApplicationContext() , "healthcare" , null , 1);
                dbCon.addOrder(userName , name.getEditText().getText().toString(),
                        aadhar.getEditText().getText().toString(),
                        email.getEditText().getText().toString(),
                        phoneNo.getEditText().getText().toString()
                        , date , time , Double.parseDouble(price[1].toString()), "lab");
                dbCon.removeCart(userName , "lab");
                Toast.makeText(LabTestBookActivity.this, "Your Booking is done successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LabTestBookActivity.this , HomeActivity.class));
            }
        });


    }
}