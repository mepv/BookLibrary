package com.example.mylibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //TODO: check the rotation for all the layouts / landscape
    //TODO: bug on the BookActivity. if the book is added to AlreadyRead, the buttons CurrentlyReading and WantToRead should not be available.

    private Button buttonAllBooks, buttonAlreadyRead, buttonWantToRead, buttonCurrentlyReading, buttonFavorite, buttonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialView();

        buttonAllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
                startActivity(intent);
            }
        });

        buttonAlreadyRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlreadyReadBookActivity.class);
                startActivity(intent);
            }
        });

        buttonWantToRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WantToReadActivity.class);
                startActivity(intent);
            }
        });

        buttonCurrentlyReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CurrentlyReadingActivity.class);
                startActivity(intent);
            }
        });

        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("Designed and Developed with passion by Mario Palacios at mepvdevelopment.wordpress.com\n" +
                        "Check my blog for more awesome applications:");

                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                        intent.putExtra("url", "https://google.com/");
                        startActivity(intent);
                    }
                });

                //TODO: investigate about customize dialogs
                builder.create().show();
            }
        });

        Utils.getInstance(this);
    }

    private void initialView() {
        buttonAbout = findViewById(R.id.buttonAbout);
        buttonAllBooks = findViewById(R.id.buttonAllBooks);
        buttonAlreadyRead = findViewById(R.id.buttonAlreadyRead);
        buttonWantToRead = findViewById(R.id.buttonWantToRead);
        buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonCurrentlyReading = findViewById(R.id.buttonCurrentlyReading);
    }
}