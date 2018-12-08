package com.apps.shreya.chatapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class LoginPageZero extends AppCompatActivity {


    private Button loginButton, signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page_zero);

        setupUIViews();     //calling the method where buttons are valued from its id

        //onClick Login button ,we will be directed to the login page
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginpage = new Intent(LoginPageZero.this, LoginPageOne.class);
                startActivity(loginpage);
            }
        });

        //onClick SignUp button ,we will be directed to signup page
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signuppage = new Intent(LoginPageZero.this, SignUpPage.class);
                startActivity(signuppage);
            }
        });
    }



    //method to assign the values from its id
    private void setupUIViews(){

        loginButton = (Button)findViewById(R.id.btn_login1);
        signUpButton = (Button)findViewById(R.id.btn_signup1);
    }
}
