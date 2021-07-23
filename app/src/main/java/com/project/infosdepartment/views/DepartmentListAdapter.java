package com.project.infosdepartment.views;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;

import java.util.List;

public class DepartmentListAdapter extends ListAdapter<DepartmentsListEntity, DepartmentViewHolder> {

    private final List<DepartmentsListEntity> departmentsListEntities;

    public DepartmentListAdapter(@NonNull DiffUtil.ItemCallback<DepartmentsListEntity> diffCallback, List<DepartmentsListEntity> departmentsListEntities) {
        super(diffCallback);
        this.departmentsListEntities = departmentsListEntities;
    }

    @Override
    public DepartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.err.println("HEREEEEE, I'M HERE !!!");
        return DepartmentViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DepartmentViewHolder holder, int position) {
        DepartmentsListEntity current = departmentsListEntities.get(position);
        String department = current.getDepartmentCode() + " - " + current.getDepartmentName();

        Log.i("[INFO][DepartmentViewHolder]", "onBindViewHolder :  set textview for department " + department);
        holder.bind(department);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return departmentsListEntities.size();
    }

    static class DepartmentDiff extends DiffUtil.ItemCallback<DepartmentsListEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull DepartmentsListEntity oldItem, @NonNull DepartmentsListEntity newItem) {
            boolean res = oldItem.getDepartmentCode().equals(newItem.getDepartmentCode());
            Log.i("[DEBUG][DepartmentDiff]", oldItem.getDepartmentCode() + " == " + newItem.getDepartmentCode() + " is " + res);
            System.err.println("HEREEEEE");
            return res;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DepartmentsListEntity oldItem, @NonNull DepartmentsListEntity newItem) {
            return oldItem.getDepartmentCode().equals(newItem.getDepartmentCode());
        }
    }
}
