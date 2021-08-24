package com.example.sqllite;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.RowHolder> {
    private ArrayList<Art> artArrayList;
    public ArtAdapter(ArrayList<Art> artarrayList) {
        this.artArrayList = artarrayList;
    }
    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new RowHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bookName.setText(artArrayList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity= (AppCompatActivity) v.getContext();
                BookListFragment bookListFragment=new BookListFragment(artArrayList.get(position).getId());
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout,bookListFragment).addToBackStack("tag").commit();
            }
        });
    }

    @Override
    public int getItemCount() {

        return artArrayList.size();

    }

    public static class RowHolder extends RecyclerView.ViewHolder {
        TextView bookName;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookName);
        }
    }
}
