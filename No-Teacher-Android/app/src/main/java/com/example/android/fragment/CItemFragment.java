package com.example.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.no_teacher_andorid.R;

public class CItemFragment extends Fragment{
    public static CItemFragment newInstance(String content) {
        CItemFragment fragment = new CItemFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String content = getArguments().getString("category");
            // 可以在这里保存数据，或在onCreateView中直接使用
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_item, container, false);
        String category = getArguments().getString("category");
        TextView categoryTextView = view.findViewById(R.id.tvDifficulty);
        categoryTextView.setText(category);
        return view;
    }

}
