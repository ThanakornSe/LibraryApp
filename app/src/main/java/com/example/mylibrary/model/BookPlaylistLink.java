package com.example.mylibrary.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "book_playlist_link",
        primaryKeys = {"book_id", "playlist_id"},
        foreignKeys = {@ForeignKey(entity = Book.class, parentColumns = "id", childColumns = "book_id"),
                @ForeignKey(entity = Playlist.class, parentColumns = "id", childColumns = "playlist_id")})
public class BookPlaylistLink {
    private int book_id;
    private int playlist_id;

    public BookPlaylistLink(int book_id, int playlist_id) {
        this.book_id = book_id;
        this.playlist_id = playlist_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(int playlist_id) {
        this.playlist_id = playlist_id;
    }
}
