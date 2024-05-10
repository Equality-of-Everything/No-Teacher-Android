package com.example.android.adapter.ViewHolder;


import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.no_teacher_andorid.R;

public class DifficultyViewHolder extends  RecyclerView.ViewHolder{
    public CheckBox cbLibraryDifficulty;
    public DifficultyViewHolder(@NonNull View itemView) {
        super(itemView);
        cbLibraryDifficulty = itemView.findViewById(R.id.cbLibraryDifficulty);
    }
}
