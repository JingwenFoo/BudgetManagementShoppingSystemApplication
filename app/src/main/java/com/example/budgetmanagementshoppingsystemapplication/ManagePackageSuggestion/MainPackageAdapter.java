package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;

import java.util.List;

public class MainPackageAdapter extends RecyclerView.Adapter<MainPackageAdapter.ViewHolder> {
    Context context;
    List<Product> promotionItems;

    public MainPackageAdapter(Context context, List<Product> promotionItems) {
        this.context = context;
        this.promotionItems = promotionItems;
    }

    @NonNull
    @Override
    public MainPackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.on_promotion_row,parent,false);
        return new MainPackageAdapter.ViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPackageAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerView childRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childRecyclerView = itemView.findViewById(R.id.childrecyclerViewPackageItem);
        }
    }
}

