package com.project.infosdepartment.views;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.project.infosdepartment.R;
import com.project.infosdepartment.model.database.entity.DepartmentEntity;
import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;
import com.project.infosdepartment.viewmodel.DepartmentViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DepartmentViewModel departmentViewModel;

    ArrayAdapter<DepartmentsListEntity> departmentsListEntityArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ListView listView = findViewById(R.id.listView);

        setSupportActionBar(toolbar);
        departmentViewModel = new ViewModelProvider(this).get(DepartmentViewModel.class);

        List<DepartmentsListEntity> departmentsListEntities = departmentViewModel.getDepartmentsListEntity();
        //final DepartmentListAdapter adapter = new DepartmentListAdapter(departmentsListEntities);
        Log.d("[DEBUG][MainActivity]", "OnCreate : setAdapter");

        departmentsListEntityArrayAdapter = new ArrayAdapter<DepartmentsListEntity>(this, android.R.layout.simple_list_item_1, departmentsListEntities) {
        };
        listView.setAdapter(departmentsListEntityArrayAdapter);
        //  listView.setLayoutManager(new LinearLayoutManager(this));
        // adapter.submitList(departmentsListEntities);
        //departmentViewModel.cleanDatabase();
        setupGroupList();
    }

    private void setupGroupList() {

        SearchView searchView = findViewById(R.id.searchView);
        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            DepartmentsListEntity departmentsListEntity = departmentsListEntityArrayAdapter.getItem(position);

            if (0 == departmentViewModel.getIfDataFetched(departmentsListEntity.getDepartmentCode()))//departmentsListEntity.getAreDataFetched())
            {
                Toast.makeText(this, "Get data from API and cache them", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Get data from cache", Toast.LENGTH_SHORT).show();
            }
            DepartmentEntity data = departmentViewModel.getDepartmentInfo(departmentsListEntity.getDepartmentCode());
            //   departmentsListEntity.setAreDataFetched(1);
            // This code will start the new activity when the settings button is clicked on the bar at the top.
            //  Intent intent = new Intent(this, GroupCourseActivity.class);
            //intent.putExtra("group", groupEntity);
            //startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                departmentsListEntityArrayAdapter.getFilter().filter(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                departmentsListEntityArrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuToInflate) {
        getMenuInflater().inflate(R.menu.action_home, menuToInflate);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                departmentViewModel.resetCache();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}