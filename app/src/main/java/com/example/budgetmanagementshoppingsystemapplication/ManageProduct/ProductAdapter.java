package com.example.budgetmanagementshoppingsystemapplication.ManageProduct;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<Product> productList;
    Context context;

    public ProductAdapter(ArrayList<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_list_row,parent,false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = this.productList.get(position);
        holder.no.setText(String.valueOf(position+1));
        holder.productName.setText(product.getProductName());
        holder.category.setText(product.getCategory());
        holder.viewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent(context, ViewForm.class);
                view.putExtra("pid",productList.get(position).getProductID());
                context.startActivity(view);
            }
        });
        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(productList.get(position).getProductID());
            }
        });
    }

    private void confirmDialog(String pid)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Product?");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product");
                ref.child(pid).removeValue();
                Toast.makeText(context,"Product deleted successfully",Toast.LENGTH_SHORT).show();
                Intent refresh = new Intent(context, ViewList.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(refresh);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView no, productName, category;
        Button viewProduct, deleteProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.noPLR);
            productName = itemView.findViewById(R.id.productNamePLR);
            category = itemView.findViewById(R.id.categoryPLR);
            viewProduct = itemView.findViewById(R.id.PLViewBtnPLR);
            deleteProduct = itemView.findViewById(R.id.PLDeleteBtnPLR);

        }

    }
}
