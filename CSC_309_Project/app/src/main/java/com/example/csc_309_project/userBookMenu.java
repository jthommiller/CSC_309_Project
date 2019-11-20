/*
Authors: James Miller & Matthew Abney
Date: 12/5/19
Project: Create an android app that can download and read an Ebook
 */
package com.example.csc_309_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class userBookMenu extends AppCompatActivity {

    //"F7F9F9, AFA2FF, 3C3744, C8C6AF, 808D8E"

    final static String usersLibraryTitle = "Your Library";
    final static String addToLibraryTitle = "Download Books To Your Library";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final TextView tv = findViewById(R.id.mainMenuTitle);
        tv.setText(usersLibraryTitle);

        final TabLayout tl = findViewById(R.id.navigationTabBar);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    tv.setText(usersLibraryTitle);
                }
                else{
                    tv.setText(addToLibraryTitle);
                }
                //tl.setSelectedTabIndicatorColor(Color.rgb(60, 55, 68));
                tl.setBackgroundColor(Color.rgb(60, 55, 68));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Button test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), readSelectedBook.class);
                startActivity(startIntent);
            }
        });
    }
}
