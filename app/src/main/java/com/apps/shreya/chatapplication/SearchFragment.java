package com.apps.shreya.chatapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    private Toolbar mToolbar;

    private RecyclerView allUsersList;

    private DatabaseReference UsersRef;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mToolbar = view.findViewById(R.id.action_bar);

        allUsersList = view.findViewById(R.id.users_list);
        allUsersList.setHasFixedSize(true);
        allUsersList.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        UsersRef = FirebaseDatabase.getInstance().getReference().child("UsersActivity");


        return view;
    }

    public void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(UsersRef, Users.class)
                        .build();

        FirebaseRecyclerAdapter<Users, UsersViewHolder> adapter =
                new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
                        holder.userName.setText(model.getName());
                        holder.userStatus.setText(model.getStatus());
                        Picasso.get().load(model.getImage()).into(holder.profileImage);
                    }

                    @NonNull
                    @Override
                    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_single_layout, viewGroup,false );
                        SearchFragment.UsersViewHolder viewHolder = new SearchFragment.UsersViewHolder(view);
                        return viewHolder;
                    }
                };

        allUsersList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView userName, userStatus;
        CircleImageView profileImage;

        public UsersViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.user_single_name);
            userStatus = (TextView) itemView.findViewById(R.id.user_single_status);
            profileImage = (CircleImageView) itemView.findViewById(R.id.user_single_image);
        }
    }
}
