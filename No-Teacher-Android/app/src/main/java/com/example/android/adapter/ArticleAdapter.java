package com.example.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bean.entity.Article;
import com.example.no_teacher_andorid.R;

import java.util.List;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/7 8:11
 * @Decription :
 */

public class ArticleAdapter extends ArrayAdapter<Article> {
    private Context mContext;
    private int mResource;

    public ArticleAdapter(Context context, int resource, List<Article> articles) {
        super(context, resource, articles);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.image_a);
            viewHolder.textView1 = convertView.findViewById(R.id.title_a);
            viewHolder.textView2 = convertView.findViewById(R.id.level_a);
            viewHolder.textView3 = convertView.findViewById(R.id.words_a);
            viewHolder.textView4 = convertView.findViewById(R.id.badge_a);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Article item = getItem(position);

        if (item != null) {
            viewHolder.imageView.setImageResource(item.getImageResource());
            viewHolder.textView1.setText(item.getTitle());
            viewHolder.textView2.setText(item.getLevel());
            viewHolder.textView3.setText(item.getWordCount());
            viewHolder.textView4.setText(item.getBadge());
        }

        return convertView;
    }


    static class ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;

        TextView textView4;
    }
}
