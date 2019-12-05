package com.example.csc_309_project;

import android.content.Context;
import android.content.pm.ActivityInfo;
<<<<<<< Updated upstream
import android.icu.text.Transliterator;
import android.os.Bundle;
=======
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
>>>>>>> Stashed changes
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
<<<<<<< Updated upstream
=======
import android.widget.EditText;
import android.widget.FrameLayout;
>>>>>>> Stashed changes
import android.widget.ImageButton;

import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class readSelectedBook extends AppCompatActivity {

<<<<<<< Updated upstream
=======
    static int linesInBook = 0;
>>>>>>> Stashed changes
    String BookName = "";

    // Loades the book from storage
    public String loadBook(){
        String Book = "";

        FileInputStream fis = null;

        try {
            fis = openFileInput(BookName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            // Puts the saved file all into a readable form
            while((text = br.readLine()) != null){
                Book += text + "\n";
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

        return Book;
    }

    // Loads position in the book
    public int loadPosition(){
        int position = 10;
        FileInputStream fis = null;
        final String FileName = "Position";

        try {
            fis = openFileInput(FileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            // Puts the saved file all into a readable form
            while((text = br.readLine()) != null){
                if ( text.equals(BookName) ){
                    position = Integer.parseInt(br.readLine());
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
        return position;
    }

    String[][] Position;
    // Saves position in the book
    public void savePosition(String BookName, int BookPosition){
        userBookMenu USBM = new userBookMenu();
        Position = USBM.loadPositionArray(Position);
        Position = USBM.deletePosition(BookName, Position);


        // Sets up file output stream to be able to save the book
        final String FileName = "Position";
        FileOutputStream fos = null;
        String positionList = BookName + "\n" + BookPosition;
        for (int i = 0; i < Position.length; i++ ){
            positionList += Position[i][0] + "\n" + Position[i][1];
        }
        try {
            fos = openFileOutput(FileName, MODE_PRIVATE);
            fos.write(positionList.getBytes());
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
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_selected_book);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

<<<<<<< Updated upstream
        Intent selectedBookToRead = getIntent();
        String title = selectedBookToRead.getStringExtra("BOOK_TITLE");
=======
        TextView book = findViewById(R.id.bookTextView);
        FrameLayout loadingScreen = findViewById(R.id.loadingScreen);

        // Gets the Book name that should fill in the book text view
        BookName = getIntent().getStringExtra("BOOK_TITLE");
>>>>>>> Stashed changes

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

<<<<<<< Updated upstream
        ScrollView book = findViewById(R.id.scrollView2);
        book.setScrollY(loadPosition());

=======

/*
        // Gets the Book name that should fill in the book text view
        loadBookThread.start();
*/
        // SHOULD set the scrollview to the last saved position in the book
        setScrollView(BookName, loadPosition(BookName));

        // Allows the user to transition back to the main menu to download or read new book
>>>>>>> Stashed changes
        ImageButton returnToLibrary = findViewById(R.id.returnToUserBookMenu);
        returnToLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< Updated upstream
                Intent startIntent = new Intent(getApplicationContext(), userBookMenu.class);
                startActivity(startIntent);
            }
        });
    }
=======
                ScrollView BSV = findViewById(R.id.scrollView2);
                savePosition(BSV.getScrollY(), BookName);
                finish();
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
        setScroll.setClickable(false);

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
        fontSizeText.setSelectAllOnFocus(true);
        fontSizeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_DONE) {
                    setFontSize(Integer.parseInt(fontSizeText.getText().toString()));;
                    handled = true;
                }
                return handled;
            }
        });
        fontSizeText.setActivated(false);
        fontSizeText.setClickable(false);

        new LoadBook(this, loadingScreen, BookName, book).execute();

        fontSizeText.setClickable(false);
        setScroll.setClickable(false);
    }


}

class LoadBook extends AsyncTask<String, String, String> {
    Context context;
    FrameLayout loadingScreen;
    String bookname;
    TextView book;

    LoadBook(Context context, FrameLayout loadingScreen, String bookname, TextView book) {
        this.context = context;
        this.loadingScreen = loadingScreen;
        this.bookname = bookname;
        this.book = book;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        loadingScreen.setAnimation(inAnimation);
        loadingScreen.setVisibility(View.VISIBLE);
    }

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
                    readSelectedBook.linesInBook++;
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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        book.setText(result);
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        loadingScreen.setAnimation(outAnimation);
        loadingScreen.setVisibility(View.GONE);
    }
>>>>>>> Stashed changes
}
