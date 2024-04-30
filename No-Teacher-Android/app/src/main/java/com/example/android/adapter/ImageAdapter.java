package com.example.android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/4/29 14:10
 * @Decription:
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    Context context;
    ArrayList<Integer> imageResourceList;

    OnItemClickListener onItemClickListener;

    public ImageAdapter(Context context, ArrayList<Integer> imageResourceList){
        this.context = context;
        this.imageResourceList = imageResourceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // 加载本地资源的图片
        Glide.with(context).load(imageResourceList.get(position)).into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(holder.imageView, imageResourceList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageResourceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_item_image);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(ImageView imageView, int imageResId);
    }
}