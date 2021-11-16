package com.example.mylibrary.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mylibrary.R;
import com.example.mylibrary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnAllBook.setOnClickListener(this);

        binding.btnAlreadyRead.setOnClickListener(this);

        binding.btnWishList.setOnClickListener(this);

        binding.btnCurrentlyReading.setOnClickListener(this);

        binding.btnFavBook.setOnClickListener(this);

        binding.btnAbout.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_AllBook:
                intent = new Intent(MainActivity.this, AllBooksActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_AlreadyRead:
                intent = new Intent(MainActivity.this, AlreadyReadBookActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_WishList:
                intent = new Intent(MainActivity.this, WishList.class);
                startActivity(intent);
                break;
            case R.id.btn_CurrentlyReading:
                intent = new Intent(MainActivity.this, CurrentlyRead.class);
                startActivity(intent);
                break;
            case R.id.btn_FavBook:
                intent = new Intent(MainActivity.this, Favorite.class);
                startActivity(intent);
                break;
            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("Designed and Developed by OnlyWeekend\n" +
                        "Check my website for more awesome application:");
                builder.setNegativeButton("Dismiss", (dialog, which) -> { });
                builder.setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //show the website of mine
                        Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                        intent.putExtra("url", "https://google.co.th");
                        startActivity(intent);
                    }
                });
                builder.setCancelable(true).create().show();
                break;
        }
    }
}