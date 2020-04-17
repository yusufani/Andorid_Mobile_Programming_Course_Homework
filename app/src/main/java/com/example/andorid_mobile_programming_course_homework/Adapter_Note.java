package com.example.andorid_mobile_programming_course_homework;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class Adapter_Note extends RecyclerView.Adapter<Adapter_Note.set_list> {
    Context context;
    public Adapter_Note(Context context, ArrayList<Note> notes) {
        this.context = context;
        this. notes= notes;
    }

    ArrayList<Note> notes;
    @NonNull
    @Override
    public set_list onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_card,parent,false);
        return new set_list(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final set_list holder, final int position) {
        holder.title.setText("Başlık: " + notes.get(position).title +"\nOluşturma Tarihi: "+notes.get(position).time);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dir = context.getFilesDir().getAbsolutePath();
                File f0 = new File(dir, notes.get(position).text_name);
                boolean d0 = f0.delete();
                Log.w("Delete Check", "File deleted: " + dir + "/myFile " + d0);
                notes.remove(position);
                setData(notes);
            }
        });
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Note_edit.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("NOTES", notes);
                intent.putExtra("pos",position);
                intent.putExtra("USERNAME", notes.get(position).getOwner());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class set_list extends RecyclerView.ViewHolder {
        TextView title ;
        LinearLayout linearLayout;
        Button show;
        Button delete;
        public set_list(@NonNull View itemView) {
            super(itemView);
                show = itemView.findViewById(R.id.notecard_show);
                delete = itemView.findViewById(R.id.notecard_delete);
                title = itemView.findViewById(R.id.note_id);
        }
    }
    public void setData(ArrayList<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }
}
