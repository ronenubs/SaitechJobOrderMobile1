package com.example.saitechjoborder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AbsListView;

import com.example.saitechjoborder.adapters.JobOrderAdapter;
import com.example.saitechjoborder.adapters.JobOrderDetailsAdapter;

public class JobOrderDetails extends AppCompatActivity {

    private RecyclerView rvJobOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_order_details);

        rvJobOrderDetails = findViewById(R.id.rvJobOrderDetails);
        JobOrderDetailsAdapter jobOrderDetailsAdapter = new JobOrderDetailsAdapter(
                this,
                new String[]
                {"Material1","Material2","Material3","Material4","Material5","Material6",
                        "Material7","Material9","Material10","Material11","Material12","Material13",
                        "Material14","Material15"});
        rvJobOrderDetails.setAdapter(jobOrderDetailsAdapter);
        rvJobOrderDetails.setLayoutManager(new LinearLayoutManager(this));
    }
}