package com.example.mylibrary.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mylibrary.model.Playlist;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlaylist(Playlist playlist);

    @Delete
    void deletePlaylist(Playlist playlist);

    @Update
    void updatePlaylist(Playlist playlist);

    @Query("SELECT * FROM playlist")
    LiveData<List<Playlist>> getAllPlaylist();

    @Query("SELECT * FROM playlist WHERE id =:id")
    LiveData<Playlist> getPlaylistById(int id);

    @Query("SELECT id FROM playlist WHERE name=:name")
    int getPlaylistIdByName(String name);

    @Query("SELECT * FROM playlist " +
            "INNER JOIN book_playlist_link ON playlist_id = playlist.id " +
            "INNER JOIN book ON book.id = book_playlist_link.book_id " +
            "WHERE book.name = :bookName")
    LiveData<List<Playlist>> getPlaylistByBook(String bookName);

}
