package com.example.csc_309_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.transform.Result;

public class readSelectedBook extends AppCompatActivity {

    // Global Variable Initialization
    String BookName = "";
    static int linesInBook = 0;

    // Sets up Scroll View y
    public void setScrollView(int position){
        ScrollView BSV = findViewById(R.id.scrollView2);
        BSV.setScrollY(position);
    }

    // Sets the font size of the book to what the user wanted
    public void setFontSize(int FontSize){
        TextView bookText = findViewById(R.id.bookTextView);
        bookText.setTextSize(FontSize);
    }

    // Saves the position
    public void savePosition(int Position, String BookName){
        // Sets up file output stream to be able to save the position
        FileOutputStream fos = null;

        // Trys to open the file output system to write the position in the book to a file and replace the current file
        try {
            fos = openFileOutput(BookName + " Position", MODE_PRIVATE);
            fos.write(Integer.toString(Position).getBytes());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
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
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_selected_book);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Prevents the soft keyboard from being displayed until User wants to change font
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        // Gets the Book name that should fill in the book text view
        BookName = getIntent().getStringExtra("BOOK_TITLE");

        // Intializes Variables for event handlers
        Button setScroll = findViewById(R.id.setScroll);
        final EditText fontSizeText = findViewById(R.id.declaredFontSize);
        FrameLayout loadingScreen = findViewById(R.id.loadingScreen);
        ImageButton returnToLibrary = findViewById(R.id.returnToUserBookMenu);
        final SeekBar randomBar = findViewById(R.id.seekRandomPlaceBar);
        TextView book = findViewById(R.id.bookTextView);

        // Sets the scrollview manually to the last saved position in the book
        setScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setScrollView(loadPosition(BookName));
            }
        });

        // Code for changing the font size
        fontSizeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    setFontSize(Integer.parseInt(fontSizeText.getText().toString()));;
                    handled = true;
                    fontSizeText.setCursorVisible(false);
                }
                return handled;
            }
        });

        // Code to display cursor for font Edit Text View
        fontSizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontSizeText.setCursorVisible(true);
            }
        });

        // Allows the user to transition back to the main menu to download or read new book
        returnToLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView BSV = findViewById(R.id.scrollView2);
                savePosition(BSV.getScrollY(), BookName);

                //Intent result = new Intent();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });


        // Opens the seek bar to be allow for random scrolling
        randomBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()  {

            // When the slider is moved left or right update the scroll view to go to new value
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                double location = randomBar.getProgress();
                if ( location == 0 ){
                    setScrollView( 0 );
                } else {
                    location = location/100;
                    location = linesInBook*location*67;
                    System.out.println(linesInBook + " lines");
                    setScrollView( (int) location );
                }
            }
            // Both of these are needed in order for the listener to work
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Starts AsyncTask to load book
        new LoadBook(this, loadingScreen, BookName, book, setScroll).execute();
    }


}

// Async to load books
class LoadBook extends AsyncTask<String, String, String> {

    // Book Initialization
    Button scroll;
    Context context;
    FrameLayout loadingScreen;
    String bookname;
    TextView book;

    // Sets up our variables with values
    LoadBook(Context context, FrameLayout loadingScreen, String bookname, TextView book, Button scroll) {
        this.context = context;
        this.loadingScreen = loadingScreen;
        this.bookname = bookname;
        this.book = book;
        this.scroll = scroll;
    }

    // Darken everything out and show progress bar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        scroll.setClickable(false);
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        loadingScreen.setAnimation(inAnimation);
        loadingScreen.setVisibility(View.VISIBLE);
    }

    // Loads the book
    @Override
    protected String doInBackground(String... strings) {
        String Book = "";
        try{

            FileInputStream fis = null;

            // Trys to open the book and put it into a string
            try {
                fis = context.openFileInput(bookname);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String text;

                // Puts the saved file all into a readable form string
                while((text = br.readLine()) != null){
                    Book += text + "\n";
                    readSelectedBook.linesInBook += 1;
                    if(isCancelled()){
                        break;
                    }
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
        return Book;
    }

    // When done get rid or progress bar
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        book.setText(result);
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        loadingScreen.setAnimation(outAnimation);
        loadingScreen.setVisibility(View.GONE);
        scroll.setClickable(true);
    }
}
