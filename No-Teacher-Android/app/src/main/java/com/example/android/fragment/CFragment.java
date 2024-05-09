package com.example.android.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android.adapter.CategoryAdapter;
import com.example.android.adapter.CategoryFragmentAdapter;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.leftRv);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);
        List<String> categoryList =new ArrayList<>(Arrays.asList(
                "精选阅读","人工智能","前沿技术","太空宇宙","生物医疗","自然科学",
                "环境生态","历史文化","艺术文学","休闲生活","社会现象","成长教育",
                "心理感情"));
        //连接子fragment的adapter
        viewPager2.setAdapter(new CategoryFragmentAdapter(getChildFragmentManager(),getViewLifecycleOwner().getLifecycle(),categoryList));
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryAdapter.setOnItemClickListener(category -> {
            Log.d("CFragment", "onItemClick: " + category);
            replaceChildFragment(category);
        });
        recyclerView.setAdapter(categoryAdapter);
        return view;
    }
    //切换子fragment
    private void replaceChildFragment(String category) {
        // 使用类别名称作为Fragment的tag，便于查找和复用
        String tag = "CItemFragment_" + category;

        // 尝试从FragmentManager中查找已存在的同Tag的Fragment
        CItemFragment existingFragment = (CItemFragment) getChildFragmentManager().findFragmentByTag(tag);

        if (existingFragment != null) {
            // 如果找到了，则更新其参数并显示（如果需要更新参数的话）
            // 注意：此处假设CItemFragment内部有处理传入参数的方法或逻辑
            Bundle args = new Bundle();
            args.putString("category", category);
            existingFragment.setArguments(args);

            // 如果Fragment尚未添加到UI中，需要执行添加操作
            if (!existingFragment.isAdded()) {
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_for_citem_fragment, existingFragment, tag) // 使用tag
                        .commit();
            }
        } else {
            // 如果没有找到，则创建新的Fragment实例并设置tag
            CItemFragment cItemFragment = new CItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", category);
            cItemFragment.setArguments(bundle);

            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_for_citem_fragment, cItemFragment, tag) // 使用tag
                    .commit();
        }
    }


}
