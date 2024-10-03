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

public class CartMedicineActivity extends AppCompatActivity {
    ListView listView;
    TextView totalCost;
    Button back , checkout , date , time;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    HashMap<String , String> item1;
    ArrayList list1;
    SimpleAdapter adapter;
    private  String [][] packagesMS = {};
    int totalCostAmount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_cart);

        listView = findViewById(R.id.md_list_view);
        totalCost = findViewById(R.id.md_cost1);
        back = findViewById(R.id.md_backBtn);
        checkout = findViewById(R.id.md_cheackout);
        date = findViewById(R.id.md_app_date);
        time = findViewById(R.id.md_app_time);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs" , Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "").toString();

        DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare", null , 1);

        ArrayList dbData = db.getCartData(username , "medicine");
        packagesMS = new String[dbData.size()][];
        for(int i=0; i<packagesMS.length; i++){
            packagesMS[i] = new String[5];
        }
        String [] costArr = new String[dbData.size()];
        for(int i=0; i<dbData.size(); i++){
            String arrData = dbData.get(i).toString();
            String [] strData = arrData.split(java.util.regex.Pattern.quote("$:"));
            packagesMS[i][0]= strData[0];
            packagesMS[i][4]="Cost: "+strData[1]+"/-";
            //Toast.makeText(this, "Cost: "+strData[0]+"2:"+strData[1], Toast.LENGTH_SHORT).show();
            costArr[i] = strData[1];
        }
        Log.d("value " , Arrays.toString(costArr));
        for(int i=0; i<costArr.length; i++){
            totalCostAmount = totalCostAmount + Integer.parseInt(costArr[i]);
        }
        Log.d("value medicine Cost " , String.valueOf(totalCostAmount));
        totalCost.setText("Total Cost : "+totalCostAmount);

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

        listView.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartMedicineActivity.this , BuyMedicineActivity.class));
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartMedicineActivity.this, "CHECK OUT CALLED", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CartMedicineActivity.this , MOrderPlaceActivity.class);
                intent.putExtra("price" , totalCost.getText());
                intent.putExtra("date" , date.getText());
                intent.putExtra("time" , time.getText());
                startActivity(intent);
            }
        });

        datePicker();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });
    }

    private void datePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date.setText(i2+"/"+i1+"/"+i);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this,dateSetListener,year , month , days );
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()+86400000);
    }
    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                time.setText(i + ":"+i1);

            }
        };

        Calendar calendar = Calendar.getInstance();
        int hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);

       // int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this , timeSetListener , hrs , mins , true);

    }

}