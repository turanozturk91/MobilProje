package com.example.words;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHolder> implements Filterable {

    List<Word> wordArrayList;
    List<Word> wordArrayListFull;
    Context context;


    public void setFilteredList(List<Word> filteredList){
        this.wordArrayList = filteredList;
        notifyDataSetChanged();
    }


    public WordAdapter(Context c, List<Word> wordArrayList){
        context = c;
        this.wordArrayList = wordArrayList;
        this.wordArrayListFull = new ArrayList<>(wordArrayList);

    }

    @NonNull
    @Override
    public WordAdapter.WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.wordlist_row,parent,false);

        return new WordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.WordHolder holder, @SuppressLint("RecyclerView") int position) {
        System.out.println("si323232zeee : " + wordArrayList.size());
        holder.word_name.setText(wordArrayList.get(position).word_name);
        holder.word_meaning.setText(wordArrayList.get(position).meaning);

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,WordEdit.class);
                intent.putExtra("WORD_NAME",wordArrayList.get(position).word_name);
                intent.putExtra("WORD_MEANING",wordArrayList.get(position).meaning);
                intent.putExtra("WORD_ID",wordArrayList.get(position).word_id);
                context.startActivity(intent);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    SQLiteDatabase database = context.openOrCreateDatabase("EnglishWordsNew",Context.MODE_PRIVATE,null);
                    int word_id = wordArrayList.get(position).word_id;
                    String sql = "DELETE FROM words WHERE word_id="+word_id;
                    database.execSQL(sql);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return wordArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Word> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(wordArrayListFull);
            }else{
                String filterPattern =  charSequence.toString().toLowerCase().trim();
                for(Word word:wordArrayListFull){
                    if(word.word_name.toLowerCase().contains(filterPattern)){
                        filteredList.add(word);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            wordArrayList.clear();
            wordArrayList.addAll( (List) filterResults.values );
            notifyDataSetChanged();
        }
    };



    public class WordHolder extends RecyclerView.ViewHolder {

        TextView word_name;
        ImageButton btn_edit;
        ImageButton btn_delete;
        TextView word_meaning;

        public WordHolder(@NonNull View itemView) {
            super(itemView);
            word_name = itemView.findViewById(R.id.txt_wordname);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            word_meaning = itemView.findViewById(R.id.txt_meaning);
        }



    }
}
