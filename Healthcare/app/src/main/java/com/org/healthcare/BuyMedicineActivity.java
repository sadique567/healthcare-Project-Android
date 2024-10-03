package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyMedicineActivity extends AppCompatActivity {
    ListView listView;
    Button goToCartBtn , backBtn;
    HashMap<String , String> item;
    ArrayList list;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

       listView = findViewById(R.id.list_view_bm);
       goToCartBtn = findViewById(R.id.bm_go_to_card);
       backBtn = findViewById(R.id.bm_backBtn);


        getMedicine();
       goToCartBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            startActivity(new Intent(BuyMedicineActivity.this , CartMedicineActivity.class));

           }
       });
       backBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(BuyMedicineActivity.this , HomeActivity.class));
           }
       });
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               //Toast.makeText(BuyMedicineActivity.this, "List View Called : ", Toast.LENGTH_SHORT).show();
               DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare", null , 1);
               Intent intent = new Intent(BuyMedicineActivity.this , MedicineDetailsActivity.class);
               String url = db.readUrl+"/medicine_name";
               RequestQueue queue = Volley.newRequestQueue(BuyMedicineActivity.this);
               JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                   @Override
                   public void onResponse(JSONArray response) {
                       try {
                           JSONObject obj = response.getJSONObject(i);
                           String madicineName = obj.getString("api_name");
                           String price = obj.getString("price_per_kg");
                           //Toast.makeText(BuyMedicineActivity.this, "Madicine Name : "+madicineName, Toast.LENGTH_SHORT).show();
                           intent.putExtra("apiName" , madicineName);
                           intent.putExtra("price" , price);

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       startActivity(intent);
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               });
               queue.add(req);


           }
       });

    }

    public void getMedicine(){
        DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare", null , 1);

        String url = db.readUrl+"/medicine_name";
        RequestQueue queue = Volley.newRequestQueue(BuyMedicineActivity.this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(BuyMedicineActivity.this, "Medicine Activity called", Toast.LENGTH_SHORT).show();
                list = new ArrayList();
                try {
                    JSONArray arr = new JSONArray(response);
                    for(int i=0; i<arr.length(); i++){
                        JSONObject obj = arr.getJSONObject(i);
                        item = new HashMap<>();
                        item.put("mName" , obj.getString("api_name"));
                        item.put("mprice" , "Rs."+obj.getString("price_per_kg"));
                        list.add(item);
                        Log.d("medicine" , String.valueOf(list));
                    }
                    adapter = new SimpleAdapter(getApplicationContext() , list ,R.layout.multi_line_layout,
                            new String[]{"mName","", "","","mprice"},
                            new int[]{R.id.line_1,R.id.line_2,R.id.line_3,R.id.line_4,R.id.line_5});
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
queue.add(request);

    }
}