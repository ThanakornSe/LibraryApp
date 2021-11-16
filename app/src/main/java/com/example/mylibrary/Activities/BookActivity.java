package com.example.mylibrary.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.mylibrary.R;
import com.example.mylibrary.databinding.ActivityBookBinding;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.BookPlaylistLink;
import com.example.mylibrary.model.BookPlaylistLinkViewModel;
import com.example.mylibrary.model.BookViewModel;
import com.example.mylibrary.model.PlaylistViewModel;
import com.example.mylibrary.util.LibraryDatabase;

public class BookActivity extends AppCompatActivity {

    private ActivityBookBinding binding;
    public static final String BOOK_ID_KEY = "bookId";
    private BookViewModel bookViewModel;
    private PlaylistViewModel playlistViewModel;
    private BookPlaylistLinkViewModel linkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(BookActivity.this, R.layout.activity_book);

        bookViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(BookViewModel.class);
        playlistViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(PlaylistViewModel.class);
        linkViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(BookPlaylistLinkViewModel.class);

        /**
         * Get data from RecyclerView
         */
        Bundle data = getIntent().getExtras();
        if (data != null) {
            int bookId = data.getInt(BOOK_ID_KEY, -1);
            if (bookId != -1) { //make sure that we have some data from intent
                BookViewModel.getBookById(bookId).observe(this, new Observer<Book>() {
                    @Override
                    public void onChanged(Book book) {
                        setDataToView(book);
                        handleAlreadyRead(book);
                        handleWishlist(book);
                        handleCurrentlyRead(book);
                        handleFavorite(book);
                    }
                });
            }
        }
    }

    /**
     * Enable and Disable Button
     * Add the book to xxxBook ArrayList
     *
     * @param incomingBook
     */
    private void handleFavorite(Book incomingBook) {
        int bookId = incomingBook.getId();
        int playlistId = PlaylistViewModel.getPlaylistIdByName(LibraryDatabase.FAVORITE);

        if(BookPlaylistLinkViewModel.isExisted(bookId,playlistId)){
            binding.btnAtF.setEnabled(false);
        }else {
            binding.btnAtF.setOnClickListener(v -> {
                BookPlaylistLinkViewModel.insert(new BookPlaylistLink(bookId, playlistId));
                Intent intent = new Intent(BookActivity.this, Favorite.class);
                startActivity(intent);
            });
        }
    }

    private void handleCurrentlyRead(final Book incomingBook) {
        int bookId = incomingBook.getId();
        int playlistId = PlaylistViewModel.getPlaylistIdByName(LibraryDatabase.CURRENTLY_READ);

        if (BookPlaylistLinkViewModel.isExisted(bookId,playlistId)) {
            binding.btnAddtoCReading.setEnabled(false);
        } else {
            binding.btnAddtoCReading.setOnClickListener(v -> {
                BookPlaylistLinkViewModel.insert(new BookPlaylistLink(bookId, playlistId));
                Intent intent = new Intent(BookActivity.this, CurrentlyRead.class);
                startActivity(intent);
            });
        }
    }

    private void handleWishlist(final Book incomingBook) {
        int bookId = incomingBook.getId();
        int playlistId = PlaylistViewModel.getPlaylistIdByName(LibraryDatabase.WISHLIST);

        if (BookPlaylistLinkViewModel.isExisted(bookId,playlistId)) {
            binding.btnAddtoWL.setEnabled(false);
        } else {
            binding.btnAddtoWL.setOnClickListener(v -> {
                BookPlaylistLinkViewModel.insert(new BookPlaylistLink(bookId, playlistId));
                Intent intent = new Intent(BookActivity.this, WishList.class);
                startActivity(intent);
            });
        }
    }

    private void handleAlreadyRead(final Book incomingBook) {
        int bookId = incomingBook.getId();
        int playlistId = PlaylistViewModel.getPlaylistIdByName(LibraryDatabase.ALREADY_READ);

        if (BookPlaylistLinkViewModel.isExisted(bookId,playlistId)) {
            binding.btnAddtoAlread.setEnabled(false);
        } else {
            binding.btnAddtoAlread.setOnClickListener(v -> {
                BookPlaylistLinkViewModel.insert(new BookPlaylistLink(bookId, playlistId));
                Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                startActivity(intent);
            });
        }
    }

    private void setDataToView(Book incomingBook) {
        binding.txtBookName.setText(incomingBook.getName());
        binding.txtAuthor.setText(incomingBook.getAuthor());
        binding.txtPage.setText(String.valueOf(incomingBook.getPage()));
        binding.txtLongdesc.setText(incomingBook.getLongDesc());
        Glide.with(this).asBitmap().load(incomingBook.getImgUrl()).into(binding.imgBook);

        binding.linkBtn.setOnClickListener(v -> {
            Intent intent = new Intent(BookActivity.this, WebsiteActivity.class);
            intent.putExtra("url", incomingBook.getRefUrl());
            startActivity(intent);
        });
    }


}