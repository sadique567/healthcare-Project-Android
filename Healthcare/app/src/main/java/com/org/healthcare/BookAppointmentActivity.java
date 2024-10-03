package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {
        TextInputLayout appName , appEmail , appPhone , appAddress, appAadharNo, appConsFee;
        TextView doctorName;

        private DatePickerDialog datePickerDialog;
        private TimePickerDialog timePickerDialog;
        Button timeSelect;
        Button dateSelect;
        Button bookAppointmentBtn , backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        appName = findViewById(R.id.app_fullName);
        appEmail = findViewById(R.id.app_emailId);
        appPhone = findViewById(R.id.app_phoneNumber);
        appAddress = findViewById(R.id.app_address);
        appAadharNo = findViewById(R.id.app_aadhar_card);
        appConsFee = findViewById(R.id.app_cons_fee);
        doctorName = findViewById(R.id.app_doctor_name_title);

        timeSelect = findViewById(R.id.app_time_select);
        dateSelect = findViewById(R.id.app_date_select);

        appName.getEditText().setKeyListener(null);
        appEmail.getEditText().setKeyListener(null);
        appPhone.getEditText().setKeyListener(null);
        appAddress.getEditText().setKeyListener(null);
        appAadharNo.getEditText().setKeyListener(null);
        appConsFee.getEditText().setKeyListener(null);
        bookAppointmentBtn = findViewById(R.id.app_book_appointment);
        backbtn = findViewById(R.id.app_backbtn);


        Intent intent = getIntent();
        String title = intent.getStringExtra("text1");
        String name = intent.getStringExtra("text2");
        String address = intent.getStringExtra("text3");
        String contact = intent.getStringExtra("text4");
        String fee = intent.getStringExtra("text5");


        doctorName.setText(title);
        appName.getEditText().setText(name);
        appEmail.getEditText().setText("sadik@gmail.com");
        appPhone.getEditText().setText(contact);
        appAddress.getEditText().setText(address);
        appConsFee.getEditText().setText(fee);


        //Datepicker

        initDatePicker();

            dateSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datePickerDialog.show();
                }
            });

//timePicker

        initTimePicker();
        timeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            timePickerDialog.show();
            }
        });


    backbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(BookAppointmentActivity.this , FindDoctorActivity.class));
        }
    });

        bookAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs" , Context.MODE_PRIVATE);
                String userName = sharedPreferences.getString("username" , "").toString();
               // Log.d("username : " , userName);
                DatabaseConn dbCon = new DatabaseConn(getApplicationContext() , "healthcare" , null , 1);
                if(dbCon.checkAppoinmentExists(userName , title+"=>"+name )==1){
                    Toast.makeText(BookAppointmentActivity.this, "Appointment Already Book", Toast.LENGTH_SHORT).show();
                }else{
                    dbCon.addOrder(userName , title+"=>"+name , address ,contact ,"" ,dateSelect.getText().toString() ,timeSelect.getText().toString() , Double.parseDouble(fee),"appointment");
                    Toast.makeText(BookAppointmentActivity.this, "Your Appointment is successfully Booked", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BookAppointmentActivity.this , HomeActivity.class));
                }

            }
        });

    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    dateSelect.setText(i2+"/"+i1+"/"+i);
            }

        };
        Calendar calendar = Calendar.getInstance();
        int year  = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this ,style , dateSetListener , year ,month ,day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+86400000);

    }

    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                timeSelect.setText(i + ":"+i1);

            }
        };

        Calendar calendar = Calendar.getInstance();
        int hrs = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);
        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this , style , timeSetListener , hrs , mins , true);

    }
}