package com.example.saitechjoborder;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.saitechjoborder.connect.MyConstants;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etPassword;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission is granted.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Camera permission is required.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etUserName = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

         launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent i = result.getData();
                    if (result.getResultCode() == Screen.Main.ordinal()) {
                        launcher.launch(i);
                    }
                    else if (result.getResultCode() == Screen.ScanItem.ordinal()) {
                        launcher.launch(i);
                    }
                    else if (result.getResultCode() == Screen.Assignee.ordinal()) {
                        launcher.launch(i);
                    }
                    else if (result.getResultCode() == Screen.QrScanner.ordinal()) {
                        launcher.launch(i);
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

        btnLogin.setOnClickListener(view -> login());
    }

    public void login() {
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(
                Request.Method.POST,
                MyConstants.SERVER_NAME + "Worker/Login.php",
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            Intent i = new Intent(getApplicationContext(), ScanItemActivity.class);
                            setResult(Screen.Main.ordinal(), i);
                            launcher.launch(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),
                                    "Credentials are not allowed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "No data available. Please check Internet connection.",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getApplicationContext(),
                        "Please provide correct data. Check also empty fields.",
                        Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();

                map.put("username", etUserName.getText().toString());
                map.put("password", etPassword.getText().toString());

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
}