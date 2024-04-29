package com.example.android.fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.android.adapter.ImageAdapter;
import com.example.android.ui.activity.ImageViewActivity;
import com.example.android.ui.activity.MainActivity;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/4/24 15:21
 * @Decription:
 */
public class MineFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        // 获取 Fragment 的上下文对象
        Context context = getContext();

        ArrayList<String> imageViewList = new ArrayList<>();

        imageViewList.add("https://nl.pinterest.com/pin/1015913628432655775/");
        imageViewList.add("https://nl.pinterest.com/pin/1015913628432471449/");
        imageViewList.add("https://nl.pinterest.com/pin/1015913628432471449/");


        ImageAdapter adapter = new ImageAdapter(getContext(),imageViewList);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String url) {
                // 创建一个意图
                Intent intent = new Intent(requireContext(), ImageViewActivity.class);
                // 将图片 URL 作为额外数据放入意图中
                intent.putExtra("image",url);

                // 如果你使用了转场动画，需要在这里设置转场动画
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), imageView, "image");

                // 使用 Fragment 的方法 startActivityForResult() 来启动新的 Activity
                startActivity(intent, options.toBundle());
            }

        });

//        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(ImageView imageView, String url) {
//                startActivity(new Intent(MineFragment.this), ImageViewActivity.class).putExtra("image",url).
//                       ActivityOptions.makeSceneTransitionAnimotion(MainActivity.this,imageView,"image").toBundl;
//            }
//        });

        recyclerView.setAdapter(adapter);

        return view;
    }

}
