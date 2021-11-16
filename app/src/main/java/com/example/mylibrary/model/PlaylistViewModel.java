package com.example.mylibrary.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mylibrary.data.PlaylistDao;
import com.example.mylibrary.data.PlaylistRepository;
import com.example.mylibrary.util.LibraryDatabase;

import java.util.List;

public class PlaylistViewModel extends AndroidViewModel {
    public static PlaylistRepository repository;
    private LiveData<List<Playlist>> allPlaylists;

    public PlaylistViewModel(Application application) {
        super(application);
        this.repository = new PlaylistRepository(application);
        this.allPlaylists = repository.getAllPlaylists();
    }

    public LiveData<List<Playlist>> getAllPlaylists(){
        return allPlaylists;
    }

    public static LiveData<Playlist> getPlaylistById(int id){
        return repository.getPlaylistById(id);
    }

    public static int getPlaylistIdByName(String name){
        return repository.getPlaylistIdByName(name);
    }

    public static LiveData<List<Playlist>> getPlaylistByBook(String bookName){
        return repository.getPlaylistByBook(bookName);
    }
}
