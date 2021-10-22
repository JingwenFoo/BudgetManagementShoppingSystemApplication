package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.R;


import java.util.List;

public class CustomerHistoryAdapter extends  RecyclerView.Adapter<CustomerHistoryAdapter.InvoiceViewHolder> {
    Context context;
    List<Payment> invoiceList;

    public CustomerHistoryAdapter(Context context, List<Payment> invoiceList) {
        this.context = context;
        this.invoiceList = invoiceList;
    }

    @NonNull
    @Override
    public CustomerHistoryAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.customer_history_row,parent,false);
        return new InvoiceViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHistoryAdapter.InvoiceViewHolder holder, int position) {
        Payment invoice = this.invoiceList.get(position);
        holder.invoiceID.setText(invoice.getPaymentID());
        holder.datetime.setText(invoice.getDateTime());
        holder.totalPurchase.setText("RM "+invoice.getAmountPay());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent(context, CustomerInvoice.class);
                view.putExtra("invoiceID", invoiceList.get(position).getPaymentID());
                context.startActivity(view);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(invoiceList == null)
            return 0;
        else
            return invoiceList.size();
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder{

        TextView invoiceID, datetime, totalPurchase;

        public  View mainLayout;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            invoiceID = itemView.findViewById(R.id.invoiceID);
            datetime = itemView.findViewById(R.id.dateTime);
            totalPurchase = itemView.findViewById(R.id.totalPurchase);
            mainLayout = itemView;
        }
    }
}
