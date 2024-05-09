package com.example.android.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.android.adapter.ImageAdapter;
import com.example.android.ui.activity.ImageViewActivity;
import com.example.android.ui.activity.MainActivity;
import com.example.android.ui.activity.UserRegisterActivity;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/4/24 15:21
 * @Decription:
 */
public class MineFragment extends Fragment {
    private RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        recyclerView = view.findViewById(R.id.recycler_mine);

        // 获取 Fragment 的上下文对象
        Context context = getContext();

        // 构造固定的图片资源列表
        ArrayList<Integer> imageResourceList = new ArrayList<>();
        imageResourceList.add(R.drawable.img_4); // 替换为你的图片资源 ID
        imageResourceList.add(R.drawable.img_2); // 替换为你的图片资源 ID
        imageResourceList.add(R.drawable.img_3); // 替换为你的图片资源 ID

        ImageAdapter adapter = new ImageAdapter(context, imageResourceList);
        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView,int imageResId) {
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

        Button btnEdit = view.findViewById(R.id.button_edit);
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), UserRegisterActivity.class);
                startActivity(intent);
            });
        } else {
            Log.e("MineFragment", "Button not found");
        }

        return view;
    }

}
