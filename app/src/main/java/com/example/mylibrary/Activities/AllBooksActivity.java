package com.example.mylibrary.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.mylibrary.R;
import com.example.mylibrary.adapter.BooksRecViewAdapter;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.BookViewModel;

import java.util.List;

public class AllBooksActivity extends AppCompatActivity {
    private RecyclerView booksRecView;
    private BooksRecViewAdapter adapter;
    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        //To create an animation for this AllBookActivity page
        //overridePendingTransition(R.anim.slide_in,R.anim.slide_out);


        //enable back button on topLeft Corner
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        booksRecView = findViewById(R.id.booksRecView);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));


        bookViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(BookViewModel.class);
        bookViewModel.getAllBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter = new BooksRecViewAdapter(AllBooksActivity.this,BooksRecViewAdapter.ALL_BOOKS,books);
                booksRecView.setAdapter(adapter);
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.slide_out,R.anim.slide_in);
//    }
}