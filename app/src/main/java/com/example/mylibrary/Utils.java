package com.example.mylibrary;

//TODO: investigate how to connect the DataBase, it could be local or in a webServer
// this class in a real world application would be the DataBase
// need to know how to integrate the connection, sent and request.


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS_KEY = "already_read_books";
    private static final String WANT_TO_READ_BOOKS_KEY = "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS_KEY = "currently_reading_books";
    private static final String FAVORITES_BOOKS_KEY = "favorites_books";

    private static Utils instance;
    private static SharedPreferences sharedPreferences;

    // no longer need it
    //private static ArrayList<Book> allBooks;
    //private static ArrayList<Book> alreadyReadBooks;
    //private static ArrayList<Book> wantToReadBooks;
    //private static ArrayList<Book> currentlyReadingBooks;
    //private static ArrayList<Book> favoriteBooks;

    private Utils(Context context) {
        //TODO: investigate about Context in Android

        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if (null == getAllBooks()) {
            initialData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks()) {
            editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getWantToReadBooks()) {
            editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getCurrentlyReadingBooks()) {
            editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getFavoriteBooks()) {
            editor.putString(FAVORITES_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    public static Utils getInstance(Context context) {
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    private void initialData() {
        //TODO: add initial Data,
        // DONE!

        ArrayList<Book> books = new ArrayList<>();

        String longDescription = "This is the remarkable story of one endearing dog's search for his purpose over the course of several lives. More than just another charming dog story, A Dog's Purpose touches on the universal quest for an answer to life's most basic question: Why are we here?\n" +
                        "\n" +
                        "Surprised to find himself reborn as a rambunctious golden-haired puppy after a tragically short life as a stray mutt, Bailey's search for his new life’s meaning leads him into the loving arms of 8-year-old Ethan. During their countless adventures Bailey joyously discovers how to be a good dog.\n" +
                        "\n" +
                        "But this life as a beloved family pet is not the end of Bailey’s journey. Reborn as a puppy yet again, Bailey wonders - will he ever find his purpose?\n" +
                        "\n" +
                        "Heartwarming, insightful, and often laugh-out-loud funny, A Dog's Purpose is not only the emotional and hilarious story of a dog's many lives, but also a dog's-eye commentary on human relationships and the unbreakable bonds between man and man's best friend. This moving and beautifully crafted story teaches us that love never dies, that our true friends are always with us, and that every creature on earth is born with a purpose.";


         books.add(new Book(1, "1984", "George Orwell", 326,
                "https://imagessl4.casadellibro.com/a/l/t7/44/9788499890944.jpg",
                "A dystopian fictional political novel",
                "Long Description"));

        books.add(new Book(2, "A Dog's Purpose", "W. Bruce Cameron", 336,
                "https://prodimage.images-bn.com/pimages/9780765388117_p0_v4_s1200x630.jpg",
                "Every Dog Happen For a Reason",
                longDescription));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        // here is better use the apply() method, the changes in your shared preferences will be applied
        // in a background thread and the main thread will not be block, it means that avoid to block the user from interacting
        // with your application.
        // but in here our data is really small and we are not going to block our user interface for long and beside that we need
        // the result of this applying immediately, that why we are using commit.
        //TODO: later on we'll see how to use callbacks in order to communicate between two different threads and then we can use apply().
        editor.commit();
    }

    // before using sharePreferences this method was static, in order to test the app, is no longer needed to be static
    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getFavoriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITES_BOOKS_KEY, null), type);
        return books;
    }

    public Book getBookById(int id) {
        ArrayList<Book> books = getAllBooks();
        if (null != books) {
            for (Book b: books) {
                if (b.getId() == id) {
                    return b;
                }
            }
        }
        return null;
    }

    public boolean addToAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS_KEY);
                editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToWantToRead (Book book) {
        ArrayList<Book> books = getWantToReadBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS_KEY);
                editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrentlyReading (Book book) {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS_KEY);
                editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavorites (Book book) {
        ArrayList<Book> books = getFavoriteBooks();
        if (null != books) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITES_BOOKS_KEY);
                editor.putString(FAVORITES_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeFromAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBooks();
        if (null != books) {
            // the way below does not work because the book your receiving is different from the book that is inside
            // your ArrayList, although the values are the sames for both of this books but their reference are not
            // for that it can't be remove the book this way. Instead the foreach loop
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS_KEY);
                        editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromWantToRead(Book book) {
        ArrayList<Book> books = getWantToReadBooks();
        if (null != books) {
            // the way below does not work because the book your receiving is different from the book that is inside
            // your ArrayList, although the values are the sames for both of this books but their reference are not
            // for that it can't be remove the book this way. Instead the foreach loop
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_BOOKS_KEY);
                        editor.putString(WANT_TO_READ_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromCurrentlyReading(Book book) {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if (null != books) {
            // the way below does not work because the book your receiving is different from the book that is inside
            // your ArrayList, although the values are the sames for both of this books but their reference are not
            // for that it can't be remove the book this way. Instead the foreach loop
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS_KEY);
                        editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromFavorites (Book book) {
        ArrayList<Book> books = getFavoriteBooks();
        if (null != books) {
            // the way below does not work because the book your receiving is different from the book that is inside
            // your ArrayList, although the values are the sames for both of this books but their reference are not
            // for that it can't be remove the book this way. Instead the foreach loop
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITES_BOOKS_KEY);
                        editor.putString(FAVORITES_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
