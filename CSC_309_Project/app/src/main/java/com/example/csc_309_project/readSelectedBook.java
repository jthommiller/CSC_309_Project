package com.example.csc_309_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.text.Transliterator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class readSelectedBook extends AppCompatActivity {

    int linesInBook = 0;
    String BookName = "";
    boolean canSetScroll = false;

    // Loads the book
    public void loadBook(String BookName){
        String Book = "";
        try{

            FileInputStream fis = null;

            try {
                fis = openFileInput(BookName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String text;

                // Puts the saved file all into a readable form
                while((text = br.readLine()) != null){
                    Book += text + "\n";
                    linesInBook++;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        TextView bookText = findViewById(R.id.bookTextView);
        bookText.setText(Book);
        canSetScroll = true;
    }

    // Loads the position
    public int loadPosition(String BookName){
        String Position = "0";

        FileInputStream fis = null;

        try {
            fis = openFileInput(BookName + " Position");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            // Puts the saved file all into a readable form
            if ((text = br.readLine()) != null){
                Position = text;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try{
            return Integer.parseInt(Position);
        } catch (Exception e) {
            return 0;
        }
    }

    // Saves the position
    public void savePosition(int Position, String BookName){
        // Sets up file output stream to be able to save the position
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(BookName + " Position", MODE_PRIVATE);
            fos.write(Integer.toString(Position).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Sets up Scroll View y
    public void setScrollView(String BookName){
        int position = loadPosition(BookName);
        System.out.println(position);
        boolean notDone = true;
        TextView bookText = findViewById(R.id.bookTextView);
        while ( notDone ){
            if ( canSetScroll ){
                ScrollView BSV = findViewById(R.id.scrollView2);
                BSV.setScrollY(position);
                notDone = !notDone;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_selected_book);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        BookName = getIntent().getStringExtra("BOOK_TITLE");
        loadBook(BookName);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );



        ImageButton returnToLibrary = findViewById(R.id.returnToUserBookMenu);
        returnToLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView BSV = findViewById(R.id.scrollView2);
                savePosition(BSV.getScrollY(), BookName);
                Intent startIntent = new Intent(getApplicationContext(), userBookMenu.class);
                startActivity(startIntent);
            }
        });

        Button setScroll = findViewById(R.id.setScroll);
        setScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setScrollView(BookName);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setScrollView(BookName);
    }
}
