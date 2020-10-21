package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    //TODO: provide a link for more information, min 2:12:23

    public static final String BOOK_ID_KEY = "bookId";

    private TextView textINameBook, textIAuthor, textIPagesNumber, textIDescription, textILongDescription;
    private Button buttonAddToCurrentlyReading, buttonAddToWantToRead, buttonAddToAlreadyRead, buttonAddToFavorites;
    private ImageView imageIBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initialViews();

        //String longDescription = "This is the remarkable story of one endearing dog's search for his purpose over the course of several lives. More than just another charming dog story, A Dog's Purpose touches on the universal quest for an answer to life's most basic question: Why are we here?\n" +
        //        "\n" +
        //        "Surprised to find himself reborn as a rambunctious golden-haired puppy after a tragically short life as a stray mutt, Bailey's search for his new life’s meaning leads him into the loving arms of 8-year-old Ethan. During their countless adventures Bailey joyously discovers how to be a good dog.\n" +
        //        "\n" +
        //        "But this life as a beloved family pet is not the end of Bailey’s journey. Reborn as a puppy yet again, Bailey wonders - will he ever find his purpose?\n" +
        //        "\n" +
        //        "Heartwarming, insightful, and often laugh-out-loud funny, A Dog's Purpose is not only the emotional and hilarious story of a dog's many lives, but also a dog's-eye commentary on human relationships and the unbreakable bonds between man and man's best friend. This moving and beautifully crafted story teaches us that love never dies, that our true friends are always with us, and that every creature on earth is born with a purpose.";
        //
        ////TODO: Get the data from Recycler view in here
        //Book book = new Book(2, "A Dog's Purpose", "W. Bruce Cameron", 336,
        //        "https://prodimage.images-bn.com/pimages/9780765388117_p0_v4_s1200x630.jpg",
        //        "Every Dog Happen For a Reason",
        //        longDescription);

        Intent intent = getIntent();
        if (null != intent) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (null != incomingBook) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }
            }
        }
    }

    /**
     * Enable and Disable button,
     * Add the book to Favorites Book ArrayList
     * @param book
     */
    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoritesBooks = Utils.getInstance(this).getFavoriteBooks();
        boolean existInFavoritesBooks = false;
        for(Book b: favoritesBooks) {
            if (b.getId() == book.getId()) {
                existInFavoritesBooks = true;
                break;
            }
        }
        if (existInFavoritesBooks) {
            buttonAddToFavorites.setEnabled(false);
        } else {
            buttonAddToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToFavorites(book)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavoritesActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Book not added, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Enable and Disable button,
     * Add the book to Currently Reading Book ArrayList
     * @param book
     */
    private void handleCurrentlyReadingBooks(final Book book) {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();
        boolean existInCurrentlyReadingBooks = false;
        for(Book b: currentlyReadingBooks) {
            if (b.getId() == book.getId()) {
                existInCurrentlyReadingBooks = true;
                break;
            }
        }
        if (existInCurrentlyReadingBooks) {
            buttonAddToCurrentlyReading.setEnabled(false);
        } else {
            buttonAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Book not added, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    /**
     * Enable and Disable button,
     * Add the book to Want To Read Book ArrayList
     * @param book
     */
    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();
        boolean existInWantToReadBooks = false;
        for(Book b: wantToReadBooks) {
            if (b.getId() == book.getId()) {
                existInWantToReadBooks = true;
                break;
            }
        }
        if (existInWantToReadBooks) {
            buttonAddToWantToRead.setEnabled(false);
        } else {
            buttonAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToWantToRead(book)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Book not added, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Enable and Disable button,
     * Add the book to Already Read Book ArrayList
     * @param book
     */
    private void handleAlreadyRead(final Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();
        boolean existInAlreadyReadBooks = false;
        for(Book b: alreadyReadBooks) {
            if (b.getId() == book.getId()) {
                existInAlreadyReadBooks = true;
                break;
            }
        }
        if (existInAlreadyReadBooks) {
            buttonAddToAlreadyRead.setEnabled(false);
        } else {
            buttonAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Book not added, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        textINameBook.setText(book.getName());
        textIAuthor.setText(book.getAuthor());
        textIPagesNumber.setText(String.valueOf(book.getPages()));
        textIDescription.setText(book.getShortDescription());
        textILongDescription.setText(book.getLongDescription());
        Glide.with(this)
                .asBitmap()
                .load(book.getImageURL())
                .into(imageIBook);

    }

    private void initialViews() {
        textIAuthor = findViewById(R.id.textINameAuthor);
        textINameBook = findViewById(R.id.textINameBook);
        textIPagesNumber = findViewById(R.id.textIPagesNumber);
        textIDescription = findViewById(R.id.textIDescription);
        textILongDescription = findViewById(R.id.textILongDescription);

        buttonAddToCurrentlyReading = findViewById(R.id.buttonAddToCurrentlyReading);
        buttonAddToWantToRead = findViewById(R.id.buttonAddToWantToRead);
        buttonAddToAlreadyRead = findViewById(R.id.buttonAddToAlreadyRead);
        buttonAddToFavorites = findViewById(R.id.buttonAddToFavorites);

        imageIBook = findViewById(R.id.imageIBook);
    }

}