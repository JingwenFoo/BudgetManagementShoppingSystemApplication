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

public class CheckoutItemAdapter extends RecyclerView.Adapter<CheckoutItemAdapter.CheckoutViewHolder> {
    Context context;
    ArrayList<ShoppingCart> cartItems;

    public CheckoutItemAdapter(Context context, ArrayList<ShoppingCart> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CheckoutItemAdapter.CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.checkout_row,parent,false);
        return new CheckoutItemAdapter.CheckoutViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutItemAdapter.CheckoutViewHolder holder, int position) {
        ShoppingCart product = this.cartItems.get(position);
        holder.quantity.setText(product.getProductQuantity());
        holder.totalPrice.setText(product.getTotalPrice());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product item = snapshot.getValue(Product.class);
                Picasso.get().load(item.getProductImage()).into(holder.productImg);
                holder.productName.setText(item.getProductName());
                holder.sellingPrice.setText(String.format("%.2f",item.getProductPrice()));

                if (item.getSellingPrice() < item.getProductPrice()) {
                    holder.normalPrice.setPaintFlags(holder.normalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.normalPrice.setText("RM "+String.format("%.2f", item.getProductPrice()));
                    holder.sellingPrice.setText(String.format("%.2f", item.getSellingPrice()));
                    Float promotion = ((item.getSellingPrice() - item.getProductPrice()) / item.getProductPrice()) * 100;
                    holder.discount.setText(String.format("%.2f", promotion) + " %");

                } else {
                    holder.sellingPrice.setText(String.format("%.2f", item.getProductPrice()));
                    holder.normalPrice.setText("");
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
        if(cartItems == null)
            return 0;
        else
            return cartItems.size();
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg;
        TextView productName, sellingPrice, totalPrice, normalPrice, discount, quantity;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.checkoutProductImg);
            productName = itemView.findViewById(R.id.checkoutProductName);
            sellingPrice = itemView.findViewById(R.id.checkoutProductSellingPrice);
            totalPrice = itemView.findViewById(R.id.checkoutProductTotalPrice);
            quantity = itemView.findViewById(R.id.checkoutProductQuantity);
            normalPrice = itemView.findViewById(R.id.checkoutProductUnitPriceRM);
            discount = itemView.findViewById(R.id.checkoutProductDiscount);
        }
    }
}
