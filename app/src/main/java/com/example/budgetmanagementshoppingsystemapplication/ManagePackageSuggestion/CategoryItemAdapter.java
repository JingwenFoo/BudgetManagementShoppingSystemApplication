package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.ViewHolder> {
    Context context;
    List<Product> categoryItems;
    ArrayList<Product> categorySelected;

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
        categorySelected = new ArrayList<>();
        holder.categoryItemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product");
                    ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1:snapshot.getChildren())
                        {
                            Product product = snapshot1.getValue(Product.class);
                            if(product.getCategoryDetail() != null && product.getCategoryDetail().matches(categoryItem.getCategoryDetail()))
                                categorySelected.add(product);
                        }
                        Collections.sort(categorySelected, new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                return o1.getCategoryDetail().compareTo(o2.getCategoryDetail());
                            }
                        });

                    }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else
                {
                    Predicate<Product> uncheck = categoryDetail -> categoryDetail.getCategoryDetail().matches(categoryItem.getCategoryDetail());
                    categorySelected.removeIf(uncheck);
                }
            }
        });

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

    public  ArrayList<Product> getCategorySelectedArrayList()
    {
        return categorySelected;
    }
}
