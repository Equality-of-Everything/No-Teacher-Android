package com.example.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android.adapter.CategoryAdapter;
import com.example.android.adapter.CategoryPagerAdapter;
import com.example.no_teacher_andorid.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.leftRv);
        List<String> categoryList =new ArrayList<>(Arrays.asList("精选阅读","人工智能","前沿技术","太空宇宙","生物医疗","自然科学","环境生态","历史文化","艺术文学","休闲生活","社会现象","成长教育","心理感情"));
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(categoryAdapter);
//        // 初始化Fragment列表
//        List<Fragment> fragments = new ArrayList<>();
//        // 添加更多Fragment
//        fragments.add(CItemFragment.newInstance("测试标题1"));
//        // 创建PagerAdapter实例
//        CategoryPagerAdapter pagerAdapter = new CategoryPagerAdapter(getChildFragmentManager(), fragments);
//        ViewPager viewPager = view.findViewById(R.id.viewPager);
//        viewPager.setAdapter(pagerAdapter);
        return view;
    }
}
