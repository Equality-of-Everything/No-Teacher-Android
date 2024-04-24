package com.example.no_teacher_andorid.adapter;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_level_select, parent, false);
        TextView textView = rowView.findViewById(R.id.level);
        textView.setText(values.get(position));
        ImageView icon = rowView.findViewById(R.id.icon); // 图标视图
        // 如果当前项被选中，则修改文字颜色
        if (position == selectedItem) {
            int color = ContextCompat.getColor(context, R.color.color);
            textView.setTextColor(color);
//            textView.setTextColor(R.colors.);// 修改为红色，你可以根据需要设置其他颜色
            icon.setVisibility(View.VISIBLE); // 显示图标
        } else {
            textView.setTextColor(Color.GRAY); // 默认颜色灰色
            icon.setVisibility(View.GONE); // 隐藏图标
        }
        return rowView;
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
