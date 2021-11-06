package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.budgetmanagementshoppingsystemapplication.ManageAccount.Register;
import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.example.budgetmanagementshoppingsystemapplication.Model.preferences;
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

        refPayment.child(paymentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Payment payment = snapshot.getValue(Payment.class);
                if(payment.getPaymentStatus().matches("Paid"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PayByCash.this)
                            .setTitle("Payment Success")
                            .setMessage("You have paid RM "+totalAmount+" successfully.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in = new Intent(PayByCash.this, CustomerViewHistory.class);
                            startActivity(in);
                        }
                    });
                    builder.create().show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}