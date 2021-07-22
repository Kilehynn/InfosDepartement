package com.project.infosdepartment.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.infosdepartment.R;
import com.project.infosdepartment.viewmodel.DepartmentViewModel;

public class MainActivity extends AppCompatActivity {
    private DepartmentViewModel departmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        departmentViewModel = new ViewModelProvider(this).get(DepartmentViewModel.class);

        final DepartmentListAdapter adapter = new DepartmentListAdapter(new DepartmentListAdapter.DepartmentDiff());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.submitList(departmentViewModel.getDepartmentsListEntity());
        departmentViewModel.cleanDatabase();
    }
}