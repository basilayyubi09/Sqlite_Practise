package com.peaceinfotech.sqlitepractise;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycle;
    FloatingActionButton addBtn;
    List<BookModel> list;
    BookAdapter adapter;
    DatabaseHelper databaseHelper;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        addBtn = findViewById(R.id.addBtn);
        recycle = findViewById(R.id.recycle);
        addBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddActivity.class)));
        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


        list = databaseHelper.getBooks();
        if (list.size() > 0) {
            adapter = new BookAdapter(list, MainActivity.this);
            recycle.setAdapter(adapter);
        } else {
            Toast.makeText(this, "ohh no", Toast.LENGTH_SHORT).show();
            textToSpeech.speak("Oh no.. No Data Found.", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}