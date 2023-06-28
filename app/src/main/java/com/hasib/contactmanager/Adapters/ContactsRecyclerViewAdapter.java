package com.hasib.contactmanager.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hasib.contactmanager.Model.Contact;
import com.hasib.contactmanager.R;
import com.hasib.contactmanager.Util.ClickListener;

import java.util.List;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

    private List<Contact> contactsList;
    private ClickListener clickListener;

    public ContactsRecyclerViewAdapter(List<Contact> contactsList, ClickListener clickListener) {
        this.contactsList = contactsList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsRecyclerViewAdapter.ViewHolder holder, int position) {
        Contact contact = new Contact(contactsList.get(position).getName(), contactsList.get(position).getPhoneNumber());
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private TextView phone;
        private ImageButton removeBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            removeBtn = itemView.findViewById(R.id.removeContactBtn);
            removeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if(id == R.id.removeContactBtn){
                clickListener.ClickedContact(getAdapterPosition()+1);
            }
        }
    }

}
