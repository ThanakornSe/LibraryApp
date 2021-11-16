package com.example.mylibrary.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mylibrary.model.Book;
import com.example.mylibrary.util.LibraryDatabase;

import java.util.List;

public class BookRepository {
    private BookDao bookDao;
    private LiveData<List<Book>> allBooks;

    public BookRepository(Application application) {
        LibraryDatabase db = LibraryDatabase.getInstance(application);
        this.bookDao = db.bookDao();
        this.allBooks = bookDao.getAllBook();
    }

    public LiveData<List<Book>> getAllBooks(){
        return allBooks;
    }

    public LiveData<Book> getBookById(int id){
        return bookDao.getBookById(id);
    }

    public void insertBook(Book book){
        LibraryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookDao.insertBook(book);
            }
        });
    }

    public void updateBook(Book book){
        LibraryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookDao.updateBook(book);
            }
        });
    }

    public void deleteBook(Book book){
        LibraryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookDao.deleteBook(book);
            }
        });
    }

    public int getBookIdByName(String name){
        return bookDao.getBookIdByName(name);
    }

    public LiveData<List<Book>> getBookByPlaylist(String playlistName){
        return bookDao.getBookByPlaylist(playlistName);
    }
}
