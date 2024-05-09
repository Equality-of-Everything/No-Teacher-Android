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
//        Fragment existingFragment = getChildFragmentManager().findFragmentById(R.id.container_for_citem_fragment);
//        if (existingFragment != null) {
//            getChildFragmentManager().beginTransaction().remove(existingFragment).commitNow();
//        }
        CItemFragment cItemFragment = new CItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        cItemFragment.setArguments(bundle);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.container_for_citem_fragment, cItemFragment)
                .commit();
    }


}
