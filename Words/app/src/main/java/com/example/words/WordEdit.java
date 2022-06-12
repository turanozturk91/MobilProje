package com.example.words;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WordEdit extends AppCompatActivity {

    TextView txt_wn;
    TextView txt_meaning;
    Button btn_save;
    ImageButton btn_back;
    int word_id;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_edit);
        txt_wn = (TextView) findViewById(R.id.wordadd_txt_wordname);
        txt_meaning = (TextView) findViewById(R.id.wordadd_txt_meaning);
        btn_save = (Button) findViewById(R.id.btn_add_add);
        btn_back = (ImageButton) findViewById(R.id.btn_edit_back);
        context = this;

        Intent intent = getIntent();
        String word_name = intent.getStringExtra("WORD_NAME");
        String meaning = intent.getStringExtra("WORD_MEANING");
        word_id = intent.getIntExtra("WORD_ID",0);
        txt_wn.setText(word_name);
        txt_meaning.setText(meaning);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wn = txt_wn.getText().toString();
                String mn = txt_meaning.getText().toString();

                try{
                    SQLiteDatabase database = context.openOrCreateDatabase("EnglishWordsNew",MODE_PRIVATE,null);
                    String sql = "UPDATE words SET word_name='"+wn+"', meaning='"+mn+"' WHERE word_id="+word_id;
                    System.out.println("SQL SORGUMUZZZZ"+sql);
                    database.execSQL(sql);
                    recreate();
                }catch(Exception exceptione){
                    exceptione.printStackTrace();
                }
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_2 = new Intent(context,MainActivity.class);
                startActivity(intent_2);
            }
        });




    }







}