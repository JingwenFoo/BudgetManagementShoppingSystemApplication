package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking.AddToCart;
import com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking.CartAdapter;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.Model.ShoppingCart;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackageSuggestionAdapter extends RecyclerView.Adapter<PackageSuggestionAdapter.ViewHolder> {
    Context context;
    List<Product> packageItems;

    public PackageSuggestionAdapter(Context context, List<Product> packageItems) {
        this.context = context;
        this.packageItems = packageItems;
    }

    @NonNull
    @Override
    public PackageSuggestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.package_suggestion_row,parent,false);
        return new PackageSuggestionAdapter.ViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageSuggestionAdapter.ViewHolder holder, int position) {
        Product product = this.packageItems.get(position);
        ArrayList<List<Product>> packageData = new ArrayList<List<Product>>();
//        System.out.println(packageData);
//        packageData.add(packageItems);
//        packageData.add(packageItems);
//        System.out.println(packageData);

        holder.packageNum.setText("Package 1");

        holder.productItemRecyclerView.setHasFixedSize(true);
        holder.productItemRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        PackageSuggestionItemAdapter adapter = new PackageSuggestionItemAdapter(context, packageItems);
        holder.productItemRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(packageItems == null)
            return 0;
        else
            return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerView productItemRecyclerView;
        TextView packageNum, totalPrice;

        public View mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productItemRecyclerView = itemView.findViewById(R.id.packageItemRecyclerView);
            packageNum = itemView.findViewById(R.id.packageNum);
            totalPrice = itemView.findViewById(R.id.packtotalPrice);
            mainLayout = itemView;
        }
    }
}

