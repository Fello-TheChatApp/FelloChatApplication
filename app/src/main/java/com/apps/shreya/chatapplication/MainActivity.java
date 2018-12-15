package com.apps.shreya.chatapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements DrawerLocker  {

    // for the navigation drawer
    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mToggle;

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private DatabaseReference mUserRef;
    private DatabaseReference mUserDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String user_id = getIntent().getStringExtra("user_id");

        //initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();


        //try adding this IF APP CRASHES
        if(mAuth.getCurrentUser() != null) {
            mUserDatabase = FirebaseDatabase.getInstance()
                    .getReference().child("Users").child(mAuth.getCurrentUser().getUid());}


        // for the toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //USING BOTTOM NAVIGATION VIEW HELPER CLASS TO HIDE ICON ANIMATION IN BOTTOM NAV BAR
        BottomNavigationViewHelper.disableShiftMode(bottomNav);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }


        RootRef = FirebaseDatabase.getInstance().getReference();

        // to initiate the navigation drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//                if (item.getItemId() == R.id.main_logout_btn) {
//                    FirebaseAuth.getInstance().signOut();
//                    sendToStart();
//                }
//
//                if (item.getItemId() == R.id.main_create_group_option) {
//                    RequestNewGroup();
//                }

                switch (item.getItemId()){
                    case R.id.main_logout_btn:
                        FirebaseAuth.getInstance().signOut();
                        sendToStart();
                        break;

                    case R.id.main_create_group_option:
                        RequestNewGroup();
                        break;
                }
                return true;
            }
        });
//        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                        case R.id.nav_friends:
                            selectedFragment = new FriendsFragment();
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

        if (currentUser == null) {
            sendToStart();
          }
// else {
//
//            mUserRef.child("online").setValue("true");
//
//        }
    }

//    protected void onStop() {
//        super.onStop();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
////        if(currentUser != null) {
////
////            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
////
////        }
//    }

    public void sendToStart() {
        Intent startintent = new Intent(MainActivity.this, LoginPageZero.class);
        startActivity(startintent);
        finish();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        if (item.getItemId() == R.id.main_logout_btn) {
//            FirebaseAuth.getInstance().signOut();
//            sendToStart();
//        }
//
//        if (item.getItemId() == R.id.main_create_group_option) {
//            RequestNewGroup();
//        }
//
////        if (mToggle.onOptionsItemSelected(item)) {
////            return true;
////        }
//        return true;
//    }


    private void RequestNewGroup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Group Name");

        final EditText groupNameField = new EditText(MainActivity.this);
        groupNameField.setHint("e.g Movies");
        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String groupName = groupNameField.getText().toString();

                if (TextUtils.isEmpty(groupName)) {
                    Toast.makeText(MainActivity.this, "Please write group name", Toast.LENGTH_LONG).show();


                } else {
                    CreateNewGroup(groupName);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }

        });
        builder.show();

    }

    private void CreateNewGroup(final String groupName) {
        RootRef = FirebaseDatabase.getInstance().getReference().child("Groups");

        RootRef.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, " group is created Successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.menu,menu);
//        return super.onCreateOptionsMenu(menu);
//
//    }

 //   @Override
 //   public boolean onOptionsItemSelected(MenuItem item) {

        //TO OPEN NEW ACTIVITY ON MENU ITEM CLICK
//        Intent myintent = new Intent(MainActivity.this, TopicsActiviy.class);
//        startActivity(myintent);
//        overridePendingTransition(R.anim.slide_up, R.anim.stay);

        //finish();
        //overridePendingTransition(R.anim.stay, R.anim.slide_down);
//        return true;
        //return super.onOptionsItemSelected(item);


 //   }


    @Override
    public void lockDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void unlockDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

}

