package com.example.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.adapter.ViewHolder.CategoryViewHolder;
import com.example.android.adapter.ViewHolder.DifficultyViewHolder;
import com.example.no_teacher_andorid.R;

import java.util.List;

public class DifficultyAdapter extends RecyclerView.Adapter<DifficultyViewHolder>{
    private Context context;
    private List<String> difficultyItems;
    private CategoryAdapter.OnItemClickListener mListener;
    //是否选择
    private int selectedPosition = -1;

    public DifficultyAdapter(Context context, List<String> difficultyItems) {
        this.context = context;
        this.difficultyItems = difficultyItems;
    }

    @NonNull
    @Override
    public DifficultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_library_difficulty, parent, false);
        return new DifficultyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DifficultyViewHolder holder, int position) {
        String difficulty = difficultyItems.get(position);
        holder.cbLibraryDifficulty.setText(difficulty);
        //点击事件
    }

    @Override
    public int getItemCount() {
        return difficultyItems.size();
    }
}
