package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticalActivity extends AppCompatActivity {

    private String  [][] healthDetails = {
        {"walking daily","","","","Click More Details"},
        {"Home care of COVID-19","","","","Click More Details"},
        {"Stop Smoking","","","","Click More Details"},
        {"Menstrual Cramps","","","","Click More Details"},
        {"Healty Gut","","","","Click More Details"}
    };
    private int [] imgs ={
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5,
    };

    HashMap<String , String> items;
    ArrayList lst;
    SimpleAdapter adapter;

    ListView listView;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_artical);
        listView = findViewById(R.id.ha_listview);
        backBtn = findViewById(R.id.ha_backbtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticalActivity.this , HomeActivity.class));
            }
        });

        lst = new ArrayList();
        for (int i=0; i<healthDetails.length; i++){
            items = new HashMap<>();
            items.put("line1" , healthDetails[i][0]);
            items.put("line2" , healthDetails[i][1]);
            items.put("line3" , healthDetails[i][2]);
            items.put("line4" , healthDetails[i][3]);
            items.put("line5" , healthDetails[i][4]);
            lst.add(items);
        }
        adapter = new SimpleAdapter(this , lst ,
                R.layout.multi_line_layout,
                new String[] {"line1","line2","line3","line4","line5"},
                new int[]{R.id.line_1,R.id.line_2 ,R.id.line_3 ,R.id.line_4 ,R.id.line_5}
                );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HealthArticalActivity.this , HealthArticalDetailsActivity.class);
                intent.putExtra("text1",healthDetails[i][0]);
                intent.putExtra("text2" , imgs[i]);
                startActivity(intent);

            }
        });
    }
}