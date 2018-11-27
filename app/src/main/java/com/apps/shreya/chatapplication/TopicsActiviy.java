package com.apps.shreya.chatapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import com.apps.shreya.chatapplication.topics.ArtActivity;
import com.apps.shreya.chatapplication.topics.EducationActivity;
import com.apps.shreya.chatapplication.topics.EntertainmentActivity;
import com.apps.shreya.chatapplication.topics.FashionActivity;
import com.apps.shreya.chatapplication.topics.MusicActivity;
import com.apps.shreya.chatapplication.topics.PersonalActivity;
import com.apps.shreya.chatapplication.topics.ScienceActivity;
import com.apps.shreya.chatapplication.topics.ThoughtsActivity;
import com.apps.shreya.chatapplication.topics.DatingActivity;
import com.apps.shreya.chatapplication.topics.PoliticsActivity;
import com.r0adkll.slidr.Slidr;

public class TopicsActiviy extends AppCompatActivity
    {

        GridLayout mainGrid;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_topics_activiy);

            Slidr.attach(this);
            android.support.v7.widget.Toolbar toolbar1 = findViewById(R.id.action_bar2);
            setSupportActionBar(toolbar1);

            mainGrid= (GridLayout) findViewById(R.id.mainGrid);

            setSingleEvent(mainGrid);
        }


        private void setSingleEvent(GridLayout mainGrid)
        {
                //Loop all the child items of Main Grid

            for(int i=0;i<mainGrid.getChildCount();i++)
                {
                    //You can see, all child item is CardView, so that we just cast object to CardView
                    CardView cardView=(CardView)mainGrid.getChildAt(i);
                    final int finalI=i;
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(finalI==0)    //open activity 1, art activity
                                {
                                    Intent intent=new Intent(TopicsActiviy.this,ArtActivity.class);
                                    startActivity(intent);
                                }

                            else if(finalI==1)    //open Education activity
                            {
                                Intent intent=new Intent(TopicsActiviy.this,EducationActivity.class);
                                startActivity(intent);
                            }

                            else if(finalI==2)    //open Entertainment activity
                            {
                                Intent intent=new Intent(TopicsActiviy.this, EntertainmentActivity.class);
                                startActivity(intent);
                            }

                            else if(finalI==3)    //open Fashion activity
                            {
                                Intent intent=new Intent(TopicsActiviy.this, FashionActivity.class);
                                startActivity(intent);
                            }

                            else if(finalI==4)    //open Dating activity
                            {
                                Intent intent=new Intent(TopicsActiviy.this, DatingActivity.class);
                                startActivity(intent);
                            }

                            if(finalI==5)    //open Music activity
                            {
                                Intent intent=new Intent(TopicsActiviy.this,MusicActivity.class);
                                startActivity(intent);
                            }

                            else if(finalI==6)    //open Politics activity
                            {
                                Intent intent=new Intent(TopicsActiviy.this,PoliticsActivity.class);
                                startActivity(intent);
                            }

                            else if(finalI==7)    //open Personal activity
                            {
                                Intent intent=new Intent(TopicsActiviy.this, PersonalActivity.class);
                                startActivity(intent);
                            }

                            else if(finalI==8)    //open Thoughts activity
                            {
                                Intent intent=new Intent(TopicsActiviy.this, ThoughtsActivity.class);
                                startActivity(intent);
                            }

                            else if(finalI==9)    //open Science activity
                            {
                                Intent intent=new Intent(TopicsActiviy.this, ScienceActivity.class);
                                startActivity(intent);
                            }



                        }
                    });
                }



            }



    }
