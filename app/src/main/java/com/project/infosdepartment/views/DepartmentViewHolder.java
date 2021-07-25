package com.project.infosdepartment.views;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.project.infosdepartment.R;

//Unused because I can't get to make the RecyclerView to work
public class DepartmentViewHolder extends RecyclerView.ViewHolder {
    private final TextView departmentItemView;

    DepartmentViewHolder(View itemView) {
        super(itemView);
        departmentItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        departmentItemView.setText(text);
    }
}


