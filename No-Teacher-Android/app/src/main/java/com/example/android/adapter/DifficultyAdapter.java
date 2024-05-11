package com.example.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.adapter.ViewHolder.CategoryViewHolder;
import com.example.android.adapter.ViewHolder.DifficultyViewHolder;
import com.example.no_teacher_andorid.R;

import java.util.List;

public class DifficultyAdapter extends RecyclerView.Adapter<DifficultyViewHolder>{
    private Context context;
    private List<String> difficultyItems;
    private OnDifficultyItemClickListener mListener;
    //是否选择
    private int selectedPosition = -1;

    public DifficultyAdapter(Context context, List<String> difficultyItems) {
        this.context = context;
        this.difficultyItems = difficultyItems;
    }

    public interface OnDifficultyItemClickListener {
        void onDifficultyItemClick(int position, String difficulty);
    }
    public void setOnItemClickListener(OnDifficultyItemClickListener listener) {
        mListener = listener;
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
        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onDifficultyItemClick(position, difficulty);
            }
        });
    }

    @Override
    public int getItemCount() {
        return difficultyItems.size();
    }
}
