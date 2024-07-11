package com.example.saitechjoborder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.saitechjoborder.qrscanners.ItemQRScanner;

public class Assignee extends AppCompatActivity {

    Button btnStart;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignee_qrscanner);

        Intent i = getIntent();
        String fqItemId = i.getStringExtra("fqItemId");
        String department = i.getStringExtra("department");
        String jobOrderNo = i.getStringExtra("jobOrderNo");

        btnStart = (Button) findViewById(R.id.btnAssigneeQRScanner);
        
        btnStart.setOnClickListener(view -> startWork(fqItemId, department, jobOrderNo));
    }

    private void startWork(String fqItemId, String department, String jobOrderNo) {
        Intent i = new Intent(this, ItemQRScanner.class);
        i.putExtra("fqItemId", fqItemId);
        i.putExtra("screen", Screen.Assignee);
        i.putExtra("department", department);
        i.putExtra("jobOrderNo", jobOrderNo);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        setResult(Screen.QrScanner.ordinal(), i);
        finish();
    }
}