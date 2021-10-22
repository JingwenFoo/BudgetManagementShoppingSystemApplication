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

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.InvoiceViewHolder> {
    Context context;
    List<Payment> invoiceList;

    public InvoiceListAdapter(Context context, List<Payment> invoiceList) {
        this.context = context;
        this.invoiceList = invoiceList;
    }

    @NonNull
    @Override
    public InvoiceListAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.view_invoice_row,parent,false);
        return new InvoiceListAdapter.InvoiceViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceListAdapter.InvoiceViewHolder holder, int position) {
        Payment invoice = this.invoiceList.get(position);

        holder.no.setText(String.valueOf(position+1));
        holder.custName.setText(invoice.getCustomerName());
        holder.datetime.setText(invoice.getDateTime());
        holder.totalPurchase.setText(invoice.getAmountPay());
        holder.status.setText(invoice.getPaymentStatus());
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

        TextView no, custName, datetime, totalPurchase, status;

        public  View mainLayout;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.IVNo);
            custName = itemView.findViewById(R.id.IVCustName);
            datetime = itemView.findViewById(R.id.IVDate);
            status = itemView.findViewById(R.id.IVStatus);
            totalPurchase = itemView.findViewById(R.id.IVAmount);
            mainLayout = itemView;
        }
    }
}