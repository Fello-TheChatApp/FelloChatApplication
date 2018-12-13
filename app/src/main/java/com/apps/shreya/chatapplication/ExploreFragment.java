package com.apps.shreya.chatapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ExploreFragment extends Fragment {


    private View groupFragmentView;
    private ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_groups=new ArrayList<>();

   private DatabaseReference GroupRef;

    public ExploreFragment()
    {

    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        groupFragmentView= inflater.inflate(R.layout.fragment_explore, container, false);

        GroupRef=FirebaseDatabase.getInstance().getReference().child("Groups");



        return groupFragmentView;

    }




}
