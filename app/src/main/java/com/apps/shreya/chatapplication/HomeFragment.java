package com.apps.shreya.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Shreya on 16/11/2018.
 */

public class HomeFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);



    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        //TO OPEN NEW ACTIVITY ON MENU ITEM CLICK
        Intent intent = new Intent(getActivity(), TopicsActiviy.class);
        startActivity(intent);
        getActivity().overridePendingTransition( R.anim.slide_up, R.anim.stay );

        //finish();
        //overridePendingTransition(R.anim.stay, R.anim.slide_down);
        return true;
        //return super.onOptionsItemSelected(item);
    }


}
