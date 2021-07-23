package com.project.infosdepartment.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.infosdepartment.R;

public class DepartmentViewHolder extends RecyclerView.ViewHolder {
    private final TextView departmentItemView;

    DepartmentViewHolder(View itemView) {
        super(itemView);
        departmentItemView = itemView.findViewById(R.id.textView);
    }

    static DepartmentViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new DepartmentViewHolder(view);
    }

    public void bind(String text) {
        departmentItemView.setText(text);
    }
}


