package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {

        private  String [][] packages1 =
                {
                        {"Package1 : Full body checkup" ,"", "","" ,"999"},
                        {"Package2: Body Glucose" ,"", "","" ,"99"},
                        {"Package3 : Covid 19 " , "","","" ,"250"},
                        {"Package4 : Thyroid checkup" ,"", "","" ,"560"},
                        {"Package5 : Immunity checkup" ,"",  "","" ,"500"}
                };

    private String[] package_details = {
            "Blood Glucose Fasting\n" +
                    "Complete Hemogram\n" +
                    "HbA1c\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "LDH Lactate Dehydrogenase, Serum\n" +
                    "Lipid Profile\n" +
                    "Liver Function Test",
            "Blood Glucose Fasting",
            "COVID-19 Antibody Ig6",
            "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",
            "Complete Hemogram\n" +
                    "CRP (C Reactive Protein) Quantitative, Serum\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "Vitamin 0 Total-25 Hydroxy\n" +
                    "Liver Function Test\n" +
                    "Lipid Profile"
    };
    HashMap<String , String> item;
    ArrayList list;
    SimpleAdapter adapter;
    Button goToCardBtn , backBtn;
    ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);
        backBtn = findViewById(R.id.lt_backBtn);
        goToCardBtn =findViewById(R.id.lt_lab_go_to_card);
        listView = findViewById(R.id.list_view_lab_test);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this , HomeActivity.class));
            }
        });
        list = new ArrayList();
        for (int i=0 ; i <packages1.length; i++){
            item = new HashMap<>();
            item.put("line1" , packages1[i][0]);
            item.put("line2" , packages1[i][1]);
            item.put("line3" , packages1[i][2]);
            item.put("line4" , packages1[i][3]);
            item.put("line5" , "Total Cost: "+packages1[i][4]+"/-");
            list.add(item);
        }
        adapter = new SimpleAdapter(this , list , R.layout.multi_line_layout ,
                new String[]{"line1" , "line2", "line3", "line4","line5"},
                new int[]{R.id.line_1 , R.id.line_2  , R.id.line_3 , R.id.line_4 , R.id.line_5}
                );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LabTestActivity.this , LabTestDetailsActivity.class);
                intent.putExtra("text1" , packages1[i][0]);
                intent.putExtra("text2" , package_details[i]);
                intent.putExtra("text3" , packages1[i][2]);
                intent.putExtra("text4" , packages1[i][3]);
                intent.putExtra("text5" , packages1[i][4]);
                startActivity(intent);
            }
        });

        goToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    Intent intent = new Intent(LabTestActivity.this , CartLabActivity.class);
    startActivity(intent);
            }
        });

    }
}