package com.example.budgetmanagementshoppingsystemapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    ArrayList<ShoppingCart> cartItems;

    public CartAdapter(Context context, ArrayList<ShoppingCart> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewD = inflater.inflate(R.layout.cart_row,parent,false);
        return new CartViewHolder (viewD);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ShoppingCart product = this.cartItems.get(position);
        holder.quantity.setText(product.getProductQuantity());
        holder.totalPrice.setText(product.getTotalPrice());
        holder.productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, CustomerViewProduct.class);
                in.putExtra("prodID", cartItems.get(position).getProductID());
                context.startActivity(in);
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product item = snapshot.getValue(Product.class);
                Picasso.get().load(item.getProductImage()).into(holder.productImg);
                holder.productName.setText(item.getProductName());
                holder.sellingPrice.setText(String.format("%.2f",item.getProductPrice()));

                if (item.getSellingPrice() < item.getProductPrice()) {
                    holder.normalPrice.setPaintFlags(holder.normalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.normalPrice.setText("RM "+String.format("%.2f", item.getProductPrice()));
                    holder.sellingPrice.setText(String.format("%.2f", item.getSellingPrice()));
                    Float promotion = ((item.getSellingPrice() - item.getProductPrice()) / item.getProductPrice()) * 100;
                    holder.discount.setText(String.format("%.2f", promotion) + " %");

                } else {
                    holder.sellingPrice.setText(String.format("%.2f", item.getProductPrice()));
                    holder.normalPrice.setText("");
                    holder.discount.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(Objects.requireNonNull(context)).getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);

                    Float price = Float.parseFloat(holder.sellingPrice.getText().toString());
                    Integer qty = Integer.parseInt(holder.quantity.getText().toString());

                    Float totalOriProductPrice = Float.parseFloat(product.getTotalPrice());
                    Float totalUpdatePrice = price*qty;

                    Float budget = Float.parseFloat(preferences.getDataBudget(context));
                    Float freshBud = Float.parseFloat(preferences.getDataFreshBudget(context));
                    Float groBud = Float.parseFloat(preferences.getDataGroBudget(context));
                    Float bevBud = Float.parseFloat(preferences.getDataBevBudget(context));
                    Float houseBud = Float.parseFloat(preferences.getDataHouseBudget(context));
                    Float PCareBud = Float.parseFloat(preferences.getDataPCareBudget(context));
                    Float clothBud = Float.parseFloat(preferences.getDataClothBudget(context));

                    Float fresh = (freshBud/100)*budget;
                    Float groc = (groBud/100)*budget;
                    Float bev = (bevBud/100)*budget;
                    Float house = (houseBud/100)*budget;
                    Float pCare = (PCareBud/100)*budget;
                    Float cloth = (clothBud/100)*budget;

                    Float freshTotal = Float.parseFloat(preferences.getDataFreshBudgetTotal(context));
                    Float groTotal = Float.parseFloat(preferences.getDataGroBudgetTotal(context));
                    Float bevTotal = Float.parseFloat(preferences.getDataBevBudgetTotal(context));
                    Float houseTotal = Float.parseFloat(preferences.getDataHouseBudgetTotal(context));
                    Float PCareTotal = Float.parseFloat(preferences.getDataPCareBudgetTotal(context));
                    Float clothTotal = Float.parseFloat(preferences.getDataClothBudgetTotal(context));

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ShoppingCart");
                    if(product.getCategory().matches("Fresh"))
                    {
                        if((freshTotal-totalOriProductPrice)+totalUpdatePrice>fresh) {
                            Toast.makeText(context, "Fresh category is over your budget", Toast.LENGTH_SHORT).show();
                            String oriQuantity = product.getProductQuantity();
                            holder.quantity.setText(oriQuantity);
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            holder.totalPrice.setText(String.format("%.2f",oriQ*price));
                        }
                        else {
                            holder.totalPrice.setText(String.format("%.2f",(totalUpdatePrice)));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("productQuantity").setValue(String.valueOf(qty));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("totalPrice").setValue(String.format("%.2f",(totalUpdatePrice)));
                            notifyDataSetChanged();
                            String oriQuantity = product.getProductQuantity();
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String stock = snapshot.child("stockAvailable").getValue(String.class);
                                    Integer stockLeft = Integer.parseInt(stock);
                                    Integer stockUpdate;
                                    if (qty > oriQ) {
                                        stockUpdate = stockLeft - (qty - oriQ);
                                    } else {
                                        stockUpdate = stockLeft + (oriQ - qty);
                                    }
                                    dbRef.child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                        }
                    }
                    else if(product.getCategory().matches("Groceries"))
                    {
                        if((groTotal-totalOriProductPrice)+totalUpdatePrice>groc)
                        {
                            Toast.makeText(context, "Groceries category is over your budget", Toast.LENGTH_SHORT).show();

                            String oriQuantity = product.getProductQuantity();
                            holder.quantity.setText(oriQuantity);
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            holder.totalPrice.setText(String.format("%.2f",oriQ*price));
                        }
                        else {
                            holder.totalPrice.setText(String.format("%.2f",(totalUpdatePrice)));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("productQuantity").setValue(String.valueOf(qty));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("totalPrice").setValue(String.format("%.2f",(totalUpdatePrice)));
                            notifyDataSetChanged();
                            String oriQuantity = product.getProductQuantity();
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String stock = snapshot.child("stockAvailable").getValue(String.class);
                                    Integer stockLeft = Integer.parseInt(stock);
                                    Integer stockUpdate;
                                    if (qty > oriQ) {
                                        stockUpdate = stockLeft - (qty - oriQ);
                                    } else {
                                        stockUpdate = stockLeft + (oriQ - qty);
                                    }
                                    dbRef.child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                    else if(product.getCategory().matches("Beverages"))
                    {
                        if((bevTotal-totalOriProductPrice)+totalUpdatePrice>bev)
                        {
                            Toast.makeText(context, "Beverages category is over your budget", Toast.LENGTH_SHORT).show();

                            String oriQuantity = product.getProductQuantity();
                            holder.quantity.setText(oriQuantity);
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            holder.totalPrice.setText(String.format("%.2f",oriQ*price));
                        }
                        else {
                            holder.totalPrice.setText(String.format("%.2f",(totalUpdatePrice)));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("productQuantity").setValue(String.valueOf(qty));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("totalPrice").setValue(String.format("%.2f",(totalUpdatePrice)));
                            notifyDataSetChanged();
                            String oriQuantity = product.getProductQuantity();
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String stock = snapshot.child("stockAvailable").getValue(String.class);
                                    Integer stockLeft = Integer.parseInt(stock);
                                    Integer stockUpdate;
                                    if (qty > oriQ) {
                                        stockUpdate = stockLeft - (qty - oriQ);
                                    } else {
                                        stockUpdate = stockLeft + (oriQ - qty);
                                    }
                                    dbRef.child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                    else if(product.getCategory().matches("Household"))
                    {
                        if((houseTotal-totalOriProductPrice)+totalUpdatePrice>house)
                        {
                            Toast.makeText(context, "Household category is over your budget", Toast.LENGTH_SHORT).show();

                            String oriQuantity = product.getProductQuantity();
                            holder.quantity.setText(oriQuantity);
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            holder.totalPrice.setText(String.format("%.2f",oriQ*price));
                        }
                        else {
                            holder.totalPrice.setText(String.format("%.2f",(totalUpdatePrice)));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("productQuantity").setValue(String.valueOf(qty));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("totalPrice").setValue(String.format("%.2f",(totalUpdatePrice)));
                            notifyDataSetChanged();
                            String oriQuantity = product.getProductQuantity();
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String stock = snapshot.child("stockAvailable").getValue(String.class);
                                    Integer stockLeft = Integer.parseInt(stock);
                                    Integer stockUpdate;
                                    if (qty > oriQ) {
                                        stockUpdate = stockLeft - (qty - oriQ);
                                    } else {
                                        stockUpdate = stockLeft + (oriQ - qty);
                                    }
                                    dbRef.child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                    else if(product.getCategory().matches("Personal Care"))
                    {
                        if((PCareTotal-totalOriProductPrice)+totalUpdatePrice>pCare)
                        {
                            Toast.makeText(context, "Personal Care category is over your budget", Toast.LENGTH_SHORT).show();

                            String oriQuantity = product.getProductQuantity();
                            holder.quantity.setText(oriQuantity);
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            holder.totalPrice.setText(String.format("%.2f",oriQ*price));
                        }
                        else {
                            holder.totalPrice.setText(String.format("%.2f",(totalUpdatePrice)));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("productQuantity").setValue(String.valueOf(qty));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("totalPrice").setValue(String.format("%.2f",(totalUpdatePrice)));
                            notifyDataSetChanged();
                            String oriQuantity = product.getProductQuantity();
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String stock = snapshot.child("stockAvailable").getValue(String.class);
                                    Integer stockLeft = Integer.parseInt(stock);
                                    Integer stockUpdate;
                                    if (qty > oriQ) {
                                        stockUpdate = stockLeft - (qty - oriQ);
                                    } else {
                                        stockUpdate = stockLeft + (oriQ - qty);
                                    }
                                    dbRef.child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                    else {
                        if((clothTotal-totalOriProductPrice)+totalUpdatePrice>cloth)
                        {
                            Toast.makeText(context, "Clothes category is over your budget", Toast.LENGTH_SHORT).show();

                            String oriQuantity = product.getProductQuantity();
                            holder.quantity.setText(oriQuantity);
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            holder.totalPrice.setText(String.format("%.2f",oriQ*price));
                        }
                        else {
                            holder.totalPrice.setText(String.format("%.2f",(totalUpdatePrice)));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("productQuantity").setValue(String.valueOf(qty));
                            reference.child(preferences.getDataUserID(context)).child(product.getProductID()).child("totalPrice").setValue(String.format("%.2f",(totalUpdatePrice)));
                            notifyDataSetChanged();
                            String oriQuantity = product.getProductQuantity();
                            Integer oriQ = Integer.parseInt(oriQuantity);
                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Product").child(product.getProductID());
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String stock = snapshot.child("stockAvailable").getValue(String.class);
                                    Integer stockLeft = Integer.parseInt(stock);
                                    Integer stockUpdate;
                                    if (qty > oriQ) {
                                        stockUpdate = stockLeft - (qty - oriQ);
                                    } else {
                                        stockUpdate = stockLeft + (oriQ - qty);
                                    }
                                    dbRef.child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                }
            }

        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(product.getProductID(),holder.quantity.getText().toString());
            }
        });

    }

    private void confirmDialog(String pID, String quantity)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Remove from cart?");
        builder.setMessage("Are you sure you want to remove?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer qty = Integer.parseInt(quantity);
                DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("Product").child(pID);
                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String stock = snapshot.child("stockAvailable").getValue(String.class);
                        Integer stockLeft = Integer.parseInt(stock);
                        Integer stockUpdate = stockLeft+qty;
                        mref.child("stockAvailable").setValue(String.valueOf(stockUpdate));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("ShoppingCart");
                ref.child(preferences.getDataUserID(context)).child(pID).removeValue();
                Toast.makeText(context,"Product removed successfully",Toast.LENGTH_SHORT).show();
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
        if(cartItems == null)
            return 0;
        else
            return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView productImg;
        ImageButton delete;
        TextView productName, sellingPrice, totalPrice, normalPrice, discount;
        EditText quantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.cartProductImg);
            delete = itemView.findViewById(R.id.cartDeleteBtn);
            productName = itemView.findViewById(R.id.cartProductName);
            sellingPrice = itemView.findViewById(R.id.cartProductSellingPrice);
            totalPrice = itemView.findViewById(R.id.cartProductTotalPrice);
            quantity = itemView.findViewById(R.id.cartProductQuantity);
            normalPrice = itemView.findViewById(R.id.cartProductUnitPriceRM);
            discount = itemView.findViewById(R.id.cartProductDiscount);
        }
    }
}
