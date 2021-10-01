package com.example.budgetmanagementshoppingsystemapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    ArrayList<Customer> accountList;
    Context context;

    public AccountAdapter(ArrayList<Customer> accountList, Context context) {
        this.accountList = accountList;
        this.context = context;
    }

    @NonNull
    @Override
    public AccountAdapter.AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.account_list_row,parent,false);
        return new AccountViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.AccountViewHolder holder, int position) {
        Customer customer = this.accountList.get(position);
        holder.no.setText(String.valueOf(position+1));
        holder.custName.setText(customer.getName());
        holder.viewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent(context,ViewAccountDetail.class);
                view.putExtra("uid",accountList.get(position).getUid());
                context.startActivity(view);
            }
        });
        holder.deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(accountList.get(position).getUid());
            }
        });
    }
    private void confirmDialog(String uid)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Customer?");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Customer");
                ref.child(uid).removeValue();
                Toast.makeText(context,"Customer deleted successfully",Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(context,ViewAccountList.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(refresh);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder{

        TextView no, custName;
        Button viewAccount, deleteAccount;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.no);
            custName = itemView.findViewById(R.id.customerNameList);
            viewAccount = itemView.findViewById(R.id.viewAccount);
            deleteAccount = itemView.findViewById(R.id.deleteAccount);
        }

    }

}

