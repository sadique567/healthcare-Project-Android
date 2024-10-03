package com.org.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NewRegisterActivity extends AppCompatActivity {
        Button submit_btn;
        TextInputLayout full_name , aadhar_no , email_id , phone_no , pswd , cnf_pswd;
        TextView already_acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        submit_btn = findViewById(R.id.signUpSubmit);
        full_name = findViewById(R.id.fullName);
        aadhar_no = findViewById(R.id.aadhar_card);
        email_id = findViewById(R.id.emailId);
        phone_no = findViewById(R.id.phoneNumber);
        pswd = findViewById(R.id.userPassword);
        cnf_pswd = findViewById(R.id.cnf_userPassword);
        already_acc = findViewById(R.id.iHaveAlreadyAnAccount);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = full_name.getEditText().getText().toString();
                int aadharNo = Integer.parseInt( aadhar_no.getEditText().getText().toString());
                String emailId  =  email_id.getEditText().getText().toString();
                int phoneNo = Integer.parseInt( phone_no.getEditText().getText().toString());
                String password = pswd.getEditText().getText().toString();
                String cnf_Password = cnf_pswd.getEditText().getText().toString();

                DatabaseConn db = new DatabaseConn(getApplicationContext() , "healthcare" , null , 1);

                if(userName.length() == 0||
                aadharNo == 0 ||
                emailId.length() == 0||
                phoneNo== 0||
                password.length() == 0 ||
                cnf_Password.length() == 0){

                    full_name.setError("Enter full Name");
                    full_name.setFocusable(true);
                    aadhar_no.setError("Aadhar No.");
                    aadhar_no.setFocusable(true);
                    email_id.setError("Email ID");
                    email_id.setFocusable(true);
                    phone_no.setError("Phone No."); phone_no.setFocusable(true);
                    pswd.setError("Enter Pswd");   pswd.setFocusable(true);
                    cnf_pswd.setError("Confirm Pswd"); cnf_pswd.setFocusable(true);
                    }
                else {

                    if (password.compareTo(cnf_Password)==0) {
                        if (passwordIsValid(password)){
                            db.userRegistration(userName , aadharNo , emailId , phoneNo , password);
                            Toast.makeText(NewRegisterActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NewRegisterActivity.this, "Password must contain atleast 8 characters , i.e Abc1233@#$", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(NewRegisterActivity.this, "Password and Confirm Password should be same", Toast.LENGTH_SHORT).show();
                    }
                   // Toast.makeText(NewRegisterActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewRegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        already_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewRegisterActivity.this  , LoginActivity.class);
                startActivity(intent);
            }
        });
   }

    public static boolean passwordIsValid(String passwordhere){
        int f1=0, f2=0,f3=0;
        if (passwordhere.length() < 8){
            return false;
        }
        else{
            for(int p = 0 ; p < passwordhere.length(); p++){
                if(Character.isLetter(passwordhere.charAt(p))){
                    f1=1;
                }
            }
            for(int d = 0 ; d < passwordhere.length(); d++ ){
                if(Character.isDigit(passwordhere.charAt(d))){
                    f2=1;
                }
            }
            for (int spl = 0 ; spl < passwordhere.length(); spl++){
                char c = passwordhere.charAt(spl);
                if(c>=33 && c<=46 || c==64){
                    f3=1;
                }
            }
            if (f1==1 && f2==1 && f3==1){
                return true;
            }
            return false;
        }



    }
}