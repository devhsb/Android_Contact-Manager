package com.hasib.contactmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hasib.contactmanager.Adapters.ContactsRecyclerViewAdapter;
import com.hasib.contactmanager.Controller.DatabaseHandler;
import com.hasib.contactmanager.Model.Contact;
import com.hasib.contactmanager.Util.ClickListener;

public class ContactsView extends AppCompatActivity implements ClickListener {
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
        removeBtn = findViewById(R.id.removeContactBtn);
        counter = findViewById(R.id.counter);
        recyclerView = findViewById(R.id.contacts_recyclerView);
        recyclerViewAdapter = new ContactsRecyclerViewAdapter(db.getAllContacts(), this);

        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        dbSize = db.getAllContacts().size();
        counter.setText(String.valueOf(dbSize));

            Common.getInstance(this).goBack(this);
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

    @Override
    public void ClickedContact(int id) {
        Contact contact = db.getContact(id);

        AlertDialog.Builder alert = new AlertDialog.Builder(ContactsView.this);
        alert.setTitle("Delete Contact");
        alert.setMessage("Are you sure to delete " + contact.getName() + " ?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.removeContact(id);
                dialogInterface.dismiss();
            }
        });

        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alert.show();
    }
}