package com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.CustomerHomepage;
import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.MainActivity;
import com.example.budgetmanagementshoppingsystemapplication.Model.Product;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AddToCart extends AppCompatActivity {

    ImageView pro_Img;
    TextView userID, sellingPrice, normalPrice, discount, proName, proBrand, proDesc, quantity, stock, overBudget;
    ImageButton closeBtn, minusBtn, addBtn;
    Button addtoCartBtn;
    DatabaseReference ref;
    Uri imageUriView;
    Float promotion;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        userID = findViewById(R.id.uid);
        pro_Img = findViewById(R.id.imgProduct);
        sellingPrice = findViewById(R.id.disSellingPrice);
        normalPrice = findViewById(R.id.disNormalPrice);
        discount = findViewById(R.id.discount);
        proName = findViewById(R.id.disName);
        proBrand = findViewById(R.id.disBrand);
        proDesc = findViewById(R.id.disDescp);
        quantity = findViewById(R.id.quantity);
        stock = findViewById(R.id.stockView);
        closeBtn = findViewById(R.id.cancelBtn);
        minusBtn = findViewById(R.id.minusQuantityBtn);
        addBtn = findViewById(R.id.addQuantityBtn);
        addtoCartBtn = findViewById(R.id.addToCartBtn);
        overBudget = findViewById(R.id.overBudget);

        Intent intent = getIntent();
        String productID = "" + intent.getStringExtra("proid");

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Product").child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product productDetail = snapshot.getValue(Product.class);
                proName.setText(String.valueOf(productDetail.getProductName()));
                proBrand.setText(String.valueOf(productDetail.getProductBrand()));
                stock.setText(String.valueOf(productDetail.getStockAvailable()));
                proDesc.setText(String.valueOf(productDetail.getProductDescription()));
                category = productDetail.getCategory();
                Picasso.get().load(String.valueOf(productDetail.getProductImage())).into(pro_Img);

                if (productDetail.getSellingPrice() < productDetail.getProductPrice()) {
                    normalPrice.setPaintFlags(normalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    normalPrice.setText("RM "+String.format("%.2f", productDetail.getProductPrice()));
                    sellingPrice.setText(String.format("%.2f", productDetail.getSellingPrice()));
                    promotion = ((productDetail.getSellingPrice() - productDetail.getProductPrice()) / productDetail.getProductPrice()) * 100;
                    discount.setText(String.format("%.2f", promotion) + " %");

                } else {
                    sellingPrice.setText(String.format("%.2f", productDetail.getProductPrice()));
                    normalPrice.setText("");
                    discount.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ActivityResultLauncher<Intent> pickImageResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUriView = data.getData();
                            pro_Img.setImageURI(imageUriView);
                        }
                    }
                });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent close = new Intent(AddToCart.this, ViewCart.class);
                startActivity(close);
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer qty = Integer.parseInt(quantity.getText().toString());
                quantity.setText(String.valueOf(qty + 1));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer qty = Integer.parseInt(quantity.getText().toString());
                if (qty <= 1)
                    quantity.setText("1");
                else
                    quantity.setText(String.valueOf(qty - 1));
            }
        });

        addtoCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float price = Float.parseFloat(sellingPrice.getText().toString());
                Integer qty = Integer.parseInt(quantity.getText().toString());
                Float totalPrice = price * qty;

                Float budget = Float.parseFloat(preferences.getDataBudget(AddToCart.this));
                Float freshBud = Float.parseFloat(preferences.getDataFreshBudget(AddToCart.this));
                Float groBud = Float.parseFloat(preferences.getDataGroBudget(AddToCart.this));
                Float bevBud = Float.parseFloat(preferences.getDataBevBudget(AddToCart.this));
                Float houseBud = Float.parseFloat(preferences.getDataHouseBudget(AddToCart.this));
                Float PCareBud = Float.parseFloat(preferences.getDataPCareBudget(AddToCart.this));
                Float clothBud = Float.parseFloat(preferences.getDataClothBudget(AddToCart.this));

                Float fresh = (freshBud/100)*budget;
                Float groc = (groBud/100)*budget;
                Float bev = (bevBud/100)*budget;
                Float house = (houseBud/100)*budget;
                Float pCare = (PCareBud/100)*budget;
                Float cloth = (clothBud/100)*budget;

                Float freshTotal = Float.parseFloat(preferences.getDataFreshBudgetTotal(AddToCart.this));
                Float groTotal = Float.parseFloat(preferences.getDataGroBudgetTotal(AddToCart.this));
                Float bevTotal = Float.parseFloat(preferences.getDataBevBudgetTotal(AddToCart.this));
                Float houseTotal = Float.parseFloat(preferences.getDataHouseBudgetTotal(AddToCart.this));
                Float PCareTotal = Float.parseFloat(preferences.getDataPCareBudgetTotal(AddToCart.this));
                Float clothTotal = Float.parseFloat(preferences.getDataClothBudgetTotal(AddToCart.this));

                ref.child("Product").child(productID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String category = snapshot.child("category").getValue(String.class);
                        if(category.matches("Fresh"))
                        {
                            if(freshTotal+totalPrice>fresh)
                            {
                                Toast.makeText(AddToCart.this, "Fresh category is over your budget", Toast.LENGTH_SHORT).show();
                                overBudget.setVisibility(View.VISIBLE);
                            }
                            else {
                                overBudget.setVisibility(View.INVISIBLE);
                                addToCart(productID);
                            }
                        }
                        else if(category.matches("Groceries"))
                        {
                            if(groTotal+totalPrice>groc)
                            {
                                Toast.makeText(AddToCart.this, "Groceries category is over your budget", Toast.LENGTH_SHORT).show();
                                overBudget.setVisibility(View.VISIBLE);
                            }
                            else {
                                overBudget.setVisibility(View.INVISIBLE);
                                addToCart(productID);
                            }
                        }
                        else if(category.matches("Beverages"))
                        {
                            if(bevTotal+totalPrice>bev)
                            {
                                Toast.makeText(AddToCart.this, "Beverages category is over your budget", Toast.LENGTH_SHORT).show();
                                overBudget.setVisibility(View.VISIBLE);
                            }
                            else {
                                overBudget.setVisibility(View.INVISIBLE);
                                addToCart(productID);
                            }
                        }
                        else if(category.matches("Household"))
                        {
                            if(houseTotal+totalPrice>house)
                            {
                                Toast.makeText(AddToCart.this, "Household category is over your budget", Toast.LENGTH_SHORT).show();
                                overBudget.setVisibility(View.VISIBLE);
                            }
                            else {
                                overBudget.setVisibility(View.INVISIBLE);
                                addToCart(productID);
                            }
                        }
                        else if(category.matches("Personal Care"))
                        {
                            if(PCareTotal+totalPrice>pCare)
                            {
                                Toast.makeText(AddToCart.this, "Personal Care category is over your budget", Toast.LENGTH_SHORT).show();
                                overBudget.setVisibility(View.VISIBLE);
                            }
                            else {
                                overBudget.setVisibility(View.INVISIBLE);
                                addToCart(productID);
                            }
                        }
                        else
                        {
                            if(clothTotal+totalPrice>cloth)
                            {
                                Toast.makeText(AddToCart.this, "Clothes category is over your budget", Toast.LENGTH_SHORT).show();
                                overBudget.setVisibility(View.VISIBLE);
                            }
                            else {
                                overBudget.setVisibility(View.INVISIBLE);
                                addToCart(productID);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        }

        private void addToCart(String productID)
        {
            Float price = Float.parseFloat(sellingPrice.getText().toString());
            Integer qty = Integer.parseInt(quantity.getText().toString());
            Float totalPrice = price * qty;

            HashMap<String, Object> map = new HashMap<>();
            map.put("productID", productID);
            map.put("productQuantity", quantity.getText().toString());
            map.put("discount", String.valueOf(promotion));
            map.put("category", category);
            map.put("totalPrice", String.format("%.2f", totalPrice));
            if (Integer.parseInt(stock.getText().toString())>0)
            {
                if ((Integer.parseInt(stock.getText().toString())-qty)>0)
                {
                    ref.child("ShoppingCart").child(preferences.getDataUserID(AddToCart.this)).child(productID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String i = snapshot.child("productQuantity").getValue(String.class);
                                Integer quantity = Integer.parseInt(i);
                                Integer quantityUpdate = quantity + qty;
                                Float totalPriceUpdate = quantityUpdate*price;
                                Integer stockLeft = Integer.parseInt(stock.getText().toString());
                                Integer stockUpdate = stockLeft-qty;
                                ref.child("Product").child(productID).child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                ref.child("ShoppingCart").child(preferences.getDataUserID(AddToCart.this)).child(productID).child("productQuantity").setValue(String.valueOf(quantityUpdate));
                                ref.child("ShoppingCart").child(preferences.getDataUserID(AddToCart.this)).child(productID).child("totalPrice").setValue(String.valueOf(totalPriceUpdate));
                                Intent in = new Intent(AddToCart.this, ViewCart.class);
                                startActivity(in);
                                finish();

                            } else {
                                Integer stockLeft = Integer.parseInt(stock.getText().toString());
                                Integer stockUpdate = stockLeft-qty;
                                ref.child("Product").child(productID).child("stockAvailable").setValue(String.valueOf(stockUpdate));
                                ref.child("ShoppingCart").child(preferences.getDataUserID(AddToCart.this)).child(productID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddToCart.this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();
                                            Intent in = new Intent(AddToCart.this, ViewCart.class);
                                            startActivity(in);
                                            finish();
                                        } else {
                                            Toast.makeText(AddToCart.this, "Failed add product to cart", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddToCart.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddToCart.this,"Something is wrong", Toast.LENGTH_LONG).show();

                        }
                    });
                }
                else
                    Toast.makeText(AddToCart.this,"Stock insufficient", Toast.LENGTH_LONG).show();

            }
            else
                Toast.makeText(AddToCart.this,"Product out of stock", Toast.LENGTH_LONG).show();



        }

    }
