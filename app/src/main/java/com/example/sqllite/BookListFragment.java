package com.example.sqllite;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class BookListFragment extends Fragment {
    private TextView artNameText, artistNameText, yearText;
    private ImageView imageView;
    private Button deleteButtton;
    private int id;
    private SQLiteDatabase sqLiteDatabase;
    public BookListFragment(int id) {
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sqLiteDatabase = getActivity().openOrCreateDatabase("arts", Context.MODE_PRIVATE, null);
        MainActivity mainActivity=new MainActivity();
        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        setValues();
    }
    private void setValues() {
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM arts WHERE id =?",new String[]{String.valueOf(id)});
        int artNameIx=cursor.getColumnIndex("artname");
        int painterNameIx=cursor.getColumnIndex("paintername");
        int yearIx=cursor.getColumnIndex("year");
        int imageIx=cursor.getColumnIndex("image");
        while(cursor.moveToNext()){
         artNameText.setText(cursor.getString(artNameIx));
         artistNameText.setText(cursor.getString(painterNameIx));
         yearText.setText(cursor.getString(yearIx));
         byte [] bytes=cursor.getBlob(imageIx);
         Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
         imageView.setImageBitmap(bitmap);
        }
        deleteButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBook();
            }
        });
            }
    private void deleteBook() {
        sqLiteDatabase.execSQL("DELETE FROM arts WHERE id=?", new String[]{String.valueOf(id)});
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new ShowRecycler());
        fragmentTransaction.commit();
    }
    private void initialize(View view) {
        artistNameText = view.findViewById(R.id.artistnameText);
        artNameText = view.findViewById(R.id.artText);
        yearText = view.findViewById(R.id.artyearText);
        imageView = view.findViewById(R.id.selectedImage);
        deleteButtton=view.findViewById(R.id.deleteButton);
    }
    @Override
    public void onAttach(Context context) {
        context = getContext();
        super.onAttach(context);
        setHasOptionsMenu(true);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.addArt).setVisible(false);
    }
}
