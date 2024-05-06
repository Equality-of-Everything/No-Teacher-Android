package com.example.android.fragment;


import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.adapter.ArticleAdapter;
import com.example.android.adapter.ImageAdapter;
import com.example.android.bean.entity.Article;
import com.example.android.ui.activity.ImageViewActivity;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/4/24 15:08
 * @Decription:
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListView listView;
    private ArticleAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_a);
        listView = view.findViewById(R.id.list_article);
        ArrayList<Article> dataList = getData(); // 获取数据列表
        adapter = new ArticleAdapter(getActivity(), R.layout.item_list_article, dataList);
        listView.setAdapter(adapter);


        // 获取 Fragment 的上下文对象
        Context context = getContext();

        // 构造固定的图片资源列表
        ArrayList<Integer> imageResourceList = new ArrayList<>();
        imageResourceList.add(R.drawable.img_4); // 替换为你的图片资源 ID
        imageResourceList.add(R.drawable.img_2); // 替换为你的图片资源 ID
        imageResourceList.add(R.drawable.img_3); // 替换为你的图片资源 ID
        imageResourceList.add(R.drawable.img);

        ImageAdapter adapter = new ImageAdapter(context, imageResourceList);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, int imageResId) {
                // 创建一个意图
                Intent intent = new Intent(requireContext(), ImageViewActivity.class);
                // 将图片资源 ID 作为额外数据放入意图中
                intent.putExtra("image", imageResId);

                // 如果你使用了转场动画，需要在这里设置转场动画
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity());

                // 使用 Fragment 的方法 startActivityForResult() 来启动新的 Activity
                startActivity(intent, options.toBundle());
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<Article> getData() {
        ArrayList<Article> data = new ArrayList<>();
        data.add(new Article(R.drawable.friend_item, "标题1", "难度600","200字","人工智能"));
        data.add(new Article(R.drawable.friend_item, "标题2", "难度600","210字","人工智能"));
        data.add(new Article(R.drawable.friend_item, "标题3", "难度600","201字","人工智能"));
        return data;
    }
}

