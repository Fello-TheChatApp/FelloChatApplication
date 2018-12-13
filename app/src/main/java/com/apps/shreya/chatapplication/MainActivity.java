package com.apps.shreya.chatapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //initialize the FirebaseAuth instance.
            mAuth = FirebaseAuth.getInstance();

            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.action_bar);
            setSupportActionBar(toolbar);

            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setOnNavigationItemSelectedListener(navListener);

            //USING BOTTOM NAVIGATION VIEW HELPER CLASS TO HIDE ICON ANIMATION IN BOTTOM NAV BAR
            BottomNavigationViewHelper.disableShiftMode(bottomNav);

            //I added this if statement to keep the selected fragment when rotating the device
                 if (savedInstanceState == null)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                 }
        }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_explore:
                            selectedFragment = new ExploreFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_notif:
                            selectedFragment = new NotifFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    //Firebase Auth When initializing your Activity, check to see if the user is currently signed in.

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
           sendToStart();
        }
    }

    public void sendToStart(){
        Intent startintent=new Intent(MainActivity.this,LoginPageZero.class);
        startActivity(startintent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.main_logout_btn){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }


        return true;
    }


}

    /**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //TO OPEN NEW ACTIVITY ON MENU ITEM CLICK
        Intent myintent=new Intent (MainActivity.this,TopicsActiviy.class);
        startActivity(myintent);
        overridePendingTransition(R.anim.slide_up, R.anim.stay);

        //finish();
        //overridePendingTransition(R.anim.stay, R.anim.slide_down);
        return true;
        //return super.onOptionsItemSelected(item);
    }
**/
