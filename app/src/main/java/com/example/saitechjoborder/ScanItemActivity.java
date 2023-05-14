package com.example.saitechjoborder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.saitechjoborder.qrscanners.ItemQRScanner;

public class ScanItemActivity extends AppCompatActivity {

    Button btnScanJobOrderItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_item);

        btnScanJobOrderItem = findViewById(R.id.btnScanJobOrderItem);

        btnScanJobOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQRScanner();
            }
        });
    }

    private void loadQRScanner() {
        startActivity(new Intent(this, ItemQRScanner.class));
    }
}