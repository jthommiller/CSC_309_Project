package com.example.csc_309_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.text.Transliterator;
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

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        ScrollView book = findViewById(R.id.scrollView2);
        book.setScrollY(loadPosition());

        ImageButton returnToLibrary = findViewById(R.id.returnToUserBookMenu);
        returnToLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), userBookMenu.class);
                startActivity(startIntent);
            }
        });
    }
}
