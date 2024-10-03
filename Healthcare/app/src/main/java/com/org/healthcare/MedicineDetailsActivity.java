package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MedicineDetailsActivity extends AppCompatActivity {
    TextView useMD , descMD , totalCost;
    Button backBtn , addToCartMDBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);

        useMD = findViewById(R.id.use_md);
        descMD = findViewById(R.id.desc_md);
        totalCost = findViewById(R.id.md_totalCost);
        backBtn = findViewById(R.id.md_backBtn);
        addToCartMDBtn = findViewById(R.id.md_add_to_card);
        useMD.setKeyListener(null);
        descMD.setKeyListener(null);

        Intent intent = getIntent();
        String mdName = intent.getStringExtra("apiName");
        String mdPrice = intent.getStringExtra("price");
        Log.d("price" , mdPrice);
      //
        //  Toast.makeText(this, "MadicineName : "+mdName, Toast.LENGTH_SHORT).show();
        totalCost.setText("M.R.P "+mdPrice);

        //passig Medicine Name to fetch details
        medicineDetails(mdName);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicineDetailsActivity.this , BuyMedicineActivity.class));
            }
        });

        addToCartMDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs" , Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username" , "").toString();
                String product = mdName;
             //   Toast.makeText(MedicineDetailsActivity.this, "userName : "+username, Toast.LENGTH_SHORT).show();

                float price = Float.parseFloat(mdPrice);
                Log.d("product Price" , String.valueOf(price));
                DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare" , null , 1);
                if (db.checkCart(username , product)==1){
                    Toast.makeText(MedicineDetailsActivity.this, "Product already added", Toast.LENGTH_SHORT).show();
                }else{
                   // Toast.makeText(MedicineDetailsActivity.this, "This is Else Block ", Toast.LENGTH_SHORT).show();
                    db.addToCart(username , product , price , "medicine");
                    Toast.makeText(MedicineDetailsActivity.this, "Record inserted to cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MedicineDetailsActivity.this , BuyMedicineActivity.class));
                }
            }
        });
    }

    public void medicineDetails(String str){
        String url = "https://784907c4-7b12-488e-8381-01f7172ebc1b.mock.pstmn.io/medicine_desc";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray arr = new JSONArray(response);
                    for (int i=0; i<arr.length(); i++){
                        JSONObject obj = arr.getJSONObject(i);
                        String name = obj.getString("api_name");
                        String use = obj.getString("use");
                        String Desc = obj.getString("description");
                        if(name.equals(str)){
                        useMD.setText(name +" : "+ use);
                        descMD.setText(Desc);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(req);

    }
}