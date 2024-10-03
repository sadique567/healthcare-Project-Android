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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {

        TextView location , txtTotalCost;
        ListView txtlistView;
        private DatePickerDialog datePickerDialog;
        private TimePickerDialog timePickerDialog;
        Button checkOutBtn , backBtn ,dateBtn , timeBtn;

        HashMap<String , String> item1;
        ArrayList list1;
        SimpleAdapter adapter;
        private  String [][] packagesMS = {};
        int totalCost = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);

        location = findViewById(R.id.cartlab_test_location);
        dateBtn = findViewById(R.id.cartlab_app_date_select);
        timeBtn = findViewById(R.id.cartlab_app_time_select);
        checkOutBtn = findViewById(R.id.cartlab_cheackout);
        backBtn = findViewById(R.id.cartlab_backBtn);
        txtTotalCost = findViewById(R.id.cartlab_total_cost);
        txtlistView = findViewById(R.id.cartlab_list_view);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs" , Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "").toString();

        DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare", null , 1);

        ArrayList dbData = db.getCartData(username , "lab");
        //Log.d("Tag" , String.valueOf(dbData));
       // Toast.makeText(this, ""+dbData, Toast.LENGTH_SHORT).show();

        packagesMS = new String[dbData.size()][];
        for(int i=0; i<packagesMS.length; i++){
            packagesMS[i] = new String[5];
        }
            //this function use for calculate total Amount of your added packages
        String [] costArr = new String[dbData.size()];
        for(int i=0; i<dbData.size(); i++){
            String arrData = dbData.get(i).toString();
            String [] strData = arrData.split(java.util.regex.Pattern.quote("$:"));
            packagesMS[i][0]= strData[0];
            packagesMS[i][4]="Cost: "+strData[1]+"/-";
            //Toast.makeText(this, "Cost: "+strData[0]+"2:"+strData[1], Toast.LENGTH_SHORT).show();
            costArr[i] = strData[1];
            //
           // costArr[i]= Integer.valueOf(String.valueOf(strData[1]));
//            Toast.makeText(this, "Toast: "+n, Toast.LENGTH_SHORT).show();
           // totalCost = totalCost +Integer.parseInt(strData[0]);
            //9266876794


        }
        Log.d("value " , Arrays.toString(costArr));
//        for(int i=0; i<costArr.length ; i++){
//            totalCost = totalCost +costArr[i];
//        }
        for(int i=0; i<costArr.length; i++){
            totalCost = totalCost + Integer.parseInt(costArr[i]);
        }
         txtTotalCost.setText("Total Cost : "+totalCost);

        list1 = new ArrayList();
        for(int i=0; i<packagesMS.length; i++){
            item1 = new HashMap<String , String>();
            item1.put("line1" , packagesMS[i][0]);
            item1.put("line2" , packagesMS[i][1]);
            item1.put("line3" , packagesMS[i][2]);
            item1.put("line4" , packagesMS[i][3]);
            item1.put("line5" , packagesMS[i][4]);
            list1.add(item1);
           // Toast.makeText(this, ""+list1.indexOf(i), Toast.LENGTH_SHORT).show();
        }
        adapter = new SimpleAdapter(this , list1 , R.layout.multi_line_layout ,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_1 , R.id.line_2 , R.id.line_3 , R.id.line_4, R.id.line_5});

        txtlistView.setAdapter(adapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartLabActivity.this , LabTestActivity.class));
            }
        });

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartLabActivity.this , LabTestBookActivity.class);
                intent.putExtra("price" , txtTotalCost.getText());
                intent.putExtra("date" , dateBtn.getText());
                intent.putExtra("time" , timeBtn.getText());
                startActivity(intent);
            }
        });

        //Datepicker

        initDatePicker();

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
//timePicker
        initTimePicker();
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                dateBtn.setText(i2+"/"+i1+"/"+i);
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

                timeBtn.setText(i + ":"+i1);

            }
        };

        Calendar calendar = Calendar.getInstance();
        int hrs = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this , style , timeSetListener , hrs , mins , true);

    }
}