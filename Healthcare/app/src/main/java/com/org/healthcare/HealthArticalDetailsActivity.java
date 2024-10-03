package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HealthArticalDetailsActivity extends AppCompatActivity {

    TextView ha;
    ImageView img;
    Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_artical_details);
        img = findViewById(R.id.imageView);
        ha = findViewById(R.id.ha_textview);
        backBtn = findViewById(R.id.had_backbtn);
        Intent intent = getIntent();
        ha.setText(intent.getStringExtra("text1"));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int imgget = bundle.getInt("text2");
            img.setImageResource(imgget);
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticalDetailsActivity.this, HealthArticalActivity.class));
            }
        });
    }
}