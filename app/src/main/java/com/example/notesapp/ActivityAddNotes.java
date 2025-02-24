package com.example.notesapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.notesapp.databinding.ActivityAddNotesBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityAddNotes extends AppCompatActivity {

    private ActivityAddNotesBinding binding;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ///khoa night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ///khoa huong dung
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onClickBack();
        initView();
    }

    private void onClickBack() {
        binding.ivBackToMain.setOnClickListener(onClick ->{
            finish();
        });
    }

    private void initView() {
        db = new DataBaseHelper(this);
        binding.btnAddNotes.setOnClickListener(v ->{
            initData();
        });
    }
    private void initData() {
        String tittle = binding.edtTitle.getText().toString();
        String des = binding.edtDes.getText().toString();
        if(!tittle.isEmpty() && !des.isEmpty()) {
            boolean check = db.insertData(tittle, des,getDate(),0);//mac dinh luc dau ko love
            db.close();
            if (check) {
                Toast.makeText(this, "Note successfully", Toast.LENGTH_SHORT).show();
            }
            binding.edtTitle.setText("");
            binding.edtDes.setText("");
            startActivity(new Intent(ActivityAddNotes.this, MainActivity.class));
        }
        else{
            Toast.makeText(this, "!OK", Toast.LENGTH_SHORT).show();
        }
    }

    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String date =  simpleDateFormat.format(new Date()) + " " + String.format("%s Tháng %s",day,month + 1);
        return date;
    }

}