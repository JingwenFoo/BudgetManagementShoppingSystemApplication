package com.example.budgetmanagementshoppingsystemapplication.ManagePayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.budgetmanagementshoppingsystemapplication.Model.DataPoint;
import com.example.budgetmanagementshoppingsystemapplication.Model.Payment;
import com.example.budgetmanagementshoppingsystemapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewIncome extends AppCompatActivity {
    LineChart graph;
    Button exportExcelBtn;
    DatabaseReference ref;
    List<String> date = new ArrayList<>();
    ArrayList<Entry> dataValues;
    File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/DailyIncome.xls");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_income);

        graph = findViewById(R.id.graph);
        exportExcelBtn = findViewById(R.id.exportExcelBtn);
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
                dataValues = new ArrayList<Entry>();
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
                    DataPoint dataPoint = new DataPoint(j,totalIncome[j]);
                    dataValues.add(new Entry(dataPoint.getxValue(),dataPoint.getyValue()));

                }
                showChart(dataValues);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ActivityCompat.requestPermissions(ViewIncome.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);


        exportExcelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                HSSFSheet hssfSheet = hssfWorkbook.createSheet("DailyIncome");
                HSSFRow hssfRow = hssfSheet.createRow(0);
                HSSFCell hssfCell = hssfRow.createCell(0);
                HSSFCell hssfCell1 = hssfRow.createCell(1);

                hssfCell.setCellValue("Date");
                hssfCell1.setCellValue("Income(RM)");

                for (int i=0; i<date.size(); i++)
                {
                    HSSFRow hssfRow1 = hssfSheet.createRow(i+1);
                    HSSFCell hssfCellDate = hssfRow1.createCell(0);
                    HSSFCell hssfCellIncome = hssfRow1.createCell(1);

                    hssfCellDate.setCellValue(date.get(i));
                    hssfCellIncome.setCellValue(dataValues.get(i).getY());
                }


                try {
                    if (!filePath.exists()) {
                        filePath.createNewFile();

                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                    hssfWorkbook.write(fileOutputStream);
                    if (fileOutputStream!=null)
                    {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                    Toast.makeText(ViewIncome.this, "Excel file exported successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri mydir = Uri.parse(filePath.toString());
                    intent.setDataAndType(mydir,"*/*");
                    startActivity(intent);

                }catch (IOException e) {
                    e.printStackTrace();
                }

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