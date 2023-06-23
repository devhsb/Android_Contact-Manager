package com.hasib.contactmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hasib.contactmanager.Controller.DatabaseHandler;
import com.hasib.contactmanager.Model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_REQUEST_ID = 1;
    private Button enterBtn;
    private Button showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Call permission check
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_REQUEST_ID);
        }


        //TODO: DATABASE TEST
            DatabaseHandler db = new DatabaseHandler(MainActivity.this);
            List<Contact> contacts = new ArrayList<>(db.getAllContacts());

        //TODO: DATABASE TEST


        //Input contact info
        enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener((v) -> {
            Intent info = new Intent(this, ContactInformation.class);
            startActivity(info);
        });

        //Show Contacts info
        showBtn = findViewById(R.id.showBtn);
        showBtn.setOnClickListener((v) -> {
            Intent info = new Intent(this, ContactsView.class);
            startActivity(info);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_REQUEST_ID) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted
            } else {
                // Permission has been denied
            }
        }
    }

}