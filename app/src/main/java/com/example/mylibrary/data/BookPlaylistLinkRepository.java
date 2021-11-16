package com.example.mylibrary.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mylibrary.model.BookPlaylistLink;
import com.example.mylibrary.util.LibraryDatabase;

import java.util.List;

public class BookPlaylistLinkRepository {
    private BookPlaylistDao bookPlaylistDao;
    private LiveData<List<BookPlaylistLink>> allLink;

    public BookPlaylistLinkRepository(Application application) {
        LibraryDatabase db = LibraryDatabase.getInstance(application);
        this.bookPlaylistDao = db.bookPlaylistDao();
        this.allLink = bookPlaylistDao.getAllBookPlaylistLink();
    }

    public LiveData<List<BookPlaylistLink>> getAllLink() {
        return allLink;
    }

    public LiveData<BookPlaylistLink> getBookPlaylistLinkByBookId(int id){
        return bookPlaylistDao.getBookPlaylistLinkByBookId(id);
    }

    public LiveData<BookPlaylistLink> getBookPlaylistLinkByPlaylistId(int id){
        return bookPlaylistDao.getBookPlaylistLinkByPlaylistId(id);
    }

    public void insert(BookPlaylistLink link){
        LibraryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookPlaylistDao.insert(link);
            }
        });
    }

    public void delete(BookPlaylistLink link){
        LibraryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookPlaylistDao.delete(link);
            }
        });
    }

    public void update(BookPlaylistLink link){
        LibraryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookPlaylistDao.update(link);
            }
        });
    }

    public boolean isExisted(int bookId, int playlistId){
        return bookPlaylistDao.isExisted(bookId,playlistId);
    }

}
