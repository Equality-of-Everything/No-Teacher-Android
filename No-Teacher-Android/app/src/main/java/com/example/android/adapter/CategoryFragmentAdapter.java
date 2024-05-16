package com.example.android.adapter;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.android.fragment.CItemFragment;

import java.util.List;

public class CategoryFragmentAdapter extends FragmentStateAdapter {
    private List<String> category; // 数据列表，对应每个Fragment的内容
    public CategoryFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<String> category) {
        super(fragmentManager, lifecycle);
        this.category = category;
    }


    @Override
    public Fragment createFragment(int position) {
        // 根据位置动态创建CItemFragment并传递数据
        return CItemFragment.newInstance(category.get(position),position+1);
    }
    

    @Override
    public int getItemCount() {
        return category.size();
    }
}