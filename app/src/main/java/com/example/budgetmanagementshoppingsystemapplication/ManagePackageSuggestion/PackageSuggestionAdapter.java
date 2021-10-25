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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class PackageSuggestionAdapter extends RecyclerView.Adapter<PackageSuggestionAdapter.ViewHolder> {
    Context context;
    ArrayList<List<Product>> packageItems;

    public PackageSuggestionAdapter(Context context, ArrayList<List<Product>> packageItems) {
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
        holder.packageNum.setText("Package "+(position+1));
        ListIterator<Product> iterator = this.packageItems.get(position).listIterator(0);
        List<Product> packageItem = new ArrayList<>();
        while (iterator.hasNext())
        {
            packageItem.add(iterator.next());
        }
        holder.productItemRecyclerView.setHasFixedSize(true);
        holder.productItemRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        PackageSuggestionItemAdapter adapter = new PackageSuggestionItemAdapter(context,packageItem);
        holder.productItemRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        System.out.println(packageItem);
        float totalPrice=0;
        for(int i=0; i<packageItem.size(); i++)
            totalPrice+=packageItem.get(i).getSellingPrice();

        holder.totalPrice.setText(String.format("%.2f",totalPrice));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisplayPackageItem.class);
                intent.putExtra("packageItem", (Serializable) packageItems.get(position));
                intent.putExtra("packageNum", holder.packageNum.getText());
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

