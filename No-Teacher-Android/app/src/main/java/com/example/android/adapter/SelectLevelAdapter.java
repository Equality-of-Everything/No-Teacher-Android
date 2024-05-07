package com.example.android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.no_teacher_andorid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/4/24 9:49
 * @Decription:
 */
public class SelectLevelAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> values;
    private int selectedItem = -1; // 记录被选中的项的位置，默认为 -1，表示没有项被选中

    public SelectLevelAdapter(Context context, ArrayList<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder; // 用于持有视图组件的引用，避免每次getView时重复查找
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_level_select, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.level);
            viewHolder.imageView = convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder); // 将ViewHolder存储在Tag中以便复用
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); // 复用ViewHolder
        }
        viewHolder.textView.setText(values.get(position)); // 设置文本内容

        // 如果当前项被选中，则修改文字颜色
        if (position == selectedItem) {
            int color = ContextCompat.getColor(context, R.color.color);
            viewHolder.textView.setTextColor(color);
//            textView.setTextColor(R.colors.);// 修改为红色，你可以根据需要设置其他颜色
            viewHolder.imageView.setVisibility(View.VISIBLE); // 显示图标
        } else {
            viewHolder.textView.setTextColor(Color.GRAY); // 默认颜色灰色
            viewHolder.imageView.setVisibility(View.GONE); // 隐藏图标
        }
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

    // 设置被选中的项的位置
    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged(); // 通知适配器数据已更改，以便刷新列表项的视图
    }

    public SelectLevelAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public SelectLevelAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SelectLevelAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    public SelectLevelAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public SelectLevelAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    public SelectLevelAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
