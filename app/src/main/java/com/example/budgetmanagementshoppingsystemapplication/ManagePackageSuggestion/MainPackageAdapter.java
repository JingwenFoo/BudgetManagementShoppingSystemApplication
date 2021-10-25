package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;

import java.util.List;

public class MainPackageAdapter extends RecyclerView.Adapter<MainPackageAdapter.ViewHolder> {
    Context context;
    List<Product> packageItems;

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

