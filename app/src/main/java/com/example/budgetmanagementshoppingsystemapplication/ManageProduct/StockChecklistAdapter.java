package com.example.budgetmanagementshoppingsystemapplication.ManageProduct;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.ManagePackageSuggestion.OnPromotionAdapter;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class StockChecklistAdapter extends RecyclerView.Adapter<StockChecklistAdapter.ViewHolder> {
Context context;
List<Product> stocklist;

    public StockChecklistAdapter(Context context, List<Product> stocklist) {
        this.context = context;
        this.stocklist = stocklist;
    }

    @NonNull
    @Override
    public StockChecklistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.stock_checklist_row,parent,false);
        return new StockChecklistAdapter.ViewHolder(viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull StockChecklistAdapter.ViewHolder holder, int position) {
        Product product = this.stocklist.get(position);
        Picasso.get().load(product.getProductImage()).into(holder.productImg);
        holder.productName.setText(product.getProductName());
        holder.productBrand.setText(product.getProductBrand());
        int stock = Integer.parseInt(product.getStockAvailable());
        if(stock<=0)
            holder.stock.setText("Out of Stock");
        else
            holder.stock.setText(product.getStockAvailable());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, EditForm.class);
                in.putExtra("editPID",product.getProductID());
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stocklist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg;
        TextView productName, productBrand, stock;

        public View mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.StockProductImg);
            productName = itemView.findViewById(R.id.StockProductName);
            productBrand = itemView.findViewById(R.id.StockProductBrand);
            stock = itemView.findViewById(R.id.stockLeft);
            mainLayout = itemView;
        }
    }
}