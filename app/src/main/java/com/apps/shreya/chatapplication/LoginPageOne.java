package com.apps.shreya.chatapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;



public class LoginPageOne extends AppCompatActivity {

    TextView forgot_password, page_signup;
    LoginButton login_with_fb;
    Button login_button;
    EditText username;
    EditText password;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Toast.makeText(this, "on Create", Toast.LENGTH_SHORT).show();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_page_one);


        //intializeControls();
        //loginWithFb();

        setUser();   //checks whether the username and password is empty




        //AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //boolean isLoggedIn = accessToken != null && !accessToken.isExpired();



    }


    private void setUser()  {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ed_text = username.getText().toString().trim();
                String pass_text = password.getText().toString().trim();
                if (ed_text.isEmpty() && pass_text.isEmpty()){
                    Toast.makeText(getApplicationContext(),"plz enter your name and password ", Toast.LENGTH_LONG).show();
                }
                else if (ed_text.isEmpty()){
                    Toast.makeText(getApplicationContext(),"plz enter name",Toast.LENGTH_LONG).show();
                }
                else if (pass_text.isEmpty()){
                    Toast.makeText(getApplicationContext(),"plz enter password",Toast.LENGTH_LONG).show();
                }
                if ( password.getText().toString().length() == 6){
                    Toast.makeText(getApplicationContext(),"Password should contain atleast 6 characters",Toast.LENGTH_LONG).show();
                }


            }
        });


        page_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signing_page = new Intent(LoginPageOne.this, SignUpPage.class);
                startActivity(signing_page);
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgot_pass = new Intent(LoginPageOne.this, ForgotPasswordActivity.class);
                startActivity(forgot_pass);
            }
        });
    }


/*
    private void intializeControls(){
        callbackManager = CallbackManager.Factory.create();
        login_with_fb = (LoginButton)findViewById(R.id.login_button);
        login_button = (Button)findViewById(R.id.btn_login);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        forgot_password = (TextView)findViewById(R.id.tv_forgotpass);
        page_signup = (TextView)findViewById(R.id.tv_signup);
    }



    private void loginWithFb(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
