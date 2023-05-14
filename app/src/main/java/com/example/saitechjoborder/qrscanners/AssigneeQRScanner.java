package com.example.saitechjoborder.qrscanners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.saitechjoborder.R;

public class AssigneeQRScanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignee_qrscanner);

        Intent i = getIntent();
        int jobOrderId = i.getIntExtra("jobOrderId", -1);

    }
}