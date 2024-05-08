package com.example.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.adapter.ViewHolder.CategoryViewHolder;
import com.example.no_teacher_andorid.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{
    private Context context;
    private List<String> categories;
    private OnItemClickListener mListener;

    // 定义一个接口用于处理item点击事件
    public interface OnItemClickListener {
        void onItemClick(String category);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CategoryAdapter(Context context, List<String> categories) {
        this.context = context;
        this.categories = categories;
    }

    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_library_left, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = categories.get(position);
        holder.categoryNameTextView.setText(category);
        holder.itemView.setOnClickListener(v ->{
            if(mListener != null){
                mListener.onItemClick(categories.get(position));
            }
        } );
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
