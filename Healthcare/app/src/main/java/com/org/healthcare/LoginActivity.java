package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText user_name , pswd;
    Button login_btn;
    TextView new_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        user_name = findViewById(R.id.userName);
        pswd = findViewById(R.id.loginPswd);
        login_btn = findViewById(R.id.button_login);
        new_registration = findViewById(R.id.new_regitration);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = user_name.getText().toString();
                String password = pswd.getText().toString();

                //DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare" , null , 1);
                DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare" , null , 1);

                if(username.length()==0 || password.length()==0){
                    Toast.makeText(LoginActivity.this, "User id or Password is missing", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "login called", Toast.LENGTH_SHORT).show();
                    if (db.userLogin(username, password)==1) {
                        Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs" , Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username" , username);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid user Name or Password", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        new_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this , NewRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}