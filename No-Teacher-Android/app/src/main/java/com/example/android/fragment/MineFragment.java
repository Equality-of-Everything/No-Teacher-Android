package com.example.android.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.adapter.ImageAdapter;
import com.example.android.ui.activity.CalendarActivity;
import com.example.android.ui.activity.ColumnActivity;
import com.example.android.ui.activity.ImageViewActivity;
import com.example.android.ui.activity.UserEditActivity;
import com.example.android.util.DataManager;
import com.example.android.util.TokenManager;
import com.example.no_teacher_andorid.R;
import com.google.android.material.carousel.CarouselLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/4/24 15:21
 * @Decription:
 */
public class MineFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageView ivAvatar;
    private TextView tvName;

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
        List<String> listText = new ArrayList<>(Arrays.asList("成长记录", "近期记录", "这是一个文本2"));
        ImageAdapter adapter = new ImageAdapter(context, imageResourceList, listText, new ImageAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(getActivity(), CalendarActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), ColumnActivity.class);
                        startActivity(intent1);
                        break;
                }
            }
        });

        recyclerView.setAdapter(adapter);

//        // 设置 CarouselLayoutManager 和 HeroCarouselStrategy
//        CarouselLayoutManager layoutManager = new CarouselLayoutManager(new HeroCarouselStrategy());
//        recyclerView.setLayoutManager(layoutManager);
//
//        // 设置 CarouselSnapHelper
//        CarouselSnapHelper snapHelper = new CarouselSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

//        // 自动循环播放
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                int itemCount = adapter.getItemCount();
//                if (itemCount > 1) {
//                    int nextItem = (layoutManager.findFirstVisibleItemPosition() + 1) % itemCount;
//                    recyclerView.smoothScrollToPosition(nextItem);
//                    handler.postDelayed(this, 3000); // 设置循环播放的间隔时间
//                }
//            }
//        };
//        handler.postDelayed(runnable, 3000); // 初始化的延迟时间

        // 其他初始化代码
        Button btnEdit = view.findViewById(R.id.button_edit);
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), UserEditActivity.class);
                startActivity(intent);
            });
        } else {
            Log.e("MineFragment", "Button not found");
        }

        ivAvatar = view.findViewById(R.id.image_avatar);
        tvName = view.findViewById(R.id.text_name);

        tvName.setText(TokenManager.getUserName(getContext()));
        String avatar = TokenManager.getUserAvatar(getContext());
        if (avatar != null) {
            Glide.with(getActivity())
                    .load(avatar)
                    .into(ivAvatar);
        }

        LiveData<Boolean> isRefreshNameLiveData = DataManager.getInstance().getIsRefreshNameLiveData();
        if (isRefreshNameLiveData != null) {
            isRefreshNameLiveData.observe(getViewLifecycleOwner(), isRefreshName -> {
                if (isRefreshName != null && isRefreshName) {
                    tvName.setText(TokenManager.getUserName(getActivity()));
                }
            });
        }

        LiveData<Boolean> isRefreshAvatarLiveData = DataManager.getInstance().getIsRefreshAvatarLiveData();
        if (isRefreshAvatarLiveData != null) {
            isRefreshAvatarLiveData.observe(getActivity(), isRefreshAvatar -> {
                if (isRefreshAvatar != null && isRefreshAvatar) {
                    Glide.with(this).load(TokenManager.getUserAvatar(getActivity())).into(ivAvatar);
                }
            });
        }

        return view;
    }

}