package com.example.words;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddWord extends AppCompatActivity {

    Button btn_add;
    TextView txt_word_name;
    TextView txt_word_meaning;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        btn_add = (Button) findViewById(R.id.btn_add_add);
        txt_word_name = (TextView) findViewById(R.id.wordadd_txt_wordname);
        txt_word_meaning = (TextView) findViewById(R.id.wordadd_txt_meaning);
        context = this;


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    SQLiteDatabase database = context.openOrCreateDatabase("EnglishWordsNew",MODE_PRIVATE,null);
                    String wn = txt_word_name.getText().toString();
                    String mn = txt_word_meaning.getText().toString();
                    String sql = "INSERT INTO words (word_name,meaning) VALUES ('"+wn+"','"+mn+"')";
                    database.execSQL(sql);
                    Intent intent = new Intent(context,MainActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }
}