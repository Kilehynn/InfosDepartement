package com.project.infosdepartment.views;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.project.infosdepartment.model.database.entity.DepartmentsListEntity;

public class DepartmentListAdapter extends ListAdapter<DepartmentsListEntity, DepartmentViewHolder> {
    public DepartmentListAdapter(@NonNull DiffUtil.ItemCallback<DepartmentsListEntity> diffCallback) {
        super(diffCallback);
    }

    @Override
    public DepartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DepartmentViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DepartmentViewHolder holder, int position) {
        DepartmentsListEntity current = getItem(position);
        String department = current.getDepartmentCode() + " - " + current.getDepartmentName();
        holder.bind(department);
    }

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
    }
}
