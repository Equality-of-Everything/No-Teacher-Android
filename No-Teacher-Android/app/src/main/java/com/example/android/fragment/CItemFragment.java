package com.example.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.no_teacher_andorid.R;

public class CItemFragment extends Fragment{
    public static CItemFragment newInstance(String category) {
        CItemFragment fragment = new CItemFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_item, container, false);
        //获取到当前点击的类别
        String category = getArguments().getString("category");
        TextView categoryTextView = view.findViewById(R.id.tvDifficulty);
        if (categoryTextView != null) { // 防止空指针异常
            categoryTextView.setText(category); // 设置文本内容
        }
        return view;
    }

}
