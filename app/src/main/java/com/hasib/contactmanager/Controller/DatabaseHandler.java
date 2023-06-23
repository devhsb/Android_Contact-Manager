package com.hasib.contactmanager.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.hasib.contactmanager.Model.Contact;
import com.hasib.contactmanager.Util.DBUtil;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, DBUtil.DATABASE_NAME, null, DBUtil.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tableQuery = "CREATE TABLE " + DBUtil.TABLE_NAME + "(" +
                            DBUtil.KEY_ID + " INTEGER PRIMARY KEY, " + DBUtil.KEY_NAME + " TEXT," + DBUtil.KEY_PHONE + " TEXT" + ")";

        sqLiteDatabase.execSQL(tableQuery);
        Log.d(TAG, "onCreate: " + "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{DBUtil.DATABASE_NAME});

        //Create a table again
        onCreate(sqLiteDatabase);
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.KEY_NAME, contact.getName());
        values.put(DBUtil.KEY_PHONE, contact.getPhoneNumber());

        db.insert(DBUtil.TABLE_NAME, null, values);
        Log.d(TAG, "addContact: " + contact.getName() + " Added");
        db.close();
    }

    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DBUtil.TABLE_NAME, new String[]{DBUtil.KEY_ID, DBUtil.KEY_NAME, DBUtil.KEY_PHONE},
                DBUtil.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null,null,null);

        if(cursor != null){
                cursor.moveToFirst();

                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                Log.d(TAG, "getContact: " + contact.getName() + " is here");
                db.close();
                cursor.close();
                return contact;
        }
        db.close();
        return null;
    }

    public List<Contact> getAllContacts(){
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAllQuery = "SELECT * FROM " + DBUtil.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllQuery, null);
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contacts.add(contact);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return contacts;
    }

    public void removeContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBUtil.TABLE_NAME, DBUtil.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.KEY_NAME, contact.getName());
        values.put(DBUtil.KEY_PHONE, contact.getPhoneNumber());

        db.update(DBUtil.TABLE_NAME, values, DBUtil.KEY_ID + "=?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

}
