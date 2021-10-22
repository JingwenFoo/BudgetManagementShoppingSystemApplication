package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.preferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class PayByCash extends AppCompatActivity {
ImageView qrcode;
DatabaseReference refCart, refPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_by_cash);

        qrcode = findViewById(R.id.qrcode);
        refCart = FirebaseDatabase.getInstance().getReference().child("ShoppingCart").child(preferences.getDataUserID(this));
        refPayment = FirebaseDatabase.getInstance().getReference().child("Payment");

        Intent intent = getIntent();
        String totalAmount = intent.getStringExtra("totalAmount");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:a");
        String currentTime = sdf.format(System.currentTimeMillis());
        String paymentID = refPayment.push().getKey();
        Map<String, Object> paymentMap = new HashMap<>();
        paymentMap.put("paymentID",paymentID);
        paymentMap.put("customerName", preferences.getDataCustomerName(this));
        paymentMap.put("amountPay",totalAmount);
        paymentMap.put("paymentStatus","Unpaid");
        paymentMap.put("datetime",currentTime);
        paymentMap.put("paymentType","Cash");
        refPayment.child(paymentID).setValue(paymentMap);
        refCart.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                refPayment.child(paymentID).child("itemPurchase").setValue(snapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        refCart.removeValue();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        MultiFormatWriter writer = new MultiFormatWriter();
        try{
            BitMatrix matrix = writer.encode(paymentID, BarcodeFormat.QR_CODE, 350, 350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrcode.setImageBitmap(bitmap);
        } catch (WriterException e){
            e.printStackTrace();
        }

    }
}