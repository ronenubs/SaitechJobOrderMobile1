package com.example.saitechjoborder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.saitechjoborder.qrscanners.ItemQRScanner;

public class ScanItemActivity extends AppCompatActivity {

    Button btnScanJobOrderItem;
    TextView tvDepartment;

    String[] department = { "Busbarring ", "Bending", "Construction ", "Assembly", "Painting " };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_item);

        btnScanJobOrderItem = findViewById(R.id.btnScanJobOrderItem);
        tvDepartment = findViewById(R.id.tvDepartment);

        SelectDepartment();

        btnScanJobOrderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQRScanner();
            }
        });

        tvDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectDepartment();
            }
        });
    }

    private void SelectDepartment(){
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, department);
        final Spinner sp = new Spinner(this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);

        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the message show for the Alert time
        builder.setMessage("Please select department.");

        builder.setView(sp);
        // Set Alert Title
        builder.setTitle("Department");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        builder.setPositiveButton("Select", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            tvDepartment.setText(sp.getSelectedItem().toString());
        });

        // Show the Alert Dialog box
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to logout?");

        // Set Alert Title
        builder.setTitle("Logout");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            finish();
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    private void loadQRScanner() {
        Intent i =  new Intent(this, ItemQRScanner.class);
        i.putExtra("screen", Screen.ScanItem);
        i.putExtra("department", tvDepartment.getText().toString());
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        setResult(Screen.QrScanner.ordinal(), i);
        finish();
    }
}