package com.project.infosdepartment.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.project.infosdepartment.R;
import com.richpath.RichPathView;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_card);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RichPathView notificationsRichPathView = findViewById(R.id.departmentPosition);

        Context context = this;

        notificationsRichPathView.setOnPathClickListener(richPath -> {
            String departmentCode = richPath.getName();
            Log.i("[DEBUG[CARD]", "People clicked on department " + departmentCode);
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("departmentCode", richPath.getName());
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}