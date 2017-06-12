package com.example.kerry.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_EXTERNEL_PERMISSION = 99;
    private Activity mainactivity;
    private TextView textContent;
    private TextView textFormatName;
    private Button buttonScan;
    private IntentIntegrator qrScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_view();

        buttonScan.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                String scanContent = result.getContents();
                String scanformat = result.getFormatName();
                textContent.setText(scanContent);
                textFormatName.setText(scanformat);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_EXTERNEL_PERMISSION:
                startCamera();
                break;
        }
    }

    private void init_view() {
        buttonScan = (Button) findViewById(R.id.buttonScan);
        textContent = (TextView) findViewById(R.id.textViewName);
        textFormatName = (TextView) findViewById(R.id.textViewAddress);
        this.mainactivity = this;
    }

    private void startCamera() {
        qrScan = new IntentIntegrator(MainActivity.this);
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        qrScan.setPrompt("請掃描");
        qrScan.setOrientationLocked(false);
        qrScan.setBeepEnabled(false);
        qrScan.initiateScan();

    }

    @Override
    public void onClick(View v) {
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "nothing",  Toast.LENGTH_LONG);
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.CAMERA }, REQUEST_EXTERNEL_PERMISSION);
        } else {
            startCamera();
        }

    }
}
