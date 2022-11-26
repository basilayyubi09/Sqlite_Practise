package com.peaceinfotech.sqlitepractise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created By BASIL AYYUBI on 26-11-2022.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.viewHolder> {
    List<BookModel> list;
    Context context;
    DatabaseHelper databaseHelper;
    public BookAdapter(List<BookModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_single, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        BookModel model = list.get(position);
        holder.idTv.setText(String.valueOf(model.id));
        holder.titleTv.setText(model.bookTitle);
        holder.authorTv.setText(model.authorName);
        holder.pageTv.setText(String.valueOf(model.pages));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , DetailsActivity.class);
                intent.putExtra("id" , model.id);
                context.startActivity(intent);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , UpdateActivity.class);
                intent.putExtra("id" , model.id);
                intent.putExtra("title" , model.bookTitle);
                intent.putExtra("author" , model.authorName);
                intent.putExtra("pages" , model.pages);
                intent.putExtra("content" , model.content);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete "+model.bookTitle +"?");
                builder.setMessage("Are you sure you want to delete "+model.bookTitle+" ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHelper = new DatabaseHelper(context);
                        long result = databaseHelper.deleteBook(model.id);
                        if (result==-1){
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            int position = list.indexOf(model);
                            list.remove(position);
                            notifyItemRangeChanged(position , list.size());
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView pageTv, authorTv, titleTv, idTv;
        ImageView edit , delete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            pageTv = itemView.findViewById(R.id.pageTv);
            authorTv = itemView.findViewById(R.id.authorTv);
            titleTv = itemView.findViewById(R.id.titleTv);
            idTv = itemView.findViewById(R.id.idTv);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
