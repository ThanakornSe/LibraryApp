package com.example.mylibrary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mylibrary.R;
import com.example.mylibrary.adapter.BooksRecViewAdapter;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.BookViewModel;
import com.example.mylibrary.util.LibraryDatabase;

import java.util.List;

public class AlreadyReadBookActivity extends AppCompatActivity {
    private RecyclerView booksRecView;
    private BooksRecViewAdapter adapter;
    private BookViewModel bookViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_read_book);

        booksRecView = findViewById(R.id.ARBRecView);
        //we can re-use the recyclerView Adapter

        booksRecView.setLayoutManager(new LinearLayoutManager(this));

        bookViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(BookViewModel.class);
        BookViewModel.getBookByPlaylist(LibraryDatabase.ALREADY_READ).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter = new BooksRecViewAdapter(AlreadyReadBookActivity.this,BooksRecViewAdapter.ALREADY_READ,books);
                booksRecView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        // To make back button not stack history
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}