package com.hasib.contactmanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hasib.contactmanager.Controller.DatabaseHandler;
import com.hasib.contactmanager.Model.Contact;

public class ContactInformation extends AppCompatActivity {
    private EditText enterName;
    private EditText enterPhone;
    private Button save;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_information);
        db = new DatabaseHandler(ContactInformation.this);

        enterName = findViewById(R.id.enterName);
        enterPhone = findViewById(R.id.enterPhone);
        save = findViewById(R.id.save);

            save.setOnClickListener((v) -> {
                if(TextUtils.isEmpty(enterName.getText().toString())){
                    enterName.setError("Please Fill The Input");
                }else if(TextUtils.isEmpty(enterPhone.getText().toString())) {
                    enterName.setError("Please Fill The Input");
                }else {
                        Contact contact = new Contact(enterName.getText().toString(), enterPhone.getText().toString());
                        db.addContact(contact);
                        Toast.makeText(this, contact.getName() + " Added", Toast.LENGTH_SHORT).show();
                    }
            });


        Common.getInstance(this).goBack(this);
        db.close();
    }
}