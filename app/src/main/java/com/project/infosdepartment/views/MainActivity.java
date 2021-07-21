package com.project.infosdepartment.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.infosdepartment.R;
import com.project.infosdepartment.model.database.DepartmentDatabase;
import com.project.infosdepartment.viewmodel.DepartmentViewModel;

public class MainActivity extends AppCompatActivity {
    private DepartmentViewModel departmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DepartmentDatabase.getDatabase(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final DepartmentListAdapter adapter = new DepartmentListAdapter(new DepartmentListAdapter.DepartmentDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        departmentViewModel = new ViewModelProvider(this).get(DepartmentViewModel.class);
        departmentViewModel.getDepartmentsListEntity().observe(this, adapter::submitList);
    }
}