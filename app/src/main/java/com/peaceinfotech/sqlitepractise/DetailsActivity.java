package com.peaceinfotech.sqlitepractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    TextView content , title , author , page;
    Integer id;
    ImageView hear;
    boolean isPlaying = false;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getIntExtra("id" , 0);
        }
        content = findViewById(R.id.content);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        page = findViewById(R.id.page);
        hear = findViewById(R.id.hear);

        textToSpeech  = new TextToSpeech(DetailsActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        hear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying){
                    if (textToSpeech.isSpeaking()){

                        textToSpeech.stop();
                    }
                    isPlaying = false;
                    hear.setImageResource(R.drawable.hear);
                }
                else {
                    isPlaying = true;
                    hear.setImageResource(R.drawable.non_hear);
                    textToSpeech.speak(content.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHelper helper = new DatabaseHelper(DetailsActivity.this);
        List<BookModel> list = helper.getSingleBook(id);
        if (list.size()>0){
            content.setText(list.get(0).content);
            title.setText(list.get(0).bookTitle);
            author.setText(list.get(0).authorName);
            page.setText(String.valueOf(list.get(0).pages));
        }
        else {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (textToSpeech.isSpeaking()){
            isPlaying = false;
            hear.setImageResource(R.drawable.hear);
            textToSpeech.stop();
        }
    }
}