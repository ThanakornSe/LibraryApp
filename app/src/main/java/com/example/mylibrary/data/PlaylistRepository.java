package com.example.mylibrary.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.Playlist;
import com.example.mylibrary.util.LibraryDatabase;

import java.util.List;

public class PlaylistRepository {
    private PlaylistDao playlistDao;
    private LiveData<List<Playlist>> allPlaylists;

    public PlaylistRepository(Application application) {
        LibraryDatabase db = LibraryDatabase.getInstance(application);
        this.playlistDao = db.playlistDao();
        this.allPlaylists = playlistDao.getAllPlaylist();
    }

    public LiveData<List<Playlist>> getAllPlaylists(){
        return allPlaylists;
    }

    public LiveData<Playlist> getPlaylistById(int id){
        return playlistDao.getPlaylistById(id);
    }

    public int getPlaylistIdByName(String name){
        return playlistDao.getPlaylistIdByName(name);
    }

    public LiveData<List<Playlist>> getPlaylistByBook(String bookName){
        return playlistDao.getPlaylistByBook(bookName);
    }
}
