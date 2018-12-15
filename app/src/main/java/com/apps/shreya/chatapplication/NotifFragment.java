package com.apps.shreya.chatapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Shreya on 16/11/2018.
 */

public class NotifFragment extends Fragment {

    private DrawerLocker mDrawerLocker; // to hide the drawer layout
 //  private ActionBarDrawerToggle mToggle;
private ListView list_view;
private ArrayAdapter<String> arrayAdapter;
private ArrayList<String> list_of_groups=new ArrayList<>();
private View groupFragmentView;
private Toolbar mToolbar;
private DatabaseReference GroupRef;


   public  NotifFragment()
    {

    }

    //-------------- to lock the drawer layout----------------------
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mDrawerLocker = (DrawerLocker) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DrawerLocker");
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDrawerLocker.lockDrawer();// to hide the drawer layout
      //  mToggle.setDrawerIndicatorEnabled(false);

        groupFragmentView= inflater.inflate(R.layout.fragment_notif, container, false);

       GroupRef=FirebaseDatabase.getInstance().getReference().child("Groups").child("Groups");

        InitializeFields();
        
        
        RetrieveAndDisplayGroups();

list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String currentGroupName=adapterView.getItemAtPosition(position).toString();

        Intent groupChatIntent=new Intent(getActivity(),GroupChatActivity.class);
      groupChatIntent.putExtra("groupName",currentGroupName);
        startActivity(groupChatIntent);



    }
});



        return groupFragmentView;



    }



    private void InitializeFields() {

       list_view=(ListView) groupFragmentView.findViewById(R.id.list_view);
       arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list_of_groups);
       list_view.setAdapter(arrayAdapter);

    }


    private void RetrieveAndDisplayGroups() {

       GroupRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              Set<String> set=new HashSet<>();

               Iterator iterator= (Iterator) dataSnapshot.getChildren().iterator();
          while(iterator.hasNext())
          {
set.add(((DataSnapshot)iterator.next()).getKey());

          }

          list_of_groups.clear();
          list_of_groups.addAll(set);
          arrayAdapter.notifyDataSetChanged();

           }


           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }


    // to hide the drawer layout

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDrawerLocker.unlockDrawer();
    }
//
    //TO HIDE TOOLBAR FROM FRAGMENT
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
   }

}
