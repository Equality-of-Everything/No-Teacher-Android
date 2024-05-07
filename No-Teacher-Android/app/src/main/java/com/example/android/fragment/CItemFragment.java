package com.example.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android.adapter.CategoryPagerAdapter;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CItemFragment extends Fragment {

    public static Fragment newInstance(String title) {
        CItemFragment fragment = new CItemFragment();
        // 使用Bundle来传递参数
        Bundle args = new Bundle();
        args.putString("ARG_TITLE", title);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_item, container, false);

        return view;
    }

}
