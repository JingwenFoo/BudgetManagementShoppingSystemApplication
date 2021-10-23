package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;

import java.util.List;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {
    Context context;
    List<Product> categoryItems;

    public CategoryItemAdapter(Context context, List<Product> categoryItems) {
        this.context = context;
        this.categoryItems = categoryItems;
    }

    @NonNull
    @Override
    public CategoryItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.select_category_row,parent,false);
        return new CategoryItemAdapter.ViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemAdapter.ViewHolder holder, int position) {
        Product categoryItem = this.categoryItems.get(position);
        holder.categoryItemCheckbox.setText(categoryItem.getCategoryDetail());
    }

    @Override
    public int getItemCount() {
        if(categoryItems == null)
            return 0;
        else
            return categoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox categoryItemCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryItemCheckbox = itemView.findViewById(R.id.categoryDetailCheckbox);
        }
    }
}
