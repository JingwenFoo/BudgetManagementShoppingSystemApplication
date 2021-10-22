package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.Model.ShoppingCart;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InvoiceItemAdapter extends RecyclerView.Adapter<InvoiceItemAdapter.InvoiceViewHolder> {
    Context context;
    ArrayList<ShoppingCart> invoiceItems;

    public InvoiceItemAdapter(Context context, ArrayList<ShoppingCart> invoiceItems) {
        this.context = context;
        this.invoiceItems = invoiceItems;
    }

    @NonNull
    @Override
    public InvoiceItemAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.customer_invoice_row,parent,false);
        return new InvoiceItemAdapter.InvoiceViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceItemAdapter.InvoiceViewHolder holder, int position) {
        ShoppingCart product = this.invoiceItems.get(position);
        holder.quantity.setText(product.getProductQuantity());
        holder.totalPrice.setText(product.getTotalPrice());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product item = snapshot.getValue(Product.class);
                holder.productName.setText(item.getProductName());
                holder.unitPrice.setText(String.format("%.2f",item.getProductPrice()));

                int quantity = Integer.parseInt(product.getProductQuantity());
                if (item.getSellingPrice() < item.getProductPrice()) {
                    Float promotion = (item.getProductPrice()*quantity) - (item.getProductPrice()*quantity*(100-Float.parseFloat(product.getDiscount())));
                    holder.discount.setText(String.format("%.2f", promotion));

                } else {
                    holder.discount.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(invoiceItems == null)
            return 0;
        else
            return invoiceItems.size();
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder{

        TextView productName, totalPrice, unitPrice, discount, quantity;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.custIVProductName);
            totalPrice = itemView.findViewById(R.id.custIVTotalPrice);
            quantity = itemView.findViewById(R.id.custIVQuantity);
            unitPrice = itemView.findViewById(R.id.custIVUnitPrice);
            discount = itemView.findViewById(R.id.custIVdiscount);
        }
    }
}