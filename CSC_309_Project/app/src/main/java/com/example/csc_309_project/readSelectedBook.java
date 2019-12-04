package com.example.csc_309_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
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

    // Loads the book
    public void loadBook(String BookName){
        String Book = "";
        try{

            FileInputStream fis = null;

            // Trys to open the book and put it into a string
            try {
                fis = openFileInput(BookName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String text;

                // Puts the saved file all into a readable form string
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

        // Fills the TextView in with the string data
        TextView bookText = findViewById(R.id.bookTextView);
        bookText.setText(Book);
    }

    // Loads the position
    public int loadPosition(String BookName){
        String Position = "0";

        FileInputStream fis = null;

        // Trys to create or replace the position file to put the data into
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

            // Closes the file system if open
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Trys to parse a number and if not a number sends 0 as a value
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

        // Trys to open the file output system to write the position in the book to a file and replace the current file
        try {
            fos = openFileOutput(BookName + " Position", MODE_PRIVATE);
            fos.write(Integer.toString(Position).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null){

                // Trys to close the file reader
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Sets up Scroll View y
    public void setScrollView(String BookName, int position){
        ScrollView BSV = findViewById(R.id.scrollView2);
        BSV.setScrollY(position);
    }

    // Sets the font size of the book to what the user wanted
    public void setFontSize(int FontSize){
        TextView bookText = findViewById(R.id.bookTextView);
        bookText.setTextSize(FontSize);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_selected_book);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Gets the Book name that should fill in the book text view
        BookName = getIntent().getStringExtra("BOOK_TITLE");
        loadBook(BookName);

        // SHOULD set the scrollview to the last saved position in the book
        setScrollView(BookName, loadPosition(BookName));

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        // Allows the user to transition back to the main menu to download or read new book
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

        // Sets the scrollview manually to the last saved position in the book
        Button setScroll = findViewById(R.id.setScroll);
        setScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setScrollView(BookName, loadPosition(BookName));
            }
        });

        // Opens the seek bar to be allow for random scrolling
        final SeekBar randomBar = findViewById(R.id.seekRandomPlaceBar);
        randomBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()  {

            // When the slider is moved left or right update the scroll view to go to new value
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView bookText = findViewById(R.id.bookTextView);

                // Math to figure out how far down we should scroll
                double location = randomBar.getProgress()/100;
                location = linesInBook*location*67;
                setScrollView(BookName, (int) location );
            }

            // Both of these are needed in order for the listener to work
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Code for changing the font size
        final EditText fontSizeText = findViewById(R.id.declaredFontSize);
        fontSizeText.addTextChangedListener(new TextWatcher() {

            // Changes the size of the font as the user types a new value in
            public void afterTextChanged(Editable s) {
                setFontSize(Integer.parseInt(fontSizeText.getText().toString()));
            }

            // Again needed for the listener to work
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


    }


}
