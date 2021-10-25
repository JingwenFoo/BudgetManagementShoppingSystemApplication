package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DisplaySelectedPackageItemAdapter extends RecyclerView.Adapter<DisplaySelectedPackageItemAdapter.ViewHolder> {
    Context context;
    List<Product> selectedPackageItems;

    public DisplaySelectedPackageItemAdapter(Context context, List<Product> selectedPackageItems) {
        this.context = context;
        this.selectedPackageItems = selectedPackageItems;
    }

    @NonNull
    @Override
    public DisplaySelectedPackageItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.display_package_item_row,parent,false);
        return new DisplaySelectedPackageItemAdapter.ViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplaySelectedPackageItemAdapter.ViewHolder holder, int position) {
        Product product = this.selectedPackageItems.get(position);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product item = snapshot.getValue(Product.class);
                Picasso.get().load(item.getProductImage()).into(holder.productImg);
                holder.productName.setText(item.getProductName());
                holder.productBrand.setText(item.getProductBrand());
                holder.sellingPrice.setText(String.format("%.2f",item.getSellingPrice()));
                if(item.getSellingPrice()<item.getProductPrice())
                {
                    holder.normalPrice.setPaintFlags(holder.normalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.normalPrice.setText("RM "+String.format("%.2f",item.getProductPrice()));
                    holder.discount.setText(String.format("%.2f",((item.getSellingPrice()-item.getProductPrice())/item.getProductPrice())*100)+"%");
                }
                else
                {
                    holder.sellingPriceRM.setTextColor(Color.BLACK);
                    holder.sellingPrice.setTextColor(Color.BLACK);
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
        if(selectedPackageItems == null)
            return 0;
        else
            return selectedPackageItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg;
        TextView productName, productBrand, sellingPrice, sellingPriceRM, normalPrice, discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.PackageProductImg);
            productName = itemView.findViewById(R.id.PackageProductName);
            productBrand = itemView.findViewById(R.id.PackageProductBrand);
            sellingPrice = itemView.findViewById(R.id.PackageProductSellingPrice);
            sellingPriceRM = itemView.findViewById(R.id.PackageProductSellingPriceRM);
            normalPrice = itemView.findViewById(R.id.PackageProductUnitPriceRM);
            discount = itemView.findViewById(R.id.PackageProductDiscount);

        }
    }
}


