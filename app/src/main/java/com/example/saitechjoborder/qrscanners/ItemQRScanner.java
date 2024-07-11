package com.example.saitechjoborder.qrscanners;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.saitechjoborder.MainActivity;
import com.example.saitechjoborder.R;
import com.example.saitechjoborder.ScanItemActivity;
import com.example.saitechjoborder.Screen;
import com.example.saitechjoborder.connect.MyConstants;
import com.example.saitechjoborder.Assignee;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemQRScanner extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    private String fqItemId;

    private String department;

    private String jobOrderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        Intent i = getIntent();
        Screen screen = (Screen) Objects.requireNonNull(i.getExtras()).get("screen");
        fqItemId = i.getStringExtra("fqItemId");
        department = i.getStringExtra("department");
        jobOrderNo = i.getStringExtra("jobOrderNo");
        try {
            CodeScannerView scannerView = findViewById(R.id.scanner_item);
            mCodeScanner = new CodeScanner(this, scannerView);
            mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> getTask(screen, result)));//fqitemid
            scannerView.setOnClickListener(view -> mCodeScanner.startPreview());

        }catch(Exception e){
            Toast.makeText(this, "Check information.", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCodeScanner.releaseResources();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCodeScanner.releaseResources();
    }

    private void getTask(Screen screen, Result result)
    {
        if(screen == Screen.ScanItem){
            try {
                String jobOrderNo = result.getText().split(",")[0];
                String fqItemId = result.getText().split(",")[1];
                getJobOrders(jobOrderNo, fqItemId);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Invalid QR.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ScanItemActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                setResult(Screen.Main.ordinal(), i);
                finish();

            }
        }
        else if(screen == Screen.Assignee){
            startWork(this.jobOrderNo, this.fqItemId);
        }
    }

    public void getJobOrders(String jobOrderNo, String fqItemId) {
        requestQueue = Volley.newRequestQueue(this);
        String toastMessage = "Try again";
        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT);

        stringRequest = new StringRequest(
                Request.Method.POST,
                MyConstants.SERVER_NAME + "JobOrder/CheckFqItemId.php",
                response -> {
                    try {
                        if (response.equals(fqItemId)) {
                            toast.setText(fqItemId);
                            toast.show();
                            Intent i = new Intent(getApplicationContext(), Assignee.class);
                            i.putExtra("fqItemId", fqItemId);
                            i.putExtra("department", department);
                            i.putExtra("jobOrderNo", jobOrderNo);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            setResult(Screen.Assignee.ordinal(), i);
                            finish();
                        } else {
                            toast.setText("Invalid QR.");
                            toast.show();
                        }
                    } catch (Exception e) {
                        toast.setText("Please try again.");
                        toast.show();
                        Intent i = new Intent(getApplicationContext(), Assignee.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        setResult(Screen.Assignee.ordinal(), i);
                        finish();
                    }
                },
                error -> {
                    toast.setText("Please provide correct data. Check also empty fields.");
                    toast.show();
                }
        ) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("fqItemId", fqItemId);
                map.put("jobOrderNo", jobOrderNo);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void startWork(String assignedJobOrder, String assignedTo) {
        requestQueue = Volley.newRequestQueue(this);
        String toastMessage = "Try again";
        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT);
        stringRequest = new StringRequest(
                Request.Method.POST,
                MyConstants.SERVER_NAME + "JobOrder/StartWork.php",
                response -> {
                    try {
                        if (response.equals("success")) {
                            toast.setText("Successfully assigned.");
                            toast.show();
                            Intent i = new Intent(getApplicationContext(), ScanItemActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            setResult(Screen.Main.ordinal(), i);
                            finish();
                        } else {
                            toast.setText("Invalid QR.");
                            toast.show();
                        }
                    }
                    catch(Exception e){
                        toast.setText("Please try again.");
                        toast.show();
                        Intent i = new Intent(getApplicationContext(), ScanItemActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        setResult(Screen.Main.ordinal(), i);
                        finish();
                    }
                },
                error -> Toast.makeText(getApplicationContext(),
                        "Please provide correct data. Check also empty fields.",
                        Toast.LENGTH_SHORT).show()
        ){
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("assignedJobOrder", assignedJobOrder);
                map.put("fqItemNo", fqItemId);
                map.put("assignedTo", assignedTo);
                map.put("department", department);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
}