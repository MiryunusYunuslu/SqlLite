package com.example.sqllite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ShowRecycler extends Fragment {
    private ArrayList<Art> artArrayList;
    public RecyclerView recyclerView;
    private  ArtAdapter artAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        artArrayList=new ArrayList<>();
        artAdapter=new ArtAdapter(artArrayList);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(artAdapter);
        getData();
    }
    private void getData() {
        SQLiteDatabase sqLiteDatabase=getActivity().openOrCreateDatabase("arts", Context.MODE_PRIVATE,null);
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM arts",null);
        int nameIx=cursor.getColumnIndex("artname");
        int idIx=cursor.getColumnIndex("id");
        while(cursor.moveToNext()){
            String name=cursor.getString(nameIx);
            int id=cursor.getInt(idIx);
            Art art=new Art(name,id);
            artArrayList.add(art);
        }
        artAdapter.notifyDataSetChanged();
        cursor.close();
    }
}