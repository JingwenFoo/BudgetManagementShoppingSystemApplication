package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking.AddToCart;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OnPromotionAdapter extends RecyclerView.Adapter<OnPromotionAdapter.ViewHolder> {
    Context context;
    List<Product> promotionItems;

    public OnPromotionAdapter(Context context, List<Product> promotionItems) {
        this.context = context;
        this.promotionItems = promotionItems;
    }

    @NonNull
    @Override
    public OnPromotionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.on_promotion_row,parent,false);
        return new OnPromotionAdapter.ViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull OnPromotionAdapter.ViewHolder holder, int position) {
        Product product = this.promotionItems.get(position);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product item = snapshot.getValue(Product.class);
                Picasso.get().load(item.getProductImage()).into(holder.productImg);
                holder.productName.setText(item.getProductName());
                holder.sellingPrice.setText(String.format("%.2f",item.getSellingPrice()));
                holder.productBrand.setText(item.getProductBrand());
                holder.normalPrice.setPaintFlags(holder.normalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.normalPrice.setText("RM "+String.format("%.2f",item.getProductPrice()));
                holder.discount.setText(String.format("%.2f",((item.getSellingPrice()-item.getProductPrice())/item.getProductPrice())*100)+"%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, AddToCart.class);
                in.putExtra("proid", product.getProductID());
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(promotionItems == null)
            return 0;
        else
            return promotionItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg;
        TextView productName, productBrand, sellingPrice, normalPrice, discount;

        public View mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.PromoteProductImg);
            productName = itemView.findViewById(R.id.PromoteProductName);
            productBrand = itemView.findViewById(R.id.PromoteProductBrand);
            sellingPrice = itemView.findViewById(R.id.PromoteProductSellingPrice);
            normalPrice = itemView.findViewById(R.id.PromoteProductUnitPriceRM);
            discount = itemView.findViewById(R.id.PromoteProductDiscount);
            mainLayout = itemView;
        }
    }
}

