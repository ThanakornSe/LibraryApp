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
public interface BookPlaylistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookPlaylistLink link);

    @Delete
    void delete(BookPlaylistLink link);

    @Update
    void update(BookPlaylistLink link);

    @Query("SELECT * FROM book_playlist_link")
    LiveData<List<BookPlaylistLink>> getAllBookPlaylistLink();

    @Query("SELECT * FROM book_playlist_link WHERE book_id =:id")
    LiveData<BookPlaylistLink> getBookPlaylistLinkByBookId(int id);

    @Query("SELECT * FROM book_playlist_link WHERE playlist_id =:id")
    LiveData<BookPlaylistLink> getBookPlaylistLinkByPlaylistId(int id);

    @Query("SELECT EXISTS(SELECT * FROM book_playlist_link WHERE book_id=:bookId AND playlist_id =:playlistId)")
    boolean isExisted(int bookId, int playlistId);

}
