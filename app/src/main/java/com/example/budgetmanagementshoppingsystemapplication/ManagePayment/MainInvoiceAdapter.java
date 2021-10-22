package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.Model.ShoppingCart;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainInvoiceAdapter extends RecyclerView.Adapter<MainInvoiceAdapter.ViewHolder> {
    Context context;
    String paymentID;

    public MainInvoiceAdapter(Context context, String paymentID) {
        this.context = context;
        this.paymentID = paymentID;
    }

    @NonNull
    @Override
    public MainInvoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.invoice_section, parent,false);
        return new MainInvoiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainInvoiceAdapter.ViewHolder holder, int position) {
        holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.invoiceID.setText(paymentID);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Payment").child(paymentID);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Payment paymentDetail = snapshot.getValue(Payment.class);
                holder.totalPay.setText(paymentDetail.getAmountPay());
                holder.paymentType.setText(paymentDetail.getPaymentType());
                holder.datetime.setText(paymentDetail.getDateTime());
                holder.custName.setText(paymentDetail.getCustomerName());

                ArrayList<ShoppingCart> invoiceItem = new ArrayList<>();
                float  totalPrice = 0;
                for (DataSnapshot snapshot1 : snapshot.child("itemPurchase").getChildren()) {
                    ShoppingCart product = snapshot1.getValue(ShoppingCart.class);
                    totalPrice += Float.parseFloat(product.getTotalPrice());
                    invoiceItem.add(product);
                }

                InvoiceItemAdapter custInvoiceAdapter = new InvoiceItemAdapter(context, invoiceItem);
                holder.childRecyclerView.setAdapter(custInvoiceAdapter);
                custInvoiceAdapter.notifyDataSetChanged();

                float totalPay = Float.parseFloat(holder.totalPay.getText().toString());
                holder.totalPrice.setText(String.format("%.2f",totalPrice));
                holder.change.setText(String.format("%.2f",totalPay-totalPrice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerView childRecyclerView;
        TextView totalPrice, paymentType, totalPay, change, invoiceID, datetime, custName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childRecyclerView = itemView.findViewById(R.id.invoiceChildRecyclerView);
            totalPrice = itemView.findViewById(R.id.total);
            paymentType = itemView.findViewById(R.id.debitCreditCard);
            totalPay = itemView.findViewById(R.id.debitCreditCardCharge);
            change = itemView.findViewById(R.id.change);
            invoiceID = itemView.findViewById(R.id.paymentID);
            datetime = itemView.findViewById(R.id.datePayment);
            custName = itemView.findViewById(R.id.custName);

        }
    }
}
