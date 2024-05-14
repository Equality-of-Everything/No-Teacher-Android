package com.example.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bean.entity.Article;
import com.example.no_teacher_andorid.R;

import java.util.List;

/**
 * @Author : Tcy
 * @Date : Created in 2024/5/7 8:11
 * @Decription :
 */

public class ArticleAdapter extends ArrayAdapter<Article> {
    private List<Article> articles;
    private Context mContext;
    private int  mResource;

    public ArticleAdapter(Context context, int resource, List<Article> articles) {
        super(context, resource, articles);
        mContext = context;
        mResource = resource;
        this.articles = articles;
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
            Glide.with(mContext).load(item.getCover()).into(viewHolder.imageView);
            viewHolder.textView1.setText(item.getTitle());
            viewHolder.textView2.setText("难度："+item.getLexile());
            viewHolder.textView3.setText(item.getWordNum()+"词");
            viewHolder.textView4.setText(item.getType());
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

    /**
     * @param item:
     * @return void
     * @author Lee
     * @description 添加新的文章数据
     * @date 2024/5/14 9:12
     */
    public void addMoreArticle(List<Article> item) {
        this.articles.addAll(item);
        notifyDataSetChanged();
    }

    public void refreshItems(List<Article> item) {
        this.articles.clear();
        this.articles.addAll(item);
        notifyDataSetChanged();
    }
}
