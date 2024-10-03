package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LabTestDetailsActivity extends AppCompatActivity {

    TextView tvPackageName , tvTotalCost;
    EditText edDetails;
    Button addToCard , btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        addToCard = findViewById(R.id.ltd_add_to_card);
        btnBack = findViewById(R.id.ltd_backBtn);
        tvPackageName = findViewById(R.id.labtd_test_packages);
        tvTotalCost = findViewById(R.id.ltd_total_cost1);
        edDetails = findViewById(R.id.ltd_eidttxt_multiline_);
        edDetails.setKeyListener(null);

        Intent intent = getIntent();
       tvPackageName.setText(intent.getStringExtra("text1"));
       edDetails.setText(intent.getStringExtra("text2"));
       tvTotalCost.setText("Total Cost : "+intent.getStringExtra("text5")+"/-");


       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(LabTestDetailsActivity.this , LabTestActivity.class));
           }
       });
addToCard.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs" , Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username" , "").toString();
        String product = tvPackageName.getText().toString();
        float price = Float.parseFloat(intent.getStringExtra("text5").toString());

        DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare" , null , 1);
        if (db.checkCart(username , product)==1){
            Toast.makeText(LabTestDetailsActivity.this, "Product already added", Toast.LENGTH_SHORT).show();
        }else{
            db.addToCart(username , product , price , "lab");
            Toast.makeText(LabTestDetailsActivity.this, "Record inserted to cart", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LabTestDetailsActivity.this , LabTestActivity.class));
        }


    }
});



    }
}