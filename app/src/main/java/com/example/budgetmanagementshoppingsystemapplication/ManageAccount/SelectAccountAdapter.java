package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectAccountAdapter extends RecyclerView.Adapter<SelectAccountAdapter.ViewHolder> {
    ArrayList<Customer> selectAccountList;
    Context context;

    public SelectAccountAdapter(ArrayList<Customer> selectAccountList, Context context) {
        this.selectAccountList = selectAccountList;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.select_account_row,parent,false);
        return new SelectAccountAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectAccountAdapter.ViewHolder holder, int position) {
        Customer customer = this.selectAccountList.get(position);
        if (!customer.getProfilePic().matches("null"))
            Picasso.get().load(customer.getProfilePic()).into(holder.profilePic);
        else
            holder.profilePic.setImageResource(R.drawable.profile_icon);
        holder.username.setText(customer.getUsername());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNewPassword.class);
                intent.putExtra("username",customer.getUsername());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return selectAccountList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView profilePic;
        TextView username;

        public View mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            username = itemView.findViewById(R.id.username);
            mainLayout = itemView;
        }

    }

}

