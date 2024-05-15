package com.example.android.adapter.ViewHolder;


import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.no_teacher_andorid.R;

public class DifficultyViewHolder extends  RecyclerView.ViewHolder{
    public TextView cbLibraryDifficulty;

    public DifficultyViewHolder(@NonNull View itemView) {
        super(itemView);
        cbLibraryDifficulty = itemView.findViewById(R.id.cbLibraryDifficulty);
    }
}
