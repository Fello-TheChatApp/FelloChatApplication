package com.apps.shreya.chatapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shreya on 16/11/2018.
 */

public class NotifFragment extends Fragment {

    private DrawerLocker mDrawerLocker; // to hide the drawer layout
 //  private ActionBarDrawerToggle mToggle;



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

        return inflater.inflate(R.layout.fragment_notif, container, false);





    }



    // to hide the drawer layout

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDrawerLocker.unlockDrawer();
    }

}
