package com.hasib.contactmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hasib.contactmanager.Adapters.ContactsRecyclerViewAdapter;
import com.hasib.contactmanager.Controller.DatabaseHandler;

public class ContactsView extends AppCompatActivity {
    private TextView name;
    private TextView phone;
    private DatabaseHandler db;
    private ImageButton removeBtn;
    private TextView counter;
    int dbSize;

    private RecyclerView recyclerView;
    private ContactsRecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_view);
        db = new DatabaseHandler(ContactsView.this);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        removeBtn = findViewById(R.id.remove);
        counter = findViewById(R.id.counter);
        recyclerView = findViewById(R.id.contacts_recyclerView);
        recyclerViewAdapter = new ContactsRecyclerViewAdapter(db.getAllContacts());

        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dbSize = db.getAllContacts().size();
        counter.setText(String.valueOf(dbSize));
        //setContact(dbSize);


//            removeBtn.setOnClickListener((v) -> {
//                    if(dbSize > 0){
//                        db.removeContact(dbSize);
//                        dbSize--;
//                        counter.setText(dbSize + "");
//                        setContact(dbSize);
//                    }else {
//                        Toast.makeText(this, "No contact found", Toast.LENGTH_SHORT).show();
//                    }
//            });

            Common.getInstance(this).goBack(this);
    }

    public void setContact(int index){
        if(dbSize < 1){
            name.setText(R.string.no_contact_found);
            phone.setText("");
        }else{
            name.setText(db.getContact(index).getName());
            phone.setText(db.getContact(index).getPhoneNumber());
        }
    }

    public void handlePhoneCall(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        if(intent.resolveActivity(getPackageManager()) != null){
            intent.setData(Uri.parse( "tel:" + 123));
            startActivity(intent);
        }else {
            Toast.makeText(this, "No app found to operate the call", Toast.LENGTH_SHORT).show();
        }
    }

    public void handleRemoveBtn(View view) {
        db.removeContact(0);
    }
}