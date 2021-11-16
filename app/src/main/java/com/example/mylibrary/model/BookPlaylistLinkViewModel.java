package com.example.mylibrary.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mylibrary.data.BookPlaylistDao;
import com.example.mylibrary.data.BookPlaylistLinkRepository;
import com.example.mylibrary.util.LibraryDatabase;

import java.util.List;

public class BookPlaylistLinkViewModel extends AndroidViewModel {
    public static BookPlaylistLinkRepository repository;
    public final LiveData<List<BookPlaylistLink>> allLink;

    public BookPlaylistLinkViewModel(Application application) {
        super(application);
        this.repository = new BookPlaylistLinkRepository(application);
        this.allLink = repository.getAllLink();
    }

    public LiveData<List<BookPlaylistLink>> getAllLink() {
        return allLink;
    }

    public static LiveData<BookPlaylistLink> getBookPlaylistLinkByBookId(int id) {
        return repository.getBookPlaylistLinkByBookId(id);
    }

    public static LiveData<BookPlaylistLink> getBookPlaylistLinkByPlaylistId(int id) {
        return repository.getBookPlaylistLinkByPlaylistId(id);
    }

    public static void insert(BookPlaylistLink link) {
        repository.insert(link);
    }

    public static void delete(BookPlaylistLink link) {
        repository.delete(link);
    }

    public static void update(BookPlaylistLink link) {
        repository.update(link);
    }

    public static boolean isExisted(int bookId, int playlistId){
        return repository.isExisted(bookId,playlistId);
    }

}
