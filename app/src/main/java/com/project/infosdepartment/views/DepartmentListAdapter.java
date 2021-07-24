package com.project.infosdepartment.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.infosdepartment.R;
import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;

import java.util.List;

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentViewHolder> {

    private final List<DepartmentsListEntity> departmentsListEntities;

    public DepartmentListAdapter(List<DepartmentsListEntity> departmentsListEntities) {
        this.departmentsListEntities = departmentsListEntities;
        printData();
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        Log.d("[DEBUG][DepartmentViewHolder]", "onCreateViewHolder :  inflates");
        // Inflate the custom layout
        View departmentView = inflater.inflate(R.layout.recyclerview_item, parent, false);

        // Return a new holder instance
        return new DepartmentViewHolder(departmentView);
    }

    @Override
    public void onBindViewHolder(DepartmentViewHolder holder, int position) {
        DepartmentsListEntity current = departmentsListEntities.get(position);
        String department = current.getDepartmentCode() + " - " + current.getDepartmentName();

        Log.d("[DEBUG][DepartmentViewHolder]", "onBindViewHolder :  set textview for department " + department);
        holder.bind(department);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return departmentsListEntities.size();
    }

    void printData() {
        final String dateFetched = " and the datas are cached";
        final String dateNotFetched = " and the datas are not cached";
        for (DepartmentsListEntity entity : departmentsListEntities
        ) {
            String data = dateNotFetched;
            if (entity.getAreDataFetched() == 1) {
                data = dateFetched;
            }
            Log.d("[DEBUG][MainActivity]", "printData : entity number " + entity.getId()
                    + " is the department number " + entity.getDepartmentCode() + " the " + entity.getDepartmentName() + data);
        }
    }
/*
    static class DepartmentDiff extends DiffUtil.ItemCallback<DepartmentsListEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull DepartmentsListEntity oldItem, @NonNull DepartmentsListEntity newItem) {
            boolean res = oldItem.getDepartmentCode().equals(newItem.getDepartmentCode());
            Log.i("[DEBUG][DepartmentDiff]", oldItem.getDepartmentCode() + " == " + newItem.getDepartmentCode() + " is " + res);
            return res;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DepartmentsListEntity oldItem, @NonNull DepartmentsListEntity newItem) {
            return oldItem.getDepartmentCode().equals(newItem.getDepartmentCode());
        }
    }*/
}
