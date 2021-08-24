package com.example.sqllite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class ChooseFragment extends Fragment {
    private Uri uri;
    private ImageView selectImage;
    private EditText artname, artistname, year;
    private Bitmap bitmap;
    public static Menu menu1;
    private byte[] byteArray;
    private Button saveButon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSelectImage();
            }
        });
        saveButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSaveButton();
            }
        });
    }
    public void initialize(View view) {
        selectImage = view.findViewById(R.id.selectImage);
        artistname = view.findViewById(R.id.artistname);
        artname = view.findViewById(R.id.artname);
        year = view.findViewById(R.id.artyear);
        saveButon=view.findViewById(R.id.saveInfo);
    }
    public void clickSaveButton() {
        SQLiteDatabase database = getContext().openOrCreateDatabase("arts", Context.MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS arts(id INTEGER PRIMARY KEY,artname VARCHAR,paintername VARCHAR,year VARCHAR,image BLOB)");
        String sqlString = "INSERT INTO arts(artname,paintername,year,image) VALUES(?,?,?,?)";
        SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
        sqLiteStatement.bindString(1, artname.getText().toString());
        sqLiteStatement.bindString(2, artistname.getText().toString());
        sqLiteStatement.bindString(3, year.getText().toString());
        sqLiteStatement.bindBlob(4, byteArray);
        sqLiteStatement.execute();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new ShowRecycler());
        fragmentTransaction.commit();
    }
    public void clickSelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Imae"), 101);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.getData();
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                selectImage.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
                byteArray = baos.toByteArray();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
