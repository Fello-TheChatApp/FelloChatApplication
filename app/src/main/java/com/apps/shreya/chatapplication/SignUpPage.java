package com.apps.shreya.chatapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//helps you signup the new user
// consists of name of user,email,password
public class SignUpPage extends AppCompatActivity {

    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCreateButton;

    //firebase auth
    private FirebaseAuth mAuth;

    TextView tv_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        //FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        tv_login = (TextView)findViewById(R.id.link_login);

        mDisplayName = (TextInputLayout) findViewById(R.id.input_name);
        mEmail = (TextInputLayout) findViewById(R.id.input_email);
        mPassword = (TextInputLayout) findViewById(R.id.input_password);
        mCreateButton = (Button) findViewById(R.id.btn_signup);


        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mDisplayName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                register_user(name, email, password);   //another method to reg, passing values

            }
        });

        //For 'already a member? login' button, to go back to login page
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentlogin = new Intent(SignUpPage.this,LoginPageOne.class);
                startActivity(intentlogin);
            }
        });
    }

    public void register_user(String name, String email, String password){

        //firebase auth --> signup new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent mainIntent = new Intent(SignUpPage.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpPage.this, "Password length: 6+",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

}
