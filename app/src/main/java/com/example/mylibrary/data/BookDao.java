package com.example.mylibrary.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.BookPlaylistLink;
import com.example.mylibrary.model.Playlist;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(Book book);

    @Delete
    void deleteBook(Book book);

    @Update
    void updateBook(Book book);

    @Query("SELECT * FROM book")
    LiveData<List<Book>> getAllBook();

    @Query("SELECT * FROM book WHERE book.id =:id")
    LiveData<Book> getBookById(int id);

    @Query("SELECT id FROM book WHERE name=:name")
    int getBookIdByName(String name);

    @Query("SELECT book.id,book.name,book.author,book.page,book.imgUrl,book.shortDesc,book.longDesc,book.isExpanded,book.refUrl FROM book " +
            "INNER JOIN book_playlist_link ON book_id = book.id " +
            "INNER JOIN playlist ON playlist.id = book_playlist_link.playlist_id " +
            "WHERE playlist.name = :playlistName")
    LiveData<List<Book>> getBookByPlaylist(String playlistName);

}
