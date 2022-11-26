package com.peaceinfotech.sqlitepractise;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    EditText bookTitle , authorName,bookPage;
    Button addBtn;
    DatabaseHelper databaseHelper;
    TextToSpeech textToSpeech;
    BookModel model;
    String content = "What is Lorem Ipsum?\n" +
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        databaseHelper = new DatabaseHelper(AddActivity.this);

        addBtn = findViewById(R.id.addBtn);
        bookTitle = findViewById(R.id.bookTitle);
        authorName = findViewById(R.id.authorName);
        bookPage = findViewById(R.id.bookPage);

        textToSpeech  = new TextToSpeech(AddActivity.this, new TextToSpeech.OnInitListener() {
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
                    Toast.makeText(AddActivity.this, "Enter Book Title", Toast.LENGTH_SHORT).show();
                }
                else if (authorName.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this, "Enter Author Name", Toast.LENGTH_SHORT).show();
                }
                else if (bookPage.getText().toString().equals("")){
                    Toast.makeText(AddActivity.this, "Enter Book Page", Toast.LENGTH_SHORT).show();
                }
                else {

                    model = new BookModel(bookTitle.getText().toString() , authorName.getText().toString() ,  Integer.valueOf(bookPage.getText().toString()) , content);
                    long result = databaseHelper.addBook(model);
                    if (result==-1){
                        Toast.makeText(AddActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        textToSpeech.speak("ohh yeah...Added Successfully", TextToSpeech.QUEUE_FLUSH, null);
                        Toast.makeText(AddActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        authorName.setText("");
                        bookPage.setText("");
                        bookTitle.setText("");
                        finish();
                    }
                }
            }
        });
    }
}