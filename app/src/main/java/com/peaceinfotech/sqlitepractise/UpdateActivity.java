package com.peaceinfotech.sqlitepractise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {
    EditText bookTitle , authorName,bookPage;
    Button addBtn;
    DatabaseHelper databaseHelper;
    TextToSpeech textToSpeech;
    BookModel model;
    String title , author , content ;
    Integer id , page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        databaseHelper = new DatabaseHelper(UpdateActivity.this);

        addBtn = findViewById(R.id.addBtn);
        bookTitle = findViewById(R.id.bookTitle);
        authorName = findViewById(R.id.authorName);
        bookPage = findViewById(R.id.bookPage);

        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getIntExtra("id" , 0);
            title = intent.getStringExtra("title");
            author = intent.getStringExtra("author");
            content = intent.getStringExtra("content");
            page = intent.getIntExtra("pages" , 0);

            setTextOnFields();
        }
        textToSpeech  = new TextToSpeech(UpdateActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookTitle.getText().toString().equals("")){
                    Toast.makeText(UpdateActivity.this, "Enter Book Title", Toast.LENGTH_SHORT).show();
                }
                else if (authorName.getText().toString().equals("")){
                    Toast.makeText(UpdateActivity.this, "Enter Author Name", Toast.LENGTH_SHORT).show();
                }
                else if (bookPage.getText().toString().equals("")){
                    Toast.makeText(UpdateActivity.this, "Enter Book Page", Toast.LENGTH_SHORT).show();
                }
                else {

                    model = new BookModel(id,bookTitle.getText().toString() , authorName.getText().toString() ,  Integer.valueOf(bookPage.getText().toString()) , content);

                    long result = databaseHelper.updateBook(model);

                    if (result==-1){
                        textToSpeech.speak("ohh noo...Something Went Wrong", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else {
                        textToSpeech.speak("Update value is... Book Title is "+bookTitle.getText().toString()+" and author name is "+authorName.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);

                    }
                }
            }
        });



    }

    private void setTextOnFields() {
        bookTitle.setText(title);
        authorName.setText(author);
        bookPage.setText(String.valueOf(page));
    }
}