package com.example.android.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.android.bean.entity.WordDetail;
import com.example.android.fragment.ReadTestPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/8 14:33
 * @Decription:
 */
public class ReadTestPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<WordDetail> wordDetails;
    public ReadTestPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }




    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    public void clearFragments() {
        fragments.clear(); // fragments假设是Adapter中维护的Fragment列表
    }
    @Override
    public int getItemPosition(@NonNull Object object) {
        // 强制每次滑动时都重新获取Fragment
        return POSITION_NONE;
    }
}
