/*
Authors: James Miller & Matthew Abney
Date: 12/5/19
Project: Create an android app that can download and read an Ebook
 */
package com.example.csc_309_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import org.w3c.dom.Text;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.prefs.Preferences;

public class userBookMenu extends AppCompatActivity {

    // Array of the books
    // BookName, Author, DownloadLink, DownloadedOrNot(true or false)
    String[][] Books = {
            {"Adventures of Huckleberry Finn", "Mark Twain", "https://www.gutenberg.org/files/76/76-0.txt", "false"},
            {"The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "https://www.gutenberg.org/files/1661/1661-0.txt", "false"},
            {"The Adventures of Tom Sawyer", "Mark Twain", "https://www.gutenberg.org/files/74/74-0.txt", "false"},
            {"Alice's Adventures in Wonderland", "Lewis Carroll", "https://www.gutenberg.org/files/11/11-0.txt", "false"},
            {"Also sprach Zarathustra. English", "Friedrich Wilhelm Nietzsche", "https://www.gutenberg.org/files/1998/1998-0.txt", "false"},
            {"An Occurrence at Owl Creek Bridge", "Ambrose Bierce", "https://www.gutenberg.org/cache/epub/375/pg375.txt", "false"},
            {"Anne of Green Gables", "L. M.  Montgomery", "https://www.gutenberg.org/files/45/45-0.txt", "false"},
            {"Anthem", "Ayn Rand", "https://www.gutenberg.org/cache/epub/1250/pg1250.txt", "false"},
            {"Autobiography of Benjamin Franklin", "Benjamin Franklin", "https://www.gutenberg.org/cache/epub/20203/pg20203.txt", "false"},
            {"The Awakening, and Selected Short Stories", "Kate Chopin", "https://www.gutenberg.org/files/160/160-0.txt", "false"},
            {"Beowulf: An Anglo-Saxon Epic Poem", "", "https://www.gutenberg.org/cache/epub/16328/pg16328.txt", "false"},
            {"Beyond Good and Evil", "Friedrich Wilhelm Nietzsche", "https://www.gutenberg.org/cache/epub/4363/pg4363.txt", "false"},
            {"The Brothers Karamazov", "Fyodor Dostoyevsky", "https://www.gutenberg.org/files/28054/28054-0.txt", "false"},
            {"Candide", "Voltaire", "https://www.gutenberg.org/cache/epub/19942/pg19942.txt", "false"},
            {"The Chaldean Account of Genesis", "A. H.  Sayce and George Smith", "https://www.gutenberg.org/files/60559/60559-0.txt", "false"},
            {"A Christmas Carol in Prose; Being a Ghost Story of Christmas", "Charles Dickens", "https://www.gutenberg.org/files/46/46-0.txt", "false"},
            {"Common Sense", "Thomas Paine", "https://www.gutenberg.org/cache/epub/147/pg147.txt", "false"},
            {"The Complete Works of William Shakespeare", "William Shakespeare", "https://www.gutenberg.org/files/100/100-0.txt", "false"},
            {"The Confessions of St. Augustine", "Bishop of Hippo Saint Augustine", "https://www.gutenberg.org/cache/epub/3296/pg3296.txt", "false"},
            {"The Count of Monte Cristo", "Alexandre Dumas", "https://www.gutenberg.org/files/1184/1184-0.txt", "false"},
            {"Crime and Punishment", "Fyodor Dostoyevsky", "https://www.gutenberg.org/files/2554/2554-0.txt", "false"},
            {"The Criticism of the Fourth Gospel", "W.  Sanday", "https://www.gutenberg.org/files/60553/60553-0.txt", "false"},
            {"Democracy in America — Volume 1", "Alexis de Tocqueville", "https://www.gutenberg.org/cache/epub/815/pg815.txt", "false"},
            {"The Devil's Dictionary", "Ambrose Bierce", "https://www.gutenberg.org/cache/epub/972/pg972.txt", "false"},
            {"Divine Comedy, Longfellow's Translation, Hell", "Dante Alighieri", "https://www.gutenberg.org/cache/epub/1001/pg1001.txt", "false"},
            {"A Doll's House: a play", "Henrik Ibsen", "https://www.gutenberg.org/cache/epub/2542/pg2542.txt", "false"},
            {"Dracula", "Bram Stoker", "https://www.gutenberg.org/cache/epub/345/pg345.txt", "false"},
            {"Dubliners", "James Joyce", "https://www.gutenberg.org/files/2814/2814-0.txt", "false"},
            {"Emma", "Jane Austen", "https://www.gutenberg.org/files/158/158-0.txt", "false"},
            {"Essays by Ralph Waldo Emerson", "Ralph Waldo Emerson", "https://www.gutenberg.org/cache/epub/16643/pg16643.txt", "false"},
            {"Essays of Michel de Montaigne — Complete", "Michel de Montaigne", "https://www.gutenberg.org/files/3600/3600-0.txt", "false"},
            {"Ethan Frome", "Edith Wharton", "https://www.gutenberg.org/files/4517/4517-0.txt", "false"},
            {"Frankenstein; Or, The Modern Prometheus", "Mary Wollstonecraft Shelley", "https://www.gutenberg.org/files/84/84-0.txt", "false"},
            {"Great Expectations", "Charles Dickens", "https://www.gutenberg.org/files/1400/1400-0.txt", "false"},
            {"Grimms' Fairy Tales", "Jacob Grimm and Wilhelm Grimm", "https://www.gutenberg.org/files/2591/2591-0.txt", "false"},
            {"Gulliver's Travels into Several Remote Nations of the World", "Jonathan Swift", "https://www.gutenberg.org/files/829/829-0.txt", "false"},
            {"Heart of Darkness", "Joseph Conrad", "https://www.gutenberg.org/files/219/219-0.txt", "false"},
            {"The Hound of the Baskervilles", "Arthur Conan Doyle", "https://www.gutenberg.org/files/2852/2852-0.txt", "false"},
            {"How the Other Half Lives: Studies Among the Tenements of New York", "Jacob A.  Riis", "https://www.gutenberg.org/cache/epub/45502/pg45502.txt", "false"},
            {"The Iliad", "Homer", "https://www.gutenberg.org/cache/epub/6130/pg6130.txt", "false"},
            {"The Importance of Being Earnest: A Trivial Comedy for Serious People", "Oscar Wilde", "https://www.gutenberg.org/cache/epub/844/pg844.txt", "false"},
            {"Incidents in the Life of a Slave Girl, Written by Herself", "Harriet A.  Jacobs", "https://www.gutenberg.org/ebooks/11030.txt.utf-8", "false"},
            {"The Interesting Narrative of the Life of Olaudah Equiano, Or Gustavus Vassa, The African", "Equiano", "https://www.gutenberg.org/cache/epub/15399/pg15399.txt", "false"},
            {"Ion", "Plato", "https://www.gutenberg.org/ebooks/1635.txt.utf-8", "false"},
            {"Jane Eyre: An Autobiography", "Charlotte Brontë", "https://www.gutenberg.org/ebooks/1260.txt.utf-8", "false"},
            {"The Jungle", "Upton Sinclair", "https://www.gutenberg.org/files/140/140-0.txt", "false"},
            {"The Kama Sutra of Vatsyayana", "Vatsyayana", "https://www.gutenberg.org/ebooks/27827.txt.utf-8", "false"},
            {"Le Morte d'Arthur: Volume 1", "Sir Thomas Malory", "https://www.gutenberg.org/files/1251/1251-0.txt", "false"},
            {"Leaves of Grass", "Walt Whitman", "https://www.gutenberg.org/files/1322/1322-0.txt", "false"},
            {"The Legend of Sleepy Hollow", "Washington Irving", "https://www.gutenberg.org/files/41/41-0.txt", "false"},
            {"Les Misérables", "Victor Hugo", "https://www.gutenberg.org/files/135/135-0.txt", "false"},
            {"Leviathan", "Thomas Hobbes", "https://www.gutenberg.org/ebooks/3207.txt.utf-8", "false"},
            {"The Life and Adventures of Robinson Crusoe", "Daniel Defoe", "https://www.gutenberg.org/files/521/521-0.txt", "false"},
            {"Little Women", "Louisa May Alcott", "https://www.gutenberg.org/ebooks/514.txt.utf-8", "false"},
            {"Metamorphosis", "Franz Kafka", "https://www.gutenberg.org/ebooks/5200.txt.utf-8", "false"},
            {"Moby Dick; Or, The Whale", "Herman Melville", "https://www.gutenberg.org/files/2701/2701-0.txt", "false"},
            {"Modern Copper Smelting", "Donald M. Levy", "https://www.gutenberg.org/files/59328/59328-0.txt", "false"},
            {"A Modest Proposal", "Jonathan Swift", "https://www.gutenberg.org/files/1080/1080-0.txt", "false"},
            {"Narrative of the Captivity and Restoration of Mrs. Mary Rowlandson", "Mary White Rowlandson", "https://www.gutenberg.org/ebooks/851.txt.utf-8", "false"},
            {"Narrative of the Life of Frederick Douglass, an American Slave", "Frederick Douglass", "https://www.gutenberg.org/ebooks/23.txt.utf-8", "false"},
            {"On Liberty", "John Stuart Mill", "https://www.gutenberg.org/ebooks/34901.txt.utf-8", "false"},
            {"The Parochial History of Cornwall, Volume 1", "", "https://www.gutenberg.org/files/60555/60555-0.txt", "false"},
            {"Peter Pan", "J. M.  Barrie", "https://www.gutenberg.org/files/16/16-0.txt", "false"},
            {"The Picture of Dorian Gray", "Oscar Wilde", "https://www.gutenberg.org/ebooks/174.txt.utf-8", "false"},
            {"Pride and Prejudice", "Jane Austen", "https://www.gutenberg.org/files/1342/1342-0.txt", "false"},
            {"The Prince", "Niccolò Machiavelli", "https://www.gutenberg.org/ebooks/1232.txt.utf-8", "false"},
            {"The Problems of Philosophy", "Bertrand Russell", "https://www.gutenberg.org/ebooks/5827.txt.utf-8", "false"},
            {"The Prophet", "Kahlil Gibran", "https://www.gutenberg.org/files/58585/58585-0.txt", "false"},
            {"Pygmalion", "Bernard Shaw", "https://www.gutenberg.org/ebooks/3825.txt.utf-8", "false"},
            {"The Republic", "Plato", "https://www.gutenberg.org/ebooks/1497.txt.utf-8", "false"},
            {"The Scarlet Letter", "Nathaniel Hawthorne", "https://www.gutenberg.org/files/25344/25344-0.txt", "false"},
            {"Second Treatise of Government", "John Locke", "https://www.gutenberg.org/ebooks/7370.txt.utf-8", "false"},
            {"Sense and Sensibility", "Jane Austen", "https://www.gutenberg.org/ebooks/161.txt.utf-8", "false"},
            {"Siddhartha", "Hermann Hesse", "https://www.gutenberg.org/ebooks/2500.txt.utf-8", "false"},
            {"Songs of Innocence, and Songs of Experience", "William Blake", "https://www.gutenberg.org/files/1934/1934-0.txt", "false"},
            {"The Souls of Black Folk", "W. E. B.  Du Bois", "https://www.gutenberg.org/ebooks/408.txt.utf-8", "false"},
            {"The Strange Case of Dr. Jekyll and Mr. Hyde", "Robert Louis Stevenson", "https://www.gutenberg.org/files/43/43-0.txt", "false"},
            {"A Study in Scarlet", "Arthur Conan Doyle", "https://www.gutenberg.org/files/244/244-0.txt", "false"},
            {"A Tale of Two Cities", "Charles Dickens", "https://www.gutenberg.org/files/98/98-0.txt", "false"},
            {"The Theory of the Leisure Class", "Thorstein Veblen", "https://www.gutenberg.org/ebooks/833.txt.utf-8", "false"},
            {"The Time Machine", "H. G.  Wells", "https://www.gutenberg.org/files/35/35-0.txt", "false"},
            {"The Tragical History of Doctor Faustus", "Christopher Marlowe", "https://www.gutenberg.org/ebooks/779.txt.utf-8", "false"},
            {"Treasure Island", "Robert Louis Stevenson", "https://www.gutenberg.org/files/120/120-0.txt", "false"},
            {"The Turn of the Screw", "Henry James", "https://www.gutenberg.org/files/209/209-0.txt", "false"},
            {"Ulysses", "James Joyce", "https://www.gutenberg.org/files/4300/4300-0.txt", "false"},
            {"Uncle Tom's Cabin", "Harriet Beecher Stowe", "https://www.gutenberg.org/files/203/203-0.txt", "false"},
            {"The Used People Lot", "Irving E. Fang", "https://www.gutenberg.org/ebooks/60545.txt.utf-8", "false"},
            {"Walden, and On The Duty Of Civil Disobedience", "Henry David Thoreau", "https://www.gutenberg.org/files/205/205-0.txt", "false"},
            {"War and Peace", "graf Leo Tolstoy", "https://www.gutenberg.org/files/2600/2600-0.txt", "false"},
            {"The War of the Worlds", "H. G.  Wells", "https://www.gutenberg.org/files/36/36-0.txt", "false"},
            {"The Wonderful Wizard of Oz", "L. Frank  Baum", "https://www.gutenberg.org/ebooks/55.txt.utf-8", "false"},
            {"The Works of Edgar Allan Poe — Volume 2", "Edgar Allan Poe", "https://www.gutenberg.org/files/2148/2148-0.txt", "false"},
            {"The Works of Edgar Allan Poe, The Raven Edition", "Edgar Allan Poe", "https://www.gutenberg.org/ebooks/25525.txt.utf-8", "false"},
            {"Wuthering Heights", "Emily Brontë", "https://www.gutenberg.org/ebooks/768.txt.utf-8", "false"},
            {"The Yellow Wallpaper", "Charlotte Perkins Gilman", "https://www.gutenberg.org/files/1952/1952-0.txt", "false"}
    };

    FrameLayout downloadScreen;
    String STRING_ARRAY = "ArrayOfStrings";
    ArrayList<String> library = new ArrayList<>();
    int librarySize;
    // Global variable so buttons set which book should be downloading
    int downloadID = 0;
    // Runs the book downloading method
    Thread downloadBookThread = new Thread(){
        public void run() {
            try{
                String book = downloadBook(Books[downloadID][2]);
                saveBook(book, Books[downloadID][0]);
                Books[downloadID][3] = "true";

            } catch (Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        }
    };

    // function to get one item (current data or forecast) from the server
    protected String downloadBook( String str_url ) {
        try {
            // assemble the string and the search request
            StringBuilder response = new StringBuilder();
            URL url = new URL(str_url);

            // make the connection
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();

            // did it do ok?
            if ( httpconn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(httpconn.getInputStream()), 8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    // have more data
                    response.append(strLine);
                    response.append("\n");
                }
                input.close();
                return response.toString();
            }
        } catch ( IOException e ) {
            System.out.println(e);
            return "false";
        }
        return "false";
    }

    // Saves the book into the phone for offline use
    public void saveBook(String Book, String BookName){
        // Sets up file output stream to be able to save the book
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(BookName, MODE_PRIVATE);
            fos.write(Book.getBytes());
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

    // Deletes the book off the phone
    public boolean deleteBook( String BookName ){
        boolean deleted = false;
        try{
            File dir = getFilesDir();
            File file = new File(dir, BookName);
            deleted = file.delete();
        } catch ( Exception E ){
            E.printStackTrace();
        } finally {
            deletePosition(BookName);
            return deleted;
        }
    }

    // Deletes the book off the phone
    public void deletePosition( String BookName ){
        try{
            File dir = getFilesDir();
            File file = new File(dir, BookName + " Position");
            file.delete();
        } catch ( Exception E ){
            E.printStackTrace();
        }
    }

    // Checks if the book is currently downloaded
    public void checkBookDownloaded(){
        for (int i = 0; i < Books.length; i++){
            FileInputStream fis = null;

            try {
                fis = openFileInput(Books[i][0]);
                Books[i][3] = "true";
            } catch (FileNotFoundException e) {
                Books[i][3] = "false";
                e.printStackTrace();
            }
        }
    }

    public String getAuthor(String bookName){
        int size = Books.length;
        for(int i = 0; i < size; i++){
            if(Books[i][0].equals(bookName)){
                return Books[i][1];
            }
        }
        return "ERROR";
    }

    // Creates the table for downloading books
    public void createTableDownload(){

        // Creates the table
        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        TableRow.LayoutParams trlp = new TableRow.LayoutParams();
        final TableLayout BookTable = findViewById(R.id.tableOfBooks);
        TableRow tr = null;
        BookTable.removeAllViews();

        // Creates new row
        tr = new TableRow(this);
        tr.setLayoutParams(trlp);
        BookTable.addView(tr);
        trlp.height = 45;
        for (int i = 0; i < Books.length; i++){
            for(int j = 0; j < 3; j++){
                // Creates download button for each record of the table in the third column
               if ( j == 2 ) {
                    final Button downloadButton = new Button(this);
                   lp.width = 0;
                   lp.weight = 1;
                    if ( Books[i][3] == "false" ) {
                        downloadButton.setText("Download");
                        downloadButton.setId(i);
                        downloadButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ( Books[v.getId()][3] == "false" ){
                                    String book = Books[v.getId()][0];
                                    Books[v.getId()][3] = "true";
                                    /*downloadButton.setText("Downloading...");
                                    downloadBookThread.run();*/
                                    new DownloadBook(getApplicationContext(), downloadScreen, downloadButton, Books[v.getId()][2], book).execute();
                                    updateBookList(book, 1);
                                }

                            }
                        });
                    } else {
                        downloadButton.setText("Downloaded");
                        downloadButton.setId(i);
                        downloadButton.setTextSize(12);
                    }
                    downloadButton.setLayoutParams(lp);
                    tr.addView(downloadButton);

                    // Creates Author Textview for second column
                } else if ( j == 1  ) {
                    TextView Author = new TextView(this);
                    Author.setText(Books[i][1]);
                    Author.setId(i);
                   lp.width = 0;
                   lp.weight = 1;
                   Author.setLayoutParams(lp);
                    tr.addView(Author);

                    //Creates bookname with the first column
                } else {
                   TextView BookName = new TextView(this);
                    BookName.setText(Books[i][0]);
                    BookName.setId(i);
                   lp.width = 0;
                   lp.weight = 2;
                   BookName.setLayoutParams(lp);
                    tr.addView(BookName);
                }
            }
            tr = new TableRow(this);
            tr.setLayoutParams(trlp);
            BookTable.addView(tr);
        }
    }

    public void updateSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = preferences.edit();

        for(int i = 0; i < librarySize; i++){
            edit.remove("BOOK_TITLE"+i);
            edit.putString("BOOK_TITLE"+i, library.get(i));
        }
        edit.putInt("SIZE", librarySize-1);
        edit.commit();

        updateLibrary();
    }

    public void updateBookList(String book, int action){
        switch(action){
            case 1:
                library.add(book);
                break;
            case 2:
                library.remove(book);
                library.add(0, book);
                break;
            case 3:
                library.remove(book);
                break;
            default:
                System.out.println("Error: " + book + " " + action);
                break;
        }
        librarySize = library.size();
        updateSharedPreferences();
    }

    // Creates the table for showing owned books
    public void createTableOwned(){

        // Creates the table
        TableRow.LayoutParams lp = new TableRow.LayoutParams();
        TableRow.LayoutParams trlp = new TableRow.LayoutParams();
        final TableLayout BookTable = findViewById(R.id.tableOfBooks);
        TableRow tr = null;
        BookTable.removeAllViews();

        // Creates new row
        tr = new TableRow(this);
        tr.setLayoutParams(trlp);
        BookTable.addView(tr);
        trlp.height = 45;
        for(int i = 0; i < librarySize; i++){
            for(int j = 0; j < 3; j++) {
                // Creates Delete button for each record of the table in the third column
                if ( j == 2 ){
                    final Button deleteButton = new Button(this);
                    lp.width = 0;
                    lp.weight = 1;
                    deleteButton.setText("Delete");
                    deleteButton.setId(i);
                    deleteButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            boolean isDeleted = deleteBook(library.get(v.getId()));
                            if (isDeleted){
                                Books[v.getId()][3] = "false";
                            }
                            updateBookList(library.get(v.getId()), 3);
                            createTableOwned();
                        }
                    });
                    deleteButton.setLayoutParams(lp);
                    tr.addView(deleteButton);

                    // Creates Author textview for second column
                } else if (j == 1) {
                    TextView Author = new TextView(this);
                    Author.setText(getAuthor(library.get(i)));
                    Author.setId(i);
                    lp.width = 0;
                    lp.weight = 1;
                    Author.setLayoutParams(lp);
                    tr.addView(Author);

                    // Creates book title textview for the first column
                } else {
                    TextView BookName = new TextView(this);
                    BookName.setText(library.get(i));
                    BookName.setId(i);
                    lp.width = 0;
                    lp.weight = 2;
                    BookName.setLayoutParams(lp);
                    BookName.setClickable(true);
                    tr.addView(BookName);

                    BookName.setOnClickListener(new View.OnClickListener() {

                        // Allows the text view be clicked and have a use
                        @Override
                        public void onClick(View v) {
                            String book = library.get(v.getId());
                            updateBookList(book, 2);

                            // We need to pass in Bookname, which is Books[v.getId()][0]
                            // Books are saved and loaded using booknames so in the other activity we use this to load the book
                            Intent intent = new Intent(getBaseContext(), readSelectedBook.class);
                            intent.putExtra("BOOK_TITLE", book);
                            startActivity(intent);
                        }
                    });
                }
            }
            tr = new TableRow(this);
            BookTable.addView(tr);
        }
    }
    public void updateLibrary(){
        library.clear();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int index = preferences.getInt("SIZE", 0);
        for(int i = index; i >= 0; i--){
            library.add(preferences.getString("BOOK_TITLE"+i, null));
        }
        librarySize = library.size();
    }

    public void removePrefs(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();
        library.clear();
    }

    // Strings use to denote which table the user is looking at
    final static String usersLibraryTitle = "Your Library";
    final static String addToLibraryTitle = "Download Books To Your Library";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        downloadScreen = findViewById(R.id.downloadScreen);

        // Needed to make http download work properly
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        updateLibrary();
        checkBookDownloaded();
        createTableOwned();

        final TextView tv = findViewById(R.id.mainMenuTitle);
        tv.setText(usersLibraryTitle);

        final TabLayout tl = findViewById(R.id.navigationTabBar);
        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    tv.setText(usersLibraryTitle);
                    updateLibrary();
                    createTableOwned();
                }
                else{
                    tv.setText(addToLibraryTitle);
                    createTableDownload();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Button purge = findViewById(R.id.purge);
        purge.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                for(int i = 0; i < Books.length; i++){
                    deleteBook(Books[i][0]);
                }
                removePrefs();
                checkBookDownloaded();
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

class DownloadBook extends AsyncTask<Void, Void, Void> {
    Context context;
    FrameLayout downloadScreen;
    Button download;
    String str_url;
    String book;

    DownloadBook(Context context, FrameLayout downloadScreen, Button download, String url, String book) {
        this.context = context;
        this.downloadScreen = downloadScreen;
        this.download = download;
        str_url = url;
        this.book = book;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        downloadScreen.setAnimation(inAnimation);
        downloadScreen.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        boolean fail = false;
        String fullBook = "";
        // Needed to make http download work properly
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            // assemble the string and the search request
            StringBuilder response = new StringBuilder();
            URL url = new URL(str_url);

            // make the connection
            HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();

            // did it do ok?
            if ( httpconn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(httpconn.getInputStream()), 8192);
                String strLine = null;
                while ((strLine = input.readLine()) != null) {
                    // have more data
                    response.append(strLine);
                    response.append("\n");
                    if(isCancelled()){
                        break;
                    }
                }
                input.close();
                fullBook = response.toString();
                System.out.println(fullBook);
            }
        } catch ( IOException e ) {
            System.out.println("BALLLLLLLLLLS");
            System.out.println(e);
            fail = true;
        }
        if(!fail){
            // Sets up file output stream to be able to save the book
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(book, context.MODE_PRIVATE);
                fos.write(fullBook.getBytes());
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
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        downloadScreen.setAnimation(outAnimation);
        downloadScreen.setVisibility(View.GONE);
        download.setText("Downloaded");
    }
}
