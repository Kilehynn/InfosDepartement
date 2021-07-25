package com.project.infosdepartment.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.project.infosdepartment.R;
import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;
import com.project.infosdepartment.viewmodel.DepartmentViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DepartmentViewModel departmentViewModel;

    ArrayAdapter<DepartmentsListEntity> departmentsListEntityArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ListView listView = findViewById(R.id.listView);

        setSupportActionBar(toolbar);
        departmentViewModel = new ViewModelProvider(this).get(DepartmentViewModel.class);

        List<DepartmentsListEntity> departmentsListEntities = departmentViewModel.getDepartmentsListEntity();
        //Because I am fetching the list of departments from the API, the user has to reload the app
        // when he use it for the first time, otherwise the list will be empty
        if (departmentsListEntities.size() == 0) {
            Toast.makeText(this, R.string.firstUse, Toast.LENGTH_LONG).show();
            LinearLayout layout = findViewById(R.id.linearLayout);
            TextView textView = new TextView(this);
            textView.setText(R.string.firstUse);
            textView.setPadding(16, 64, 16, 32);
            textView.setTextSize(20);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(textView);
            layout.removeView(listView);
        } else {
            departmentsListEntityArrayAdapter = new ArrayAdapter<DepartmentsListEntity>(this, android.R.layout.simple_list_item_1, departmentsListEntities) {
            };
            listView.setAdapter(departmentsListEntityArrayAdapter);
            // Remaining code of an attempt to usue RecyclerView and LiveData but couldn't make it work,
            // the RecyclerView remained empty
            // listView.setLayoutManager(new LinearLayoutManager(this));
            // adapter.submitList(departmentsListEntities);

            setupGroupList();
        }
    }

    private void setupGroupList() {

        SearchView searchView = findViewById(R.id.searchView);
        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            DepartmentsListEntity departmentsListEntity = departmentsListEntityArrayAdapter.getItem(position);
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("departmentCode", departmentsListEntity.getDepartmentCode());
            startActivity(intent);
        });
        //Set up the searchView
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
            case R.id.interactiveCard:
                showInteractiveCard();
                return true;
            case R.id.refresh:
                departmentViewModel.resetCache();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showInteractiveCard() {
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }

}