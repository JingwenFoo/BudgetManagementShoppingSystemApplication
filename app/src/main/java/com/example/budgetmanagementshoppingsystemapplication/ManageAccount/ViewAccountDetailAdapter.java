package com.example.budgetmanagementshoppingsystemapplication.ManageAccount;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.CustomerHistoryAdapter;
import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ViewAccountDetailAdapter extends RecyclerView.Adapter<ViewAccountDetailAdapter.ViewHolder> {
    Context context;
    String uid;
    CustomerHistoryAdapter adapter;
    public ViewAccountDetailAdapter(Context context, String uid) {
        this.context = context;
        this.uid = uid;
    }

    @NonNull
    @Override
    public ViewAccountDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_view_account_detail, parent,false);
        return new ViewAccountDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAccountDetailAdapter.ViewHolder holder, int position) {
        holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        DatabaseReference refCust = FirebaseDatabase.getInstance().getReference().child("Customer").child(uid);
        refCust.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.getValue(Customer.class);
                holder.name.setText(String.valueOf(customer.getName()));
                holder.phone.setText(String.valueOf(customer.getPhone()));
                holder.email.setText(String.valueOf(customer.getEmail()));
                holder.address.setText(String.valueOf(customer.getAddress()));
                holder.id.setText(uid);
                holder.username.setText(String.valueOf(customer.getUsername()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        List<Payment> purchaseHistory = new ArrayList<>();
        DatabaseReference refPayment = FirebaseDatabase.getInstance().getReference().child("Payment");
        refPayment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Payment invoice = snapshot1.getValue(Payment.class);
                    if(invoice.getCustomerName()!=null && invoice.getCustomerName().matches(holder.name.getText().toString()))
                    {
                        String invoiceID = snapshot1.child("paymentID").getValue(String.class);
                        String dateTime = snapshot1.child("datetime").getValue(String.class);
                        String totalPurchase = snapshot1.child("amountPay").getValue(String.class);

                        purchaseHistory.add(new Payment(invoiceID, totalPurchase, dateTime));

                    }
                }
                Collections.reverse(purchaseHistory);
                adapter = new CustomerHistoryAdapter(context, purchaseHistory);
                holder.childRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Payment").orderByChild("datetime")
                        .startAt(s.toString())
                        .endAt(s.toString()+"\uf8ff");

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        purchaseHistory.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Payment inv = dataSnapshot.getValue(Payment.class);
                            if(inv.getCustomerName()!=null && inv.getCustomerName().matches(holder.name.getText().toString()))
                                purchaseHistory.add(inv);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerView childRecyclerView;
        TextView name, username, id, phone, email, address;
        EditText search;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childRecyclerView = itemView.findViewById(R.id.custInvoiceListRecyclerView);
            name = itemView.findViewById(R.id.custNameTextView);
            username = itemView.findViewById(R.id.custUsernameTextView);
            id = itemView.findViewById(R.id.custIDTextView);
            phone = itemView.findViewById(R.id.custContactTextView);
            email = itemView.findViewById(R.id.custEmailTextView);
            address = itemView.findViewById(R.id.custAddressTextView);
            search = itemView.findViewById(R.id.searchInvoiceList);

        }
    }

}
