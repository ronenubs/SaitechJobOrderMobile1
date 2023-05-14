package com.example.saitechjoborder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.saitechjoborder.adapters.JobOrderAdapter;
import com.example.saitechjoborder.classes.JobOrder;
import com.example.saitechjoborder.connect.MyConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobOrdersList extends AppCompatActivity {

    private RecyclerView rvJobOrder;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_orders_list);

        ArrayList<JobOrder> jobOrders = new ArrayList<>();
        jobOrders = getJobOrders();

        rvJobOrder = findViewById(R.id.rvJobOrder);
        JobOrderAdapter jobOrderAdapter = new JobOrderAdapter(this, jobOrders);
        rvJobOrder.setAdapter(jobOrderAdapter);
        rvJobOrder.setLayoutManager(new LinearLayoutManager(this));
    }

    public ArrayList<JobOrder> getJobOrders() {
        ArrayList<JobOrder> jobOrders = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(
                Request.Method.POST,
                MyConstants.SERVER_NAME + "JobOrder/GetJobOrderNumbers.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    jobOrders.add(
                                            new JobOrder(
                                                    object.getInt("jobOrderNo"),
                                                    object.getInt("formalQuotationNo")));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Please provide correct data. Check also empty fields.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Please provide correct data. Check also empty fields.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(stringRequest);
        return jobOrders;
    }
}