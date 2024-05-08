package com.example.android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/8 14:33
 * @Decription:
 */
public class ReadTestPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

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
}
