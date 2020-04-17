package com.example.andorid_mobile_programming_course_homework;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_People extends RecyclerView.Adapter<Adapter_People.set_list> {
    Context context;
    public Adapter_People(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    ArrayList<User> users;
    @NonNull
    @Override
    public set_list onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.personcard,parent,false);
        return new set_list(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final set_list holder, int position) {
        holder.username.setText(users.get(position).getUsername());
        holder.password.setText(users.get(position).getPassword());
        holder.image.setImageResource(users.get(position).getImageID());
        holder.password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("input Type" ,String.valueOf(holder.password.getInputType()));
                if (holder.password.getInputType() == 129)
                    holder.password.setInputType(1);
                else if (holder.password.getInputType() == 1)
                    holder.password.setInputType(129);


            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class set_list extends RecyclerView.ViewHolder {
        ImageView image ;
        TextView username;
        TextView password;
    public set_list(@NonNull View itemView) {

        super(itemView);
        image = itemView.findViewById(R.id.card_image);
        username = itemView.findViewById(R.id.card_username);
        password= itemView.findViewById(R.id.card_password);
    }
}
}
