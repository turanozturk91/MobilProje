package com.example.words;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    List<Word> wordArrayList = new ArrayList<>();
    RecyclerView rv;
    SearchView searchView;
    Button btn_addnew;
    Context context;
    Inflater inflater;
    WordAdapter wordAdapter;
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv =  findViewById(R.id.rvwords);
        noData = findViewById(R.id.noData);
        wordAdapter = new WordAdapter(this,wordArrayList);
        btn_addnew = (Button) findViewById(R.id.btn_add_nw);
        context = this;


        searchView = (SearchView) findViewById(R.id.arama);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });






        try{
            SQLiteDatabase database = this.openOrCreateDatabase("EnglishWordsNew",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS words (word_id INTEGER PRIMARY KEY, word_name VARCHAR, meaning VARCHAR)");
            //database.execSQL("INSERT INTO words (word_name,meaning) VALUES ('run','koş')");
            //database.execSQL("INSERT INTO words (word_name,meaning) VALUES ('fly','uç')");

            Cursor cursor = database.rawQuery("SELECT * FROM words",null);
            int name_index = cursor.getColumnIndex("word_name");
            int meaning_index = cursor.getColumnIndex("meaning");
            int id_index = cursor.getColumnIndex("word_id");
            while(cursor.moveToNext()){
                String word_name = cursor.getString(name_index);
                String meaning = cursor.getString(meaning_index);
                int word_id = cursor.getInt(id_index);
                Word word = new Word(word_id,word_name,meaning);
                wordArrayList.add(word);
            }
            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }


        rv.setAdapter(wordAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

        btn_addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AddWord.class);
                startActivity(intent);
            }
        });


        if( wordArrayList.size() == 0 ){
            rv.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.VISIBLE);
        }else {
            rv.setVisibility(View.VISIBLE);
            noData.setVisibility(View.INVISIBLE);
        }

    }


    private void filterList(String text){
        List<Word> filteredList = new ArrayList<>();
        for (Word word : wordArrayList){
            System.out.println(word.word_name);
            if(word.word_name.toLowerCase().contains(text.toLowerCase())){
                filteredList.add(word);
                System.out.println("WORD" + word.word_name);
            }

            if (filteredList.isEmpty()){

            }else{
                wordAdapter.setFilteredList(filteredList);
            }
        }
    }



}