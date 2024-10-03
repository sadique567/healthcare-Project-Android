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

public class MOrderPlaceActivity extends AppCompatActivity {
    TextInputLayout fName , aadharNo , emailId , phoneNo;
    Button book;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morder_place);
        fName = findViewById(R.id.md_fullName);
        aadharNo = findViewById(R.id.md_aadhar_card);
        emailId = findViewById(R.id.md_emailId);
        phoneNo = findViewById(R.id.md_phoneNumber);
        book = findViewById(R.id.md_book);

        Intent intent = getIntent();
        String [] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs" , Context.MODE_PRIVATE);
                String userName = sharedPreferences.getString("username" , "").toString();
                Log.d("username : " , userName);
                DatabaseConn dbCon = new DatabaseConn(getApplicationContext() , "healthcare" , null , 1);
                dbCon.addOrder(userName , fName.getEditText().getText().toString(),
                        aadharNo.getEditText().getText().toString(),
                        emailId.getEditText().getText().toString(),
                        phoneNo.getEditText().getText().toString()
                        , date , time , Double.parseDouble(price[1].toString()), "medicine");
                dbCon.removeCart(userName , "medicine");
                Toast.makeText(MOrderPlaceActivity.this, "Your Booking is done successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MOrderPlaceActivity.this , HomeActivity.class));
            }
        });


    }
}