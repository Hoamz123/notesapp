package hoamz.notesapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;


import com.hoamz.notesapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DataBaseHelper db;
    private List<Note> listNote;
    private AdapterRcView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ///khoa night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ///khoa huong dung
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        db = new DataBaseHelper(this);
        listNote = getData();
        adapter = new AdapterRcView(this,listNote,db);
        onClickFab();
        binding.rcViewNotes.setAdapter(adapter);
        binding.rcViewNotes.setLayoutManager(new GridLayoutManager(this,1));
        binding.rcViewNotes.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    private void onClickFab() {
        //xu li nut fab
        binding.btnFab.setOnClickListener(v ->{
            startActivity(new Intent(this, ActivityAddNotes.class));
        });
    }
    private List<Note> getData() {
        Cursor cursor = db.getAllTableData();
        List<Note> noteList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Note note = new Note(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
                noteList.add(note);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return noteList;
    }
}