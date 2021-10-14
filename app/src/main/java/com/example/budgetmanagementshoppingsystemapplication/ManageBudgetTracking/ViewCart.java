package com.example.budgetmanagementshoppingsystemapplication.ManageBudgetTracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.ManagePayment.CustomerCheckout;
import com.example.budgetmanagementshoppingsystemapplication.ManageProduct.CaptureAct;
import com.example.budgetmanagementshoppingsystemapplication.Model.ShoppingCart;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.preferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;


public class ViewCart extends AppCompatActivity {

    TextView tv_budget, tv_ttpricecart;
    Button editBudBtn, CheckoutBtn;
    ImageButton addMoreBtn;
    DatabaseReference ref;
    RecyclerView mainRecyclerView, cartRecyclerView;
    List<Section> sectionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        tv_budget = findViewById(R.id.budgetTextView);
        tv_ttpricecart = findViewById(R.id.totalPriceCart);
        editBudBtn = findViewById(R.id.editBudgetBtn);
        CheckoutBtn = findViewById(R.id.checkOutBtn);
        addMoreBtn = findViewById(R.id.addMoreItemBtn);
        cartRecyclerView = findViewById(R.id.categoryRecyclerView);

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("ShoppingCart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float totalPrice = 0;
                for (DataSnapshot snapshot1 : snapshot.child(preferences.getDataUserID(ViewCart.this)).getChildren()) {
                    ShoppingCart product = snapshot1.getValue(ShoppingCart.class);
                    float total = Float.valueOf(product.getTotalPrice());
                    totalPrice += total;

                }
                preferences.setDataTotalCart(ViewCart.this,String.format("%.2f",totalPrice));
                tv_ttpricecart.setText(String.format("%.2f",totalPrice));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        Float budget = Float.parseFloat(preferences.getDataBudget(ViewCart.this));
        Float totalCartPrice = Float.parseFloat(preferences.getDataTotalCart(ViewCart.this));

        if(totalCartPrice>budget)
        {
            addMoreBtn.setEnabled(false);
            Toast.makeText(ViewCart.this,"Over budget! Increase budget to add more items",Toast.LENGTH_SHORT).show();
        }

        addMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        Float budgetValue = Float.parseFloat(preferences.getDataBudget(this));
        Float freshBud = Float.parseFloat(preferences.getDataFreshBudget(this));
        Float groBud = Float.parseFloat(preferences.getDataGroBudget(this));
        Float bevBud = Float.parseFloat(preferences.getDataBevBudget(this));
        Float houseBud = Float.parseFloat(preferences.getDataHouseBudget(this));
        Float PCareBud = Float.parseFloat(preferences.getDataPCareBudget(this));
        Float clothBud = Float.parseFloat(preferences.getDataClothBudget(this));


        String sectionOne = "Fresh";
        String budgetOne = String.format("%.2f",(freshBud/100)*budgetValue);
        sectionList.add(new Section(sectionOne, budgetOne));
        String sectionTwo = "Groceries";
        String budgetTwo = String.format("%.2f",(groBud/100)*budgetValue);
        sectionList.add(new Section(sectionTwo, budgetTwo));
        String sectionThree = "Beverages";
        String budgetThree = String.format("%.2f",(bevBud/100)*budgetValue);
        sectionList.add(new Section(sectionThree, budgetThree));
        String sectionFour = "Household";
        String budgetFour = String.format("%.2f",(houseBud/100)*budgetValue);
        sectionList.add(new Section(sectionFour, budgetFour));
        String sectionFive = "Personal Care";
        String budgetFive = String.format("%.2f",(PCareBud/100)*budgetValue);
        sectionList.add(new Section(sectionFive, budgetFive));
        String sectionSix = "Clothes";
        String budgetSix = String.format("%.2f",(clothBud/100)*budgetValue);
        sectionList.add(new Section(sectionSix, budgetSix));

        mainRecyclerView = findViewById(R.id.cartRecyclerView);
        MainRecylcerAdapter mainRecylcerAdapter = new MainRecylcerAdapter(ViewCart.this, sectionList);
        mainRecyclerView.setAdapter(mainRecylcerAdapter);
        mainRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        tv_budget.setText(String.valueOf(preferences.getDataBudget(this)));
        editBudBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(ViewCart.this, Budget.class);
                startActivity(edit);
            }
        });

        CheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkout = new Intent(ViewCart.this, CustomerCheckout.class);
                startActivity(checkout);
            }
        });

    }

    private void scanCode()
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning Code");
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents() != null)
            {
                String productCode = result.getContents();
                Intent in = new Intent(ViewCart.this, AddToCart.class);
                in.putExtra("proid", productCode);
                in.putExtra("totalPriceCart", tv_ttpricecart.getText().toString());
                startActivity(in);
            }
            else
            {
                Toast.makeText(ViewCart.this, "No result",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        tv_budget.setText(String.valueOf(preferences.getDataBudget(this)));

    }

}