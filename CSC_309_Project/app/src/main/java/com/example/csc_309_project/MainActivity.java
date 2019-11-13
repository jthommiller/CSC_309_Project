/*
Authors: James Miller & Matthew Abney
Date: 12/5/19
Project: Create an android app that can download and read an Ebook
 */
package com.example.csc_309_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // BookName, Author, DownloadLink
    final String[][] Books = {
            {"Adventures of Huckleberry Finn", "Mark Twain", "https://www.gutenberg.org/files/76/76-0.txt"},
            {"The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "https://www.gutenberg.org/files/1661/1661-0.txt"},
            {"The Adventures of Tom Sawyer", "Mark Twain", "https://www.gutenberg.org/files/74/74-0.txt"},
            {"Alice's Adventures in Wonderland", "Lewis Carroll", "https://www.gutenberg.org/files/11/11-0.txt"},
            {"Also sprach Zarathustra. English", "Friedrich Wilhelm Nietzsche", "https://www.gutenberg.org/files/1998/1998-0.txt"},
            {"An Occurrence at Owl Creek Bridge", "Ambrose Bierce", "https://www.gutenberg.org/ebooks/375.txt.utf-8"},
            {"Anne of Green Gables", "L. M.  Montgomery", "https://www.gutenberg.org/files/45/45-0.txt"},
            {"Anthem", "Ayn Rand", "https://www.gutenberg.org/ebooks/1250.txt.utf-8"},
            {"Autobiography of Benjamin Franklin", "Benjamin Franklin", "https://www.gutenberg.org/ebooks/20203.txt.utf-8"},
            {"The Awakening, and Selected Short Stories", "Kate Chopin", "https://www.gutenberg.org/files/160/160-0.txt"},
            {"Beowulf: An Anglo-Saxon Epic Poem", "", "https://www.gutenberg.org/ebooks/16328.txt.utf-8"},
            {"Beyond Good and Evil", "Friedrich Wilhelm Nietzsche", "https://www.gutenberg.org/ebooks/4363.txt.utf-8"},
            {"The Brothers Karamazov", "Fyodor Dostoyevsky", "https://www.gutenberg.org/files/28054/28054-0.txt"},
            {"Candide", "Voltaire", "https://www.gutenberg.org/ebooks/19942.txt.utf-8"},
            {"The Chaldean Account of Genesis", "A. H.  Sayce and George Smith", "https://www.gutenberg.org/files/60559/60559-0.txt"},
            {"A Christmas Carol in Prose; Being a Ghost Story of Christmas", "Charles Dickens", "https://www.gutenberg.org/files/46/46-0.txt"},
            {"Common Sense", "Thomas Paine", "https://www.gutenberg.org/ebooks/147.txt.utf-8"},
            {"The Complete Works of William Shakespeare", "William Shakespeare", "https://www.gutenberg.org/files/100/100-0.txt"},
            {"The Confessions of St. Augustine", "Bishop of Hippo Saint Augustine", "https://www.gutenberg.org/ebooks/3296.txt.utf-8"},
            {"The Count of Monte Cristo", "Alexandre Dumas", "https://www.gutenberg.org/files/1184/1184-0.txt"},
            {"Crime and Punishment", "Fyodor Dostoyevsky", "https://www.gutenberg.org/files/2554/2554-0.txt"},
            {"The Criticism of the Fourth Gospel", "W.  Sanday", "https://www.gutenberg.org/files/60553/60553-0.txt"},
            {"Democracy in America — Volume 1", "Alexis de Tocqueville", "https://www.gutenberg.org/ebooks/815.txt.utf-8"},
            {"The Devil's Dictionary", "Ambrose Bierce", "https://www.gutenberg.org/ebooks/972.txt.utf-8"},
            {"Divine Comedy, Longfellow's Translation, Hell", "Dante Alighieri", "https://www.gutenberg.org/ebooks/1001.txt.utf-8"},
            {"A Doll's House: a play", "Henrik Ibsen", "https://www.gutenberg.org/ebooks/2542.txt.utf-8"},
            {"Dracula", "Bram Stoker", "https://www.gutenberg.org/ebooks/345.txt.utf-8"},
            {"Dubliners", "James Joyce", "https://www.gutenberg.org/files/2814/2814-0.txt"},
            {"Emma", "Jane Austen", "https://www.gutenberg.org/files/158/158-0.txt"},
            {"Essays by Ralph Waldo Emerson", "Ralph Waldo Emerson", "https://www.gutenberg.org/ebooks/16643.txt.utf-8"},
            {"Essays of Michel de Montaigne — Complete", "Michel de Montaigne", "https://www.gutenberg.org/files/3600/3600-0.txt"},
            {"Ethan Frome", "Edith Wharton", "https://www.gutenberg.org/files/4517/4517-0.txt"},
            {"Frankenstein; Or, The Modern Prometheus", "Mary Wollstonecraft Shelley", "https://www.gutenberg.org/files/84/84-0.txt"},
            {"Great Expectations", "Charles Dickens", "https://www.gutenberg.org/files/1400/1400-0.txt"},
            {"Grimms' Fairy Tales", "Jacob Grimm and Wilhelm Grimm", "https://www.gutenberg.org/files/2591/2591-0.txt"},
            {"Gulliver's Travels into Several Remote Nations of the World", "Jonathan Swift", "https://www.gutenberg.org/files/829/829-0.txt"},
            {"Heart of Darkness", "Joseph Conrad", "https://www.gutenberg.org/files/219/219-0.txt"},
            {"The Hound of the Baskervilles", "Arthur Conan Doyle", "https://www.gutenberg.org/files/2852/2852-0.txt"},
            {"How the Other Half Lives: Studies Among the Tenements of New York", "Jacob A.  Riis", "https://www.gutenberg.org/ebooks/45502.txt.utf-8"},
            {"The Iliad", "Homer", "https://www.gutenberg.org/ebooks/6130.txt.utf-8"},
            {"The Importance of Being Earnest: A Trivial Comedy for Serious People", "Oscar Wilde", "https://www.gutenberg.org/ebooks/844.txt.utf-8"},
            {"Incidents in the Life of a Slave Girl, Written by Herself", "Harriet A.  Jacobs", "https://www.gutenberg.org/ebooks/11030.txt.utf-8"},
            {"The Interesting Narrative of the Life of Olaudah Equiano, Or Gustavus Vassa, The African", "Equiano", "https://www.gutenberg.org/ebooks/15399.txt.utf-8"},
            {"Ion", "Plato", "https://www.gutenberg.org/ebooks/1635.txt.utf-8"},
            {"Jane Eyre: An Autobiography", "Charlotte Brontë", "https://www.gutenberg.org/ebooks/1260.txt.utf-8"},
            {"The Jungle", "Upton Sinclair", "https://www.gutenberg.org/files/140/140-0.txt"},
            {"The Kama Sutra of Vatsyayana", "Vatsyayana", "https://www.gutenberg.org/ebooks/27827.txt.utf-8"},
            {"Le Morte d'Arthur: Volume 1", "Sir Thomas Malory", "https://www.gutenberg.org/files/1251/1251-0.txt"},
            {"Leaves of Grass", "Walt Whitman", "https://www.gutenberg.org/files/1322/1322-0.txt"},
            {"The Legend of Sleepy Hollow", "Washington Irving", "https://www.gutenberg.org/files/41/41-0.txt"},
            {"Les Misérables", "Victor Hugo", "https://www.gutenberg.org/files/135/135-0.txt"},
            {"Leviathan", "Thomas Hobbes", "https://www.gutenberg.org/ebooks/3207.txt.utf-8"},
            {"The Life and Adventures of Robinson Crusoe", "Daniel Defoe", "https://www.gutenberg.org/files/521/521-0.txt"},
            {"Little Women", "Louisa May Alcott", "https://www.gutenberg.org/ebooks/514.txt.utf-8"},
            {"Metamorphosis", "Franz Kafka", "https://www.gutenberg.org/ebooks/5200.txt.utf-8"},
            {"Moby Dick; Or, The Whale", "Herman Melville", "https://www.gutenberg.org/files/2701/2701-0.txt"},
            {"Modern Copper Smelting", "Donald M. Levy", "https://www.gutenberg.org/files/59328/59328-0.txt"},
            {"A Modest Proposal", "Jonathan Swift", "https://www.gutenberg.org/files/1080/1080-0.txt"},
            {"Narrative of the Captivity and Restoration of Mrs. Mary Rowlandson", "Mary White Rowlandson", "https://www.gutenberg.org/ebooks/851.txt.utf-8"},
            {"Narrative of the Life of Frederick Douglass, an American Slave", "Frederick Douglass", "https://www.gutenberg.org/ebooks/23.txt.utf-8"},
            {"On Liberty", "John Stuart Mill", "https://www.gutenberg.org/ebooks/34901.txt.utf-8"},
            {"The Parochial History of Cornwall, Volume 1", "", "https://www.gutenberg.org/files/60555/60555-0.txt"},
            {"Peter Pan", "J. M.  Barrie", "https://www.gutenberg.org/files/16/16-0.txt"},
            {"The Picture of Dorian Gray", "Oscar Wilde", "https://www.gutenberg.org/ebooks/174.txt.utf-8"},
            {"Pride and Prejudice", "Jane Austen", "https://www.gutenberg.org/files/1342/1342-0.txt"},
            {"The Prince", "Niccolò Machiavelli", "https://www.gutenberg.org/ebooks/1232.txt.utf-8"},
            {"The Problems of Philosophy", "Bertrand Russell", "https://www.gutenberg.org/ebooks/5827.txt.utf-8"},
            {"The Prophet", "Kahlil Gibran", "https://www.gutenberg.org/files/58585/58585-0.txt"},
            {"Pygmalion", "Bernard Shaw", "https://www.gutenberg.org/ebooks/3825.txt.utf-8"},
            {"The Republic", "Plato", "https://www.gutenberg.org/ebooks/1497.txt.utf-8"},
            {"The Scarlet Letter", "Nathaniel Hawthorne", "https://www.gutenberg.org/files/25344/25344-0.txt"},
            {"Second Treatise of Government", "John Locke", "https://www.gutenberg.org/ebooks/7370.txt.utf-8"},
            {"Sense and Sensibility", "Jane Austen", "https://www.gutenberg.org/ebooks/161.txt.utf-8"},
            {"Siddhartha", "Hermann Hesse", "https://www.gutenberg.org/ebooks/2500.txt.utf-8"},
            {"Songs of Innocence, and Songs of Experience", "William Blake", "https://www.gutenberg.org/files/1934/1934-0.txt"},
            {"The Souls of Black Folk", "W. E. B.  Du Bois", "https://www.gutenberg.org/ebooks/408.txt.utf-8"},
            {"The Strange Case of Dr. Jekyll and Mr. Hyde", "Robert Louis Stevenson", "https://www.gutenberg.org/files/43/43-0.txt"},
            {"A Study in Scarlet", "Arthur Conan Doyle", "https://www.gutenberg.org/files/244/244-0.txt"},
            {"A Tale of Two Cities", "Charles Dickens", "https://www.gutenberg.org/files/98/98-0.txt"},
            {"The Theory of the Leisure Class", "Thorstein Veblen", "https://www.gutenberg.org/ebooks/833.txt.utf-8"},
            {"The Time Machine", "H. G.  Wells", "https://www.gutenberg.org/files/35/35-0.txt"},
            {"The Tragical History of Doctor Faustus", "Christopher Marlowe", "https://www.gutenberg.org/ebooks/779.txt.utf-8"},
            {"Treasure Island", "Robert Louis Stevenson", "https://www.gutenberg.org/files/120/120-0.txt"},
            {"The Turn of the Screw", "Henry James", "https://www.gutenberg.org/files/209/209-0.txt"},
            {"Ulysses", "James Joyce", "https://www.gutenberg.org/files/4300/4300-0.txt"},
            {"Uncle Tom's Cabin", "Harriet Beecher Stowe", "https://www.gutenberg.org/files/203/203-0.txt"},
            {"The Used People Lot", "Irving E. Fang", "https://www.gutenberg.org/ebooks/60545.txt.utf-8"},
            {"Walden, and On The Duty Of Civil Disobedience", "Henry David Thoreau", "https://www.gutenberg.org/files/205/205-0.txt"},
            {"War and Peace", "graf Leo Tolstoy", "https://www.gutenberg.org/files/2600/2600-0.txt"},
            {"The War of the Worlds", "H. G.  Wells", "https://www.gutenberg.org/files/36/36-0.txt"},
            {"The Wonderful Wizard of Oz", "L. Frank  Baum", "https://www.gutenberg.org/ebooks/55.txt.utf-8"},
            {"The Works of Edgar Allan Poe — Volume 2", "Edgar Allan Poe", "https://www.gutenberg.org/files/2148/2148-0.txt"},
            {"The Works of Edgar Allan Poe, The Raven Edition", "Edgar Allan Poe", "https://www.gutenberg.org/ebooks/25525.txt.utf-8"},
            {"Wuthering Heights", "Emily Brontë", "https://www.gutenberg.org/ebooks/768.txt.utf-8"},
            {"The Yellow Wallpaper", "Charlotte Perkins Gilman", "https://www.gutenberg.org/files/1952/1952-0.txt"}
            };



    // function to get one item (current data or forecast) from the server
    protected String fetchItem( String str_url ) {
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
            return e.getMessage();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final Button start = findViewById( R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = "";
                String file = fetchItem(URL);
                System.out.println(file);
                TextView text = findViewById(R.id.testvew);
                text.setText(file);
            }
        });


    }
}
