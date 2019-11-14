/*
Authors: James Miller & Matthew Abney
Date: 12/5/19
Project: Create an android app that can download and read an Ebook
 */
package com.example.csc_309_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userBookMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
