package com.project.infosdepartment.views;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.project.infosdepartment.R;
import com.project.infosdepartment.model.database.DepartmentDatabase;
import com.project.infosdepartment.model.database.entity.DepartmentEntity;
import com.project.infosdepartment.viewmodel.InfoViewModel;
import com.richpath.RichPath;
import com.richpath.RichPathView;

public class InfoActivity extends AppCompatActivity {

    InfoViewModel infoViewModel;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = findViewById(R.id.toolbar);


        Bundle extra = getIntent().getExtras();
        assert extra != null;

        String departmentCode = extra.getString("departmentCode");
        Log.i("[DEBUG][INFO]", "Department Code: " + departmentCode);

        infoViewModel = new ViewModelProvider(this).get(InfoViewModel.class);
        int isDataFetched = infoViewModel.getIfDataFetched(departmentCode);

        if (isDataFetched == 0) {
            Toast.makeText(this, "Get data from API and cache them", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Get data from cache", Toast.LENGTH_SHORT).show();
        }

        DepartmentDatabase.getDatabaseWriteExecutor().execute(() -> {
            DepartmentEntity departmentEntity = infoViewModel.getDepartmentInfo(departmentCode);

            while (departmentEntity == null) {

                departmentEntity = infoViewModel.getDepartmentInfo(departmentCode);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.i("[DEBUG][THREAD]", "Sleeping");
            }

            DepartmentEntity finalDepartmentEntity = departmentEntity;
            runOnUiThread(() -> {

                String departmentCodeText = "Code départemental : " + finalDepartmentEntity.getDepartmentCode();
                String departmentInhabitants = "Population : " + finalDepartmentEntity.getInhabitants();
                String departmentTowns = "Nombre de communes : " + finalDepartmentEntity.getNbTowns();
                String departmentName = finalDepartmentEntity.getDepartmentName();
                String departmentNameText = "Département : " + departmentName;
                ((TextView) findViewById(R.id.departmentCode)).setText(departmentCodeText);
                ((TextView) findViewById(R.id.departmentInhabitants)).setText(departmentInhabitants);
                ((TextView) findViewById(R.id.departmentTowns)).setText(departmentTowns);
                ((TextView) findViewById(R.id.departmentName)).setText(departmentNameText);

                RichPathView notificationsRichPathView = findViewById(R.id.departmentPosition);
                notificationsRichPathView.setVisibility(View.VISIBLE);
                RichPath dept = notificationsRichPathView.findRichPathByName(String.valueOf(departmentCode));
                dept.setFillColor(Color.RED);

                toolbar.setTitle("(" + departmentCode + ") " + departmentName);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            });
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}