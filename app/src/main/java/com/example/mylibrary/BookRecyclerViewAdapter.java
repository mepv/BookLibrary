package com.example.mylibrary;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.mylibrary.BookActivity.BOOK_ID_KEY;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "BookRecyclerViewAdapter";

    ArrayList<Book> books = new ArrayList<>();
    private Context myContext;
    private String parentActivity;

    public BookRecyclerViewAdapter(Context myContext, String parentActivity) {
        this.myContext = myContext;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "OnBindViewHolder: Called");
        holder.textName.setText(books.get(position).getName());
        Glide.with(myContext)
                .asBitmap()
                .load(books.get(position).getImageURL()).into(holder.imageBook);

        holder.parentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myContext, BookActivity.class);
                intent.putExtra(BOOK_ID_KEY, books.get(position).getId());
                myContext.startActivity(intent);
            }
        });

        holder.textAuthor.setText(books.get(position).getAuthor());
        holder.textDescription.setText(books.get(position).getShortDescription());

        if (books.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parentCardView);
            holder.expandedRelativeLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

            //TODO: is better to use constants here, REPLACE with a ENUM.
            switch (parentActivity) {
                case "allBooks":
                    holder.textButtonDelete.setVisibility(View.GONE);
                    break;

                case "alreadyRead":
                    holder.textButtonDelete.setVisibility(View.VISIBLE);
                    holder.textButtonDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                            builder.setMessage("Are you sure you want to delete " + books.get(position).getName() + "?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (Utils.getInstance(myContext).removeFromAlreadyRead(books.get(position))) {
                                        Toast.makeText(myContext, "Book removed", Toast.LENGTH_SHORT).show();
                                        //TODO: show some message to the user in case there are no books on the activity - avoid empty screen -> bad user experience

                                        //TODO: right it seems like this method is not being call, all before have been done.
                                        // this can be fixed by creating a callback interface and create a communication between this part of our application
                                        // between this onClick() method and for example AlreadyReadActivity. We can implement that callback interface inside the
                                        // AlreadyReadActivity, so that whenever a book is removed we update the list of our books.
                                        notifyDataSetChanged();
                                    }
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            builder.create().show();
                        }
                    });
                    break;

                case "wantToRead":
                    holder.textButtonDelete.setVisibility(View.VISIBLE);
                    holder.textButtonDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                            builder.setMessage("Are you sure you want to delete " + books.get(position).getName() + "?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (Utils.getInstance(myContext).removeFromWantToRead(books.get(position))) {
                                        Toast.makeText(myContext, "Book removed", Toast.LENGTH_SHORT).show();
                                        //TODO: show some message to the user in case there are no books on the activity - avoid empty screen -> bad user experience
                                        notifyDataSetChanged();
                                    }
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            builder.create().show();
                        }
                    });
                    break;

                case "currentlyReading":
                    holder.textButtonDelete.setVisibility(View.VISIBLE);
                    holder.textButtonDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                            builder.setMessage("Are you sure you want to delete " + books.get(position).getName() + "?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (Utils.getInstance(myContext).removeFromCurrentlyReading(books.get(position))) {
                                        Toast.makeText(myContext, "Book removed", Toast.LENGTH_SHORT).show();
                                        //TODO: show some message to the user in case there are no books on the activity - avoid empty screen -> bad user experience
                                        notifyDataSetChanged();
                                    }
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            builder.create().show();
                        }
                    });
                    break;

                default:
                    holder.textButtonDelete.setVisibility(View.VISIBLE);
                    holder.textButtonDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                            builder.setMessage("Are you sure you want to delete " + books.get(position).getName() + "?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (Utils.getInstance(myContext).removeFromFavorites(books.get(position))) {
                                        Toast.makeText(myContext, "Book removed", Toast.LENGTH_SHORT).show();
                                        //TODO: show some message to the user in case there are no books on the activity - avoid empty screen -> bad user experience
                                        notifyDataSetChanged();
                                    }
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            builder.create().show();
                        }
                    });
                    break;
            }

        } else {
            TransitionManager.beginDelayedTransition(holder.parentCardView);
            holder.expandedRelativeLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView parentCardView;
        private ImageView imageBook;
        private TextView textName;
        // second part - expanded CardView
        private ImageView downArrow, upArrow;
        private RelativeLayout expandedRelativeLayout;
        private TextView textAuthor, textDescription;
        private TextView textButtonDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parentCardView = itemView.findViewById(R.id.parentCardView);
            imageBook = itemView.findViewById(R.id.imageBook);
            textName = itemView.findViewById(R.id.textBookName);

            downArrow = itemView.findViewById(R.id.buttonDownArrow);
            upArrow = itemView.findViewById(R.id.buttonUpArrow);
            expandedRelativeLayout = itemView.findViewById(R.id.expandedRelativeLayout);
            textAuthor = itemView.findViewById(R.id.textAuthor);
            textDescription = itemView.findViewById(R.id.textShortDescription);
            textButtonDelete = itemView.findViewById(R.id.textButtonDelete);

            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
