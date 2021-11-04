package com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.Model.SuggestPackage;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PackageSuggestionItemAdapter extends RecyclerView.Adapter<PackageSuggestionItemAdapter.ViewHolder> {
    Context context;
    List<SuggestPackage> packageItemsID;
    String packageNumber, packageID;
   // List<Product> packageListItem;

    public PackageSuggestionItemAdapter(Context context, List<SuggestPackage> packageItemsID, String packageID, String packageNumber) {
        this.context = context;
        this.packageItemsID = packageItemsID;
        this.packageID = packageID;
        this.packageNumber = packageNumber;
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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product.getProductID().matches(packageItemsID.get(position).getProductID()))
                    {
                        holder.productName.setText(product.getProductName());
                        holder.sellingPrice.setText(String.format("%.2f",product.getSellingPrice()));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisplayPackageItem.class);
           //     intent.putExtra("packageItem", (Serializable) packageItemsID);
                intent.putExtra("packageID",packageID);
                intent.putExtra("packageNum", packageNumber);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(packageItemsID == null)
            return 0;
        else
            return packageItemsID.size();
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

