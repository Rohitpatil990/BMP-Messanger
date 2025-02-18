package com.example.bmpmessanger;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class userAdapter extends RecyclerView.Adapter<userAdapter.viewholder> {
    Context mainActivity;
    ArrayList<Users> usersArrayList;
    public userAdapter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity=mainActivity;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public userAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.viewholder holder, int position) {

        Users users = usersArrayList.get(position);
        holder.username.setText(users.Username);
        holder.userstatus.setText(users.Status);
        Picasso.get().load(users.profileImg).into(holder.userimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, chatwindow.class);
                intent.putExtra("nameeee",users.getUsername());
                intent.putExtra("receiverImg", users.getProfileImg());
                intent.putExtra("uid",users.getUserId());
                mainActivity.startActivity(intent);
            }
        });

        if (users.getProfileImg() != null && !users.getProfileImg().isEmpty()) {
            Picasso.get()
                    .load(users.getProfileImg())
                    .placeholder(R.drawable.man) // Placeholder while loading
                    .error(R.drawable.man) // Default if loading fails
                    .into(holder.userimg);
        } else {
            holder.userimg.setImageResource(R.drawable.man);
        }
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CircleImageView userimg;
        TextView username;
        TextView userstatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userimg = itemView.findViewById(R.id.userImg);
            username = itemView.findViewById(R.id.username);
            userstatus = itemView.findViewById(R.id.status);
        }
    }
}