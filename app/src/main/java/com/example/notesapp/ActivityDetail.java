package com.example.notesapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.notesapp.databinding.ActivityDetailBinding;

public class ActivityDetail extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private final DataBaseHelper db = new DataBaseHelper(ActivityDetail.this);
    private int id = 0;
    private String ID = "";
    private int loveStar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ///khoa night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ///khoa huong dung
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            Note note = (Note) bundle.get("NOTE");
            assert note != null;
            id = note.getId();
            loveStar = note.getLoveStar();
            initView(note);
        }
        ID = (id != 0) ? String.valueOf(id) : "";
        onClick();

        binding.edtDesUd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.ivDoneUpdate.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.edtUpdateTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.ivDoneUpdate.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initView(Note note) {
        binding.edtUpdateTitle.setTextColor(Color.YELLOW);
        String title = note.getTittle();
        String des = note.getDescription();
        String date = note.getDate();
        binding.edtUpdateTitle.setText(title);
        binding.edtDesUd.setText(des);
        binding.tvDate.setText(date);
    }

    private void onClick() {
        binding.ivDoneUpdate.setOnClickListener(v ->{
            String title = binding.edtUpdateTitle.getText().toString();
            String des = binding.edtDesUd.getText().toString();
            if(!title.isEmpty() && !des.isEmpty()){
                db.updateData(ID,title,des,loveStar);//mac dinh chi update noi dung va title
            }
            ///an ban phim ao
            InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View view = getCurrentFocus();
            if(view != null){
                manager.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
            binding.edtDesUd.clearFocus();
            binding.edtUpdateTitle.clearFocus();
            //an nut V
            binding.ivDoneUpdate.setVisibility(View.INVISIBLE);
        });

        binding.ivBackUpdate.setOnClickListener(v -> {
            startActivity(new Intent(ActivityDetail.this, MainActivity.class));
            finish();
        });

    }
}
