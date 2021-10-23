package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.budgetmanagementshoppingsystemapplication.Model.Customer;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectCategory extends AppCompatActivity {
RecyclerView recyclerViewCategoryDetail;
Button suggestBtn;
EditText search;
DatabaseReference ref;
List<Product> categoryItemList = new ArrayList<>();
CategoryItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        search = findViewById(R.id.searchCategoryEditText);
        recyclerViewCategoryDetail = findViewById(R.id.categoryitemRecycleView);
        suggestBtn = findViewById(R.id.suggestBtn);
        ref = FirebaseDatabase.getInstance().getReference();

        recyclerViewCategoryDetail.setHasFixedSize(true);
        recyclerViewCategoryDetail.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new CategoryItemAdapter(this, categoryItemList);
        recyclerViewCategoryDetail.setAdapter(adapter);

        ref.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Product categoryItem = dataSnapshot.getValue(Product.class);
                    if (!categoryItemList.equals(categoryItem.getCategoryDetail()))
                    {
                        categoryItemList.add(new Product(categoryItem.getCategoryDetail()));
                    }
                }
                Collections.sort(categoryItemList, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return o1.getCategoryDetail().compareTo(o2.getCategoryDetail());
                    }
                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(search.getText()!=null)
                    searchCategory(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    
    private void searchCategory(String search)
    {
        Query query = FirebaseDatabase.getInstance().getReference().child("Product").orderByChild("categoryDetail")
                .startAt(search)
                .endAt(search+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryItemList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product categoryD = dataSnapshot.getValue(Product.class);
                    categoryItemList.add(categoryD);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}