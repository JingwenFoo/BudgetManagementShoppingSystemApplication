package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ViewIncome extends AppCompatActivity {
    LineChart graph;
    DatabaseReference ref;
    List<String> date = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_income);

        graph = findViewById(R.id.graph);
        ref = FirebaseDatabase.getInstance().getReference().child("Payment");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String paymentDate = dataSnapshot.child("datetime").getValue(String.class);

                    if(!date.contains(paymentDate.substring(0,10)))
                        date.add(paymentDate.substring(0,10));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float[] totalIncome = new float[date.size()];
                ArrayList<Entry> dataValues = new ArrayList<Entry>();
                List<String> total = new ArrayList();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Payment payment = dataSnapshot.getValue(Payment.class);
                    String paymentDate = dataSnapshot.child("datetime").getValue(String.class);
                    for (int i = 0; i < date.size(); i++) {

                        if (paymentDate.startsWith(date.get(i))) {
                            totalIncome[i] += Float.parseFloat(payment.getAmountPay());
                        }
                    }

                }
                for(int j=0; j<date.size(); j++)
                {
                    System.out.println(totalIncome[j]);
                    DataPoint dataPoint = new DataPoint(j,totalIncome[j]);
                    dataValues.add(new Entry(dataPoint.getxValue(),dataPoint.getyValue()));

                }
                showChart(dataValues);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private  void showChart(ArrayList<Entry> dataValues)
    {
        LineDataSet lineDataSet = new LineDataSet(null, null);
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        LineData lineData;

        XAxis xAxis = graph.getXAxis();
        YAxis yAxisRight = graph.getAxisRight();

        xAxis.setValueFormatter(new MyAxisValueFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineWidth(3);
        xAxis.setTextSize(6);
        yAxisRight.setEnabled(false);
        Description description = new Description();
        description.setText("");
        lineDataSet.setValues(dataValues);
        lineDataSet.setLabel("Income");
        lineDataSets.add(lineDataSet);
        lineDataSet.setValueFormatter(new MyValueFormatter());
        lineDataSet.setLineWidth(3f);
        lineDataSet.setValueTextSize(8);
        lineData = new LineData(lineDataSets);
        graph.getViewPortHandler().fitScreen();
        graph.setClipChildren(false);
        graph.setClipToPadding(false);
        graph.setClipValuesToContent(false);
        graph.setDescription(description);
        graph.setData(lineData);
        graph.invalidate();
    }

    public class MyValueFormatter extends ValueFormatter{
        @Override
        public String getFormattedValue(float value) {
            return "RM "+value;
        }
    }

    public class MyAxisValueFormatter extends ValueFormatter{
        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return date.get((int) value);
        }

    }

}