package com.example.android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.no_teacher_andorid.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/4/29 14:10
 * @Decription:
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    Context context;
    ArrayList<Integer> imageResourceList;
    private OnClickListener onClickListener;
    private List<String> textList;
    public interface OnClickListener {
        void onItemClick(int position);
    }

    public ImageAdapter(Context context, ArrayList<Integer> imageResourceList,List<String> textList,OnClickListener onClickListener){
        this.context = context;
        this.imageResourceList = imageResourceList;
        this.textList=textList;
        this.onClickListener = onClickListener;
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
        //填充文本
        String text=textList.get(position);
        holder.textView.setText(text);
        // 设置 item 点击监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageResourceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_item_image);
            textView=itemView.findViewById(R.id.item_list_text);
        }
    }
}