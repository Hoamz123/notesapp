package com.example.notesapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.notesapp.databinding.ActivityStartBinding;

public class ActivityStart extends AppCompatActivity {

    private ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ///khoa night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ///khoa huong dung
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();
    }
    private void initView() {
        binding.progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() ->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();///ket thuc act hien tai
            //onDestroy();
        },1500);
        ///hanh dong se duoc thuc hien sau 2.5s (-> doan code ben trong se duoc chay)
    }
}
