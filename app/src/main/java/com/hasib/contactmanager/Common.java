package com.hasib.contactmanager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Common extends ContextWrapper {
   private ImageButton btn;
    public Common(Context base) {
        super(base);
    }

    //actionBarBackArrow
    public void goBack(AppCompatActivity activity){
        btn = activity.findViewById(R.id.arrowBackBtn);
        btn.setOnClickListener((v)-> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    public static Common getInstance(Context context){
        return new Common(context);
    }

}
