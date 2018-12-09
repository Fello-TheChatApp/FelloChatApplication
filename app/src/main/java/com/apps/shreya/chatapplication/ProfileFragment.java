package com.apps.shreya.chatapplication;

import android.app.ProgressDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static java.lang.System.load;


/**
 * Created by Shreya on 16/11/2018.
 */

public class ProfileFragment extends Fragment  {

    CircleImageView mDisplayImage;
    TextView mName;
    private TextView mStatus;

    private Button mStatusBtn;
    private Button mImageBtn;

private static final int GALLERY_PICK=1;

private StorageReference mImageStorage;
private ProgressDialog mProgressDialog;


    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    public ProfileFragment() {
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mDisplayImage = view.findViewById(R.id.profile_pic);
        mName = view.findViewById(R.id.username);
        mStatus = view.findViewById(R.id.default_intro);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        mUserDatabase= FirebaseDatabase.getInstance().getReference("UsersActivity").child(mCurrentUser.getUid());




       mStatusBtn=(Button)view.findViewById(R.id.settings_status_btn);
mImageBtn=(Button) view.findViewById(R.id.settings_image_btn);

mImageStorage=FirebaseStorage.getInstance().getReference();



        String current_uid = mCurrentUser.getUid();

        mUserDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
         mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mName.setText(name);
                mStatus.setText(status);

                Picasso.get().load(image).into(mDisplayImage);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


            

        });
         mStatusBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                String status_value=mStatus.getText().toString();
                 //Intent status_intent=new Intent(getContext(),StatusActivity.class);

                 Intent intent = new Intent(getContext(),StatusActivity.class);
                 intent.putExtra("status_value",status_value);
                 startActivity(intent);

             }
         });

         mImageBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent galleryIntent=new Intent();
                 galleryIntent.setType("image/*");
                 galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(Intent.createChooser(galleryIntent,"SELECT_IMAGE"),GALLERY_PICK);


               /*  // start picker to get image for cropping and then use the image in cropping activity
                 CropImage.activity()
                         .setGuidelines(CropImageView.Guidelines.ON)
                         .start(getActivity());  */
             }
         });










        return view;



    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(getContext(), this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                 mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setTitle("Uploading Image");
                mProgressDialog.setMessage("Please wait until we process and upload the image ");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                Uri resultUri = result.getUri();
                String current_user_id=mCurrentUser.getUid();
                StorageReference filepath= mImageStorage.child("profile_images").child(current_user_id+".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            String download_url=task.getResult().getStorage().getDownloadUrl().toString();
                            mUserDatabase.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Success Uploading", Toast.LENGTH_LONG).show();
                                }}
                            });

                           // Toast.makeText(getActivity(), "Working", Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error in uploading", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }
    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
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


    //
}
