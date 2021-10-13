package com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.ShoppingCart;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainRecylcerAdapter extends RecyclerView.Adapter<MainRecylcerAdapter.ViewHolder> {
    Context context;
    List<Section> sectionList;

    public MainRecylcerAdapter(Context context, List<Section> sectionList) {
        this.context = context;
        this.sectionList = sectionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.section_row, parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Section section = sectionList.get(position);
        String category = section.getCategory();
        holder.category.setText(category);
        holder.categoryBudget.setText(section.getCategoryBudget());

        if(category.matches("Fresh"))
        {
            holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            ref.child("ShoppingCart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ShoppingCart> cartData = new ArrayList<>();
                float freshBud = 0;
                for(DataSnapshot snapshot1 : snapshot.child(preferences.getDataUserID(context)).getChildren()){
                    ShoppingCart product = snapshot1.getValue(ShoppingCart.class);
                    if(product.getCategory()!=null && product.getCategory().matches("Fresh")){
                        float total = Float.valueOf(product.getTotalPrice());
                        freshBud += total;
                        cartData.add(product);
                    }
                }

                preferences.setDataFreshBudgetTotal(context,String.format("%.2f",freshBud));

                CartAdapter cartAdapter = new CartAdapter(context, cartData);
                holder.childRecyclerView.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
        });
        }
        if(category.matches("Groceries"))
        {

            holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            ref.child("ShoppingCart").child(preferences.getDataUserID(context)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ShoppingCart> cartData = new ArrayList<>();
                    float groBud = 0;
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){

                        ShoppingCart product = snapshot1.getValue(ShoppingCart.class);
                        if(product.getCategory()!=null && product.getCategory().matches("Groceries")){
                            float total = Float.valueOf(product.getTotalPrice());
                            groBud += total;
                            cartData.add(product);
                        }
                    }
                    preferences.setDataGroBudgetTotal(context,String.format("%.2f",groBud));

                    CartAdapter cartAdapter = new CartAdapter(context, cartData);
                    holder.childRecyclerView.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }

        if(category.matches("Beverages"))
        {

            holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            ref.child("ShoppingCart").child(preferences.getDataUserID(context)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ShoppingCart> cartData = new ArrayList<>();
                    float bevBud = 0;
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){

                        ShoppingCart product = snapshot1.getValue(ShoppingCart.class);
                        if(product.getCategory()!=null && product.getCategory().matches("Beverages")){
                            float total = Float.valueOf(product.getTotalPrice());
                            bevBud += total;
                            cartData.add(product);
                        }
                    }
                    preferences.setDataBevBudgetTotal(context,String.format("%.2f",bevBud));

                    CartAdapter cartAdapter = new CartAdapter(context, cartData);
                    holder.childRecyclerView.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
        if(category.matches("Household"))
        {

            holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            ref.child("ShoppingCart").child(preferences.getDataUserID(context)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ShoppingCart> cartData = new ArrayList<>();
                    float houseBud = 0;
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){

                        ShoppingCart product = snapshot1.getValue(ShoppingCart.class);
                        if(product.getCategory()!=null && product.getCategory().matches("Household")){
                            float total = Float.valueOf(product.getTotalPrice());
                            houseBud += total;
                            cartData.add(product);
                        }
                    }
                    preferences.setDataHouseBudgetTotal(context,String.format("%.2f",houseBud));

                    CartAdapter cartAdapter = new CartAdapter(context, cartData);
                    holder.childRecyclerView.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
        if(category.matches("Personal Care"))
        {

            holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            ref.child("ShoppingCart").child(preferences.getDataUserID(context)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ShoppingCart> cartData = new ArrayList<>();
                    float pCareBud = 0;
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){

                        ShoppingCart product = snapshot1.getValue(ShoppingCart.class);
                        if(product.getCategory()!=null && product.getCategory().matches("Personal Care")){
                            float total = Float.valueOf(product.getTotalPrice());
                            pCareBud += total;
                            cartData.add(product);
                        }
                    }
                    preferences.setDataPCareBudgetTotal(context,String.format("%.2f",pCareBud));

                    CartAdapter cartAdapter = new CartAdapter(context, cartData);
                    holder.childRecyclerView.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
        if(category.matches("Clothes"))
        {

            holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

            ref.child("ShoppingCart").child(preferences.getDataUserID(context)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<ShoppingCart> cartData = new ArrayList<>();
                    float clothBud = 0;
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){

                        ShoppingCart product = snapshot1.getValue(ShoppingCart.class);
                        if(product.getCategory()!=null && product.getCategory().matches("Clothes")){
                            float total = Float.valueOf(product.getTotalPrice());
                            clothBud += total;
                            cartData.add(product);
                        }
                    }
                    preferences.setDataClothBudgetTotal(context,String.format("%.2f",clothBud));

                    CartAdapter cartAdapter = new CartAdapter(context, cartData);
                    holder.childRecyclerView.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView category, categoryBudget;
        RecyclerView childRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            categoryBudget = itemView.findViewById(R.id.categoryBudget);
            childRecyclerView = itemView.findViewById(R.id.categoryRecyclerView);
        }
    }
}
