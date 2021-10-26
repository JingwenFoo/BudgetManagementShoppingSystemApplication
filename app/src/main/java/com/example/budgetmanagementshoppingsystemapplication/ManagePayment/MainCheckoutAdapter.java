package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetmanagementshoppingsystemapplication.Model.ShoppingCart;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainCheckoutAdapter extends RecyclerView.Adapter<MainCheckoutAdapter.ViewHolder> {
    Context context;


    public MainCheckoutAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public MainCheckoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.checkout_section, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCheckoutAdapter.ViewHolder holder, int position) {
        holder.childRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("ShoppingCart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ShoppingCart> cartData = new ArrayList<>();
                float  totalPrice = 0;
                for (DataSnapshot snapshot1 : snapshot.child(preferences.getDataUserID(context)).getChildren()) {
                    ShoppingCart product = snapshot1.getValue(ShoppingCart.class);
                    totalPrice += Float.parseFloat(product.getTotalPrice());
                    cartData.add(product);
                }

                CheckoutItemAdapter cartAdapter = new CheckoutItemAdapter(context, cartData);
                holder.childRecyclerView.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
                holder.totalPrice.setText(String.format("%.2f",totalPrice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected_id = holder.radioGroup.getCheckedRadioButtonId();
                if (selected_id == R.id.radioDebitCard)
                {
                    Intent cardPayment = new Intent(context,CardPayment.class);
                    cardPayment.putExtra("totalAmount",holder.totalPrice.getText());
                    context.startActivity(cardPayment);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Pay Cash at Counter?");
                    builder.setMessage("Are you sure you want to pay cash at counter?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent PayByCash = new Intent(context, PayByCash.class);
                            PayByCash.putExtra("totalAmount",holder.totalPrice.getText());
                            context.startActivity(PayByCash);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
            return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerView childRecyclerView;
        TextView totalPrice;
        Button payBtn;
        RadioGroup radioGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childRecyclerView = itemView.findViewById(R.id.recyclerViewCheckoutSection);
            totalPrice = itemView.findViewById(R.id.totalPriceCheckout);
            payBtn = itemView.findViewById(R.id.payBtn);
            radioGroup = itemView.findViewById(R.id.paymentTypeRadio);


        }
    }
}
