package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class DoctorsDetailsActivity extends AppCompatActivity {

    private String [][] doctorDetails = {
            {"Doctor Name : Saba Khan" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Ss" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shenaz" , "Hospital Address : Noida 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shaziya Khan" , "Hospital Address : Mumbai 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"}
    };
    private String [][] doctorDetails1 = {
            {"Doctor Name : Saba Khan" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Ss" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shenaz" , "Hospital Address : Noida 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shaziya Khan" , "Hospital Address : Mumbai 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"}
    };
    private String [][] doctorDetails2 = {
            {"Doctor Name : Saba Khan" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Ss" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shenaz" , "Hospital Address : Noida 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shaziya Khan" , "Hospital Address : Mumbai 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"}
    };
    private String [][] doctorDetails3 = {
            {"Doctor Name : Saba Khan" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Ss" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shenaz" , "Hospital Address : Noida 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shaziya Khan" , "Hospital Address : Mumbai 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"}
    };
    private String [][] doctorDetails4 = {
            {"Doctor Name : Saba Khan" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Ss" , "Hospital Address : Delhi 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shenaz" , "Hospital Address : Noida 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"},
            {"Doctor Name : Shaziya Khan" , "Hospital Address : Mumbai 110025" , "Exp : 5 Yrs" , "Mobile No : 55522","1620"}
    };
String [][] oneDoctorDetails = {};

    TextView doctorsDetails;
    Button backBtn;
    ListView listView;
    ArrayList list;
    SimpleAdapter adapter;
    HashMap<String , String> item;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_details);

        doctorsDetails = findViewById(R.id.title_doctor_details1);
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");
        doctorsDetails.setText(title);

        if(title.compareTo("Family Physicians")==0)
            oneDoctorDetails = doctorDetails;
        else  if(title.compareTo("Dietician")==0)
            oneDoctorDetails = doctorDetails1;
        else
        if(title.compareTo("Dentist")==0)
            oneDoctorDetails = doctorDetails2;
        else
        if(title.compareTo("Surgeon")==0)
            oneDoctorDetails = doctorDetails3;
        else
        if(title.compareTo("Cardiologists")==0)
            oneDoctorDetails = doctorDetails4;


            backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorsDetailsActivity.this , FindDoctorActivity.class));
            }
        });


        list = new ArrayList();
        for(int i=0; i<oneDoctorDetails.length; i++){
            item = new HashMap<String , String>();
            item.put("line1" , oneDoctorDetails[i][0]);
            item.put("line2" , oneDoctorDetails[i][1]);
            item.put("line3" , oneDoctorDetails[i][2]);
            item.put("line4" , oneDoctorDetails[i][3]);
            String fee = "Cons Fee: "+  NumberFormat.getCurrencyInstance(new Locale("en" , "IN")).format(Integer.parseInt(oneDoctorDetails[i][4]))+"/-";
            item.put("line5" , fee);
            list.add(item);
            adapter = new SimpleAdapter(this , list,
                    R.layout.multi_line_layout ,
                    new String[]{"line1" , "line2" , "line3" , "line4" , "line5"} ,
                    new int[]{R.id.line_1 , R.id.line_2 ,R.id.line_3, R.id.line_4, R.id.line_5});
            listView = findViewById(R.id.list_view_doctors_details);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(DoctorsDetailsActivity.this , BookAppointmentActivity.class);
                    intent.putExtra("text1" , title);
                    intent.putExtra("text2",oneDoctorDetails[i][0]);
                    intent.putExtra("text3",oneDoctorDetails[i][1]);
                    intent.putExtra("text4", oneDoctorDetails[i][3]);
                    intent.putExtra("text5",oneDoctorDetails[i][4]);

                    startActivity(intent);
                }
            });

        }
    }
}