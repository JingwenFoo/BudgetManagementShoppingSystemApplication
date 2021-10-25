package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking.AddToCart;
import com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking.ViewCart;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.preferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class MainPackageAdapter extends RecyclerView.Adapter<MainPackageAdapter.ViewHolder> {
    Context context;
    List<Product> packageItems;
    Boolean productSaved = false;
    public MainPackageAdapter(Context context, List<Product> packageItems) {
        this.context = context;
        this.packageItems = packageItems;
    }

    @NonNull
    @Override
    public MainPackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.display_package_item_section,parent,false);
        return new MainPackageAdapter.ViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPackageAdapter.ViewHolder holder, int position) {

        holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        DisplaySelectedPackageItemAdapter adapter = new DisplaySelectedPackageItemAdapter(context,packageItems);
        holder.childRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        float totalPrice=0;
        for(int i=0; i<packageItems.size(); i++)
            totalPrice+=packageItems.get(i).getSellingPrice();
        holder.totalPrice.setText(String.format("%.2f",totalPrice));
        holder.saveToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                HashMap<String, Object> map = new HashMap<>();
                for(int i=0; i<packageItems.size(); i++)
                {
                    int finalI = i;
                    Float discount = ((packageItems.get(finalI).getSellingPrice() - packageItems.get(finalI).getProductPrice()) / packageItems.get(finalI).getProductPrice()) * 100;

                    ref.child("ShoppingCart").child(preferences.getDataUserID(context)).child(packageItems.get(i).getProductID()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String qty = snapshot.child("productQuantity").getValue(String.class);
                                Integer quantity = Integer.parseInt(qty);
                                Integer quantityUpdate = quantity+1;
                                Float totalPriceUpdate = quantityUpdate*packageItems.get(finalI).getSellingPrice();
                                Integer stockLeft = Integer.parseInt(packageItems.get(finalI).getStockAvailable());
                                Integer stockUpdate = stockLeft-1;
                                ref.child("Product").child(packageItems.get(finalI).getProductID()).child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                ref.child("ShoppingCart").child(preferences.getDataUserID(context)).child(packageItems.get(finalI).getProductID()).child("productQuantity").setValue(String.valueOf(quantityUpdate));
                                ref.child("ShoppingCart").child(preferences.getDataUserID(context)).child(packageItems.get(finalI).getProductID()).child("totalPrice").setValue(String.valueOf(totalPriceUpdate));
                                Intent in = new Intent(context, ViewCart.class);
                                context.startActivity(in);
                            } else {
                                map.put("productID", packageItems.get(finalI).getProductID());
                                map.put("productQuantity", "1");
                                map.put("discount", String.format("%.2f",discount));
                                map.put("category", packageItems.get(finalI).getCategory());
                                map.put("totalPrice", String.format("%.2f",packageItems.get(finalI).getSellingPrice()));
                                Integer stockLeft = Integer.parseInt(packageItems.get(finalI).getStockAvailable());
                                Integer stockUpdate = stockLeft-1;
                                ref.child("Product").child(packageItems.get(finalI).getProductID()).child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                ref.child("ShoppingCart").child(preferences.getDataUserID(context)).child(packageItems.get(finalI).getProductID()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                            productSaved=true;
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(context,"Something is wrong", Toast.LENGTH_LONG).show();

                        }
                    });
                }
                if (productSaved=true)
                {
                    Toast.makeText(context, "Products saved to cart successfully", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(context, ViewCart.class);
                    context.startActivity(in);
                }
                else
                    Toast.makeText(context, "Failed to save products into cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerView childRecyclerView;
        TextView totalPrice;
        Button saveToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childRecyclerView = itemView.findViewById(R.id.childrecyclerViewPackageItem);
            totalPrice = itemView.findViewById(R.id.totalPricePackageSelected);
            saveToCart = itemView.findViewById(R.id.saveToCartBtn);
        }
    }
}

