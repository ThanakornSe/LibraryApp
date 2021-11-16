package com.example.mylibrary.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mylibrary.data.BookDao;
import com.example.mylibrary.data.BookRepository;
import com.example.mylibrary.util.LibraryDatabase;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    public static BookRepository repository;
    public final LiveData<List<Book>> allBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        this.allBooks = repository.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks(){
        return allBooks;
    }

    public static LiveData<Book> getBookById(int id){
        return repository.getBookById(id);
    }

    public static void insertBook(Book book){
        LibraryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                repository.insertBook(book);
            }
        });
    }

    public static void updateBook(Book book){
        LibraryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                repository.updateBook(book);
            }
        });
    }

    public static void deleteBook(Book book){
        LibraryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                repository.deleteBook(book);
            }
        });
    }

    public static int getBookIdByName(String name){
        return repository.getBookIdByName(name);
    }

    public static LiveData<List<Book>> getBookByPlaylist(String playlistName){
        return repository.getBookByPlaylist(playlistName);
    }
}
