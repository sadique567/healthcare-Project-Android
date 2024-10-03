package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);
// 1 Back Button
        CardView backBtn = findViewById(R.id.card_find_doctor_backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindDoctorActivity.this , HomeActivity.class));
            }
        });
// 2        Physician
        CardView familyPhysician = findViewById(R.id.card_find_doctor_family_physician);
        familyPhysician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindDoctorActivity.this , DoctorsDetailsActivity.class);
                intent.putExtra("Title", "Family Physician");
                startActivity(intent);
            }
        });
// 3 Dietician
        CardView dietician = findViewById(R.id.card_find_doctor_dietician);
        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindDoctorActivity.this , DoctorsDetailsActivity.class);
                intent.putExtra("Title", "Dietician");
                startActivity(intent);
            }
        });
// 4 Dentist
        CardView dentist = findViewById(R.id.card_find_doctor_Dentist);
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindDoctorActivity.this , DoctorsDetailsActivity.class);
                intent.putExtra("Title", "Dentist");
                startActivity(intent);
            }
        });
// 5 Surgeon
        CardView surgeon = findViewById(R.id.card_find_doctor_Surgeon);
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindDoctorActivity.this , DoctorsDetailsActivity.class);
                intent.putExtra("Title", "Surgeon");
                startActivity(intent);
            }
        });
// 6 Cardiologis
        CardView cardiologist = findViewById(R.id.card_find_doctor_cardiologist);
        cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindDoctorActivity.this , DoctorsDetailsActivity.class);
                intent.putExtra("Title", "Cardiologist");
                startActivity(intent);
            }
        });
    }
}