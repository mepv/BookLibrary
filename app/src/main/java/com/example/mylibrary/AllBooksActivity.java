package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Objects;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView booksRecyclerView;
    private BookRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        // overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        // how to animate the app, right know doesn't look well
        // in the future learn more about this. It need the method below to animate the backwards, finish(), min: 2:41:49

        //TODO: implement in all activities, review the functionality in every Activity. Works with the method below, onOptionsItemSelected()
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        adapter = new BookRecyclerViewAdapter(this, "allBooks");
        booksRecyclerView = findViewById(R.id.booksRecyclerView);

        adapter.setBooks(Utils.getInstance(this).getAllBooks());
        booksRecyclerView.setAdapter(adapter);
        //TODO: investigate how to implement expandable CardView with GridLayoutManager.
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //@Override
    //public void finish() {
    //    super.finish();
    //    overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
    //}
}