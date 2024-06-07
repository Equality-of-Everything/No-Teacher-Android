package com.example.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.adapter.ViewHolder.DifficultyViewHolder;
import com.example.no_teacher_andorid.R;

import java.util.List;

/**
 * 难度值adapter
 */
public class DifficultyAdapter extends RecyclerView.Adapter<DifficultyViewHolder>{
    private Context context;
    private List<Integer> difficultyItems;
    private OnDifficultyItemClickListener mListener;

    //是否选择
    private int selectedPosition = -1;

    public DifficultyAdapter(Context context, List<Integer> difficultyItems) {
        this.context = context;
        this.difficultyItems = difficultyItems;
    }

    public interface OnDifficultyItemClickListener {
        void onDifficultyItemClick(int difficulty);
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
        Integer difficulty = difficultyItems.get(position);
        holder.cbLibraryDifficulty.setText(String.valueOf(difficulty));
        //点击事件
        holder.itemView.setSelected( position == selectedPosition);
        holder.itemView.setOnClickListener(v ->{
            int currentPosition = holder.getAdapterPosition();
            if (selectedPosition == currentPosition) {
                // 如果点击的是已选中的项，则取消选中并重置selectedPosition
                selectedPosition = -1;
            } else {
                selectedPosition = currentPosition;
            }
            // 通知数据集变更，重新绑定所有ViewHolder
            notifyDataSetChanged();
            if(mListener != null){
                mListener.onDifficultyItemClick(difficultyItems.get(currentPosition));
            }else {
                Log.d("CategoryAdapter", "mListener is null"+ currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return difficultyItems.size();
    }
}
