package com.example.android.adapter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.no_teacher_andorid.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder{
    public TextView categoryNameTextView;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryNameTextView = itemView.findViewById(R.id.cbLibraryLeft);

    }
}
