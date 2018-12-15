package com.apps.shreya.chatapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shreya on 16/11/2018.
 */

public class SearchFragment extends Fragment {

    private DrawerLocker mDrawerLocker; // to hide the drawer layout
   private Toolbar mToolbar;

    private RecyclerView mUsersList;

    private DatabaseReference UsersRef;

    private LinearLayoutManager mLayoutManager;

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

        View view = inflater.inflate(R.layout.fragment_search, container, false);
       mDrawerLocker.lockDrawer();// to hide the drawer layout
      //  mToolbar =(Toolbar) view.findViewById(R.id.action_bar);

        mLayoutManager=new LinearLayoutManager(getContext());

        mUsersList = (RecyclerView) view.findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(mLayoutManager);

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        return view;
    }

    // to hide the drawer layout

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDrawerLocker.unlockDrawer();
    }


    public void onStart() {

       super.onStart();

      FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(UsersRef, Users.class)

                        .build();

       FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {


        @Override
        protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {

            holder.setName(model.getName());
            holder.setUserStatus(model.getStatus());

            holder.setUserImage(Users.getThumb_image(),getActivity().getApplicationContext());

            // final String user_name=getRef(position).(model.getName());

            final   String user_id=getRef(position).getKey();
            holder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        CharSequence options[] = new CharSequence[]{"Open Profile", "Send message"};

                                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                        builder.setTitle("Select Options");
                                        builder.setItems(options, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                //Click Event for each item.
                                                if(i == 0){

                                                    Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
                                                    profileIntent.putExtra("user_id", user_id);
                                                    startActivity(profileIntent);

                                                }

                                                if(i == 1){

                                                    Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                                    chatIntent.putExtra("user_id", user_id);

                                                    //TO DISPLAY USERS NAME ON TOOLBAR DURING CHAT
                                                    //           chatIntent.putExtra("user_name", model.getName());


                                                    startActivity(chatIntent);

                                                }

                                            }
                                        });

                                        builder.show();

                                    }
                                });
            //Picasso.with(getActivity()).load(model.getImage()).into(holder.profileImage);
       }

       @NonNull
       @Override
       public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
         View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_single_layout, viewGroup,false );
       SearchFragment.UsersViewHolder viewHolder = new SearchFragment.UsersViewHolder(view);
       return viewHolder;
       }                  };

       mUsersList.setAdapter(firebaseRecyclerAdapter);
       firebaseRecyclerAdapter.startListening();

   }

    public  static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;
        TextView userNameView, userStatus;
        //CircleImageView userImageView;


        public UsersViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

        }

        public  void setName (String name)
        {

            userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);


        }

        public void setUserStatus(String status)


        {
            userStatus = (TextView) mView.findViewById(R.id.user_single_status);
            userStatus.setText(status);
        }

        public void setUserImage(String thumb_image,Context cxt)
        {
            CircleImageView userImageView=(CircleImageView) mView.findViewById(R.id.user_single_image);
            Picasso.with(cxt).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);
        }
    }

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

