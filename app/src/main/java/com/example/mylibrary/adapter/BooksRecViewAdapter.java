package com.example.mylibrary.adapter;

import static com.example.mylibrary.Activities.BookActivity.BOOK_ID_KEY;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.Activities.BookActivity;
import com.example.mylibrary.R;
import com.example.mylibrary.model.Book;
import com.example.mylibrary.model.BookPlaylistLink;
import com.example.mylibrary.model.BookPlaylistLinkViewModel;
import com.example.mylibrary.model.PlaylistViewModel;
import com.example.mylibrary.util.LibraryDatabase;

import java.util.List;

public class BooksRecViewAdapter extends RecyclerView.Adapter<BooksRecViewAdapter.ViewHolder>{


    public static final String ALL_BOOKS = "allBooks";
    public static final String ALREADY_READ = "alreadyRead";
    public static final String WISHLIST = "wishlist";
    public static final String CURRENTLY_READ = "currentlyRead";

    private Context context;
    private String parentActivity;
    private List<Book> books;

    public BooksRecViewAdapter(Context context, String parentActivity, List<Book> books) {
        this.context = context;
        this.parentActivity = parentActivity;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_books,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.mtxtBookName.setText(book.getName());
        Glide.with(context).asBitmap().load(book.getImgUrl()).into(holder.mBookImg);

        holder.txtAuthor.setText(book.getAuthor());
        holder.txtDesc.setText(book.getShortDesc());

        if(book.isExpanded()){
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.ExpandRel.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

            switch (parentActivity) {
                case ALL_BOOKS:
                    holder.btndelete.setVisibility(View.GONE);
                    break;

                case ALREADY_READ:
                    holder.btndelete.setVisibility(View.VISIBLE);
                    holder.btndelete.setOnClickListener(v -> {
                        //set Alert Message to notify user before doing it.
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Make sure you want to delete " + book.getName() + "?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int playlistId = PlaylistViewModel.getPlaylistIdByName(LibraryDatabase.ALREADY_READ);
                                BookPlaylistLinkViewModel.delete(new BookPlaylistLink(book.getId(), playlistId));
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.create().show();
                    });
                    break;
                case WISHLIST:
                    holder.btndelete.setVisibility(View.VISIBLE);
                    holder.btndelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Make sure you want to delete " + book.getName() + "?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            int playlistId = PlaylistViewModel.getPlaylistIdByName(LibraryDatabase.WISHLIST);
                            BookPlaylistLinkViewModel.delete(new BookPlaylistLink(book.getId(), playlistId));
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {
                        });
                        builder.create().show();
                    });
                    break;
                case CURRENTLY_READ:
                    holder.btndelete.setVisibility(View.VISIBLE);
                    holder.btndelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Make sure you want to delete " + book.getName() + "?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            int playlistId = PlaylistViewModel.getPlaylistIdByName(LibraryDatabase.CURRENTLY_READ);
                            BookPlaylistLinkViewModel.delete(new BookPlaylistLink(book.getId(), playlistId ));
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {
                        });
                        builder.create().show();
                    });
                    break;
                default:
                    holder.btndelete.setVisibility(View.VISIBLE);
                    holder.btndelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Make sure you want to delete " + book.getName() + "?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            int playlistId = PlaylistViewModel.getPlaylistIdByName(LibraryDatabase.FAVORITE);
                            BookPlaylistLinkViewModel.delete(new BookPlaylistLink(book.getId(), playlistId));
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {
                        });
                        builder.create().show();
                    });
                    break;
            }

        }else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.ExpandRel.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }

        holder.parent.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookActivity.class);
            intent.putExtra(BOOK_ID_KEY, book.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private TextView mtxtBookName,txtAuthor,txtDesc,btndelete;
        private ImageView mBookImg,downArrow,upArrow;
        private RelativeLayout ExpandRel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            mtxtBookName = itemView.findViewById(R.id.txtBookName);
            mBookImg = itemView.findViewById(R.id.imgBook);
            txtAuthor = itemView.findViewById(R.id.txt_AuthorName);
            txtDesc = itemView.findViewById(R.id.txtShortDesc);
            downArrow = itemView.findViewById(R.id.btn_DownArrow);
            upArrow = itemView.findViewById(R.id.btn_UpArrow);
            ExpandRel = itemView.findViewById(R.id.expandedRelLayout);
            btndelete = itemView.findViewById(R.id.btn_delete);

            /**
             * this is gonna change expanded field
             * to invert value we have to use !book.isExpanded
             */
            downArrow.setOnClickListener(v -> {
                Book book = books.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
               // ExpandRel.setVisibility(ExpandRel.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            });

            upArrow.setOnClickListener(v -> {
                Book book = books.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
                //ExpandRel.setVisibility(ExpandRel.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            });

        }
    }
}
