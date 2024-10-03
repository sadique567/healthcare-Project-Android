package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetailsActivity extends AppCompatActivity {
    TextView title;
    ListView listView;
    Button backBtn;

    private String [][] orderDetails = {};
    HashMap<String , String> item;
    ArrayList arrayList;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        title = findViewById(R.id.OD_title);
        listView = findViewById(R.id.OD_listView);
        backBtn = findViewById(R.id.OD_back);

        arrayList = new ArrayList();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailsActivity.this , HomeActivity.class));
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs" , Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("username" , "").toString();
        DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare" , null , 1);
        ArrayList dbData = db.getOrderDetailsData(userName);
        Log.d("dbdata", String.valueOf(dbData));
        orderDetails = new String[dbData.size()][];
        for(int i=0; i<orderDetails.length; i++){
            orderDetails[i] = new String[5];
            String arrData =dbData.get(i).toString();
            String [] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            Log.d("dbdata 0", strData[0]);
            Log.d("dbdata 1", strData[1]);
            Log.d("dbdata 2", strData[5]+strData[6]);
            Log.d("dbdata 3", strData[3]);
            Log.d("dbdata 4", strData[7]);
            Log.d("dbdata 5", strData[2]);
            Log.d("dbdata 41", strData[4]);
            Log.d("dbdata 81", strData[8]);

            orderDetails[i][0] = strData[0];
            orderDetails[i][1] = strData[1];
            if (strData[8].compareTo("medicine") == 0){
                orderDetails[i][3] = "Del: " + strData[5];
            }
            else if (strData[8].compareTo("appointment") == 0){
                orderDetails[i][3] = "Del: " + strData[5];
            }
            else{
                orderDetails[i][3] = "Del: "+strData[5]+" "+strData[6];
            }
            orderDetails[i][2]= "Rs. "+strData[7];
            orderDetails[i][4] =strData[8];
        }

        for(int i=0 ; i<orderDetails.length; i++){
            item = new HashMap<>();
            item.put("line1",orderDetails[i][0]);
            item.put("line2",orderDetails[i][1]);
            item.put("line3",orderDetails[i][2]);
            item.put("line4",orderDetails[i][3]);
            item.put("line5",orderDetails[i][4]);
            arrayList.add(item);
        }
        //Log.d("item", String.valueOf(arrayList));
        adapter = new SimpleAdapter(this , arrayList ,
                R.layout.multi_line_layout ,
                new String[]{"line1", "line2","line3","line4","line5"} ,
                new int[] {R.id.line_1 , R.id.line_2 , R.id.line_3 , R.id.line_4 , R.id.line_5});
        listView.setAdapter(adapter);
    }
}