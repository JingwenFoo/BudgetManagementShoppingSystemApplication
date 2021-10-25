package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PackageSuggestionItemAdapter extends RecyclerView.Adapter<PackageSuggestionItemAdapter.ViewHolder> {
    Context context;
    List<Product> packageItems;
    String packageNumber;
    List<Product> packageListItem;

    public PackageSuggestionItemAdapter(Context context, List<Product> packageItems, String packageNumber, List<Product> packageListItem) {
        this.context = context;
        this.packageItems = packageItems;
        this.packageNumber = packageNumber;
        this.packageListItem = packageListItem;
    }

    @NonNull
    @Override
    public PackageSuggestionItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.package_suggestion_item_row,parent,false);
        return new PackageSuggestionItemAdapter.ViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageSuggestionItemAdapter.ViewHolder holder, int position) {
        holder.productName.setText(packageItems.get(position).getProductName());
        holder.sellingPrice.setText(String.format("%.2f",packageItems.get(position).getSellingPrice()));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisplayPackageItem.class);
                intent.putExtra("packageItem", (Serializable) packageListItem);
                intent.putExtra("packageNum", packageNumber);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(packageItems == null)
            return 0;
        else
            return packageItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView productName, sellingPrice;

        public View mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.packageproName);
            sellingPrice = itemView.findViewById(R.id.packageproPrice);
            mainLayout = itemView;
        }
    }
}

