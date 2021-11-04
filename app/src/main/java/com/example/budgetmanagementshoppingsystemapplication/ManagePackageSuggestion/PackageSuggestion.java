package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.Model.SuggestPackage;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PackageSuggestion extends AppCompatActivity {
    RecyclerView recyclerView;
    PackageSuggestionAdapter adapter;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_suggestion);

        recyclerView = findViewById(R.id.packagelistRecycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference().child("SuggestPackage").child(preferences.getDataUserID(this));

        List<SuggestPackage> packageList = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    String PackageID = dataSnapshot.child("packageID").getValue(String.class);
                    String totalPackagePrice = dataSnapshot.child("totalPackagePrice").getValue(String.class);
                    packageList.add(new SuggestPackage(PackageID,totalPackagePrice));
                }
                adapter = new PackageSuggestionAdapter(PackageSuggestion.this, packageList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}