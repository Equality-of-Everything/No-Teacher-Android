package com.example.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.no_teacher_andorid.R;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/8 14:29
 * @Decription:
 */
public class ReadTestPagerFragment extends Fragment {
//    private static final String ARG_PAGE_TITLE = "page_title";
//    private String pageTitle;
//
//    public static ReadTestPagerFragment newInstance(String pageTitle) {
//        ReadTestPagerFragment fragment = new ReadTestPagerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PAGE_TITLE, pageTitle);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            pageTitle = getArguments().getString(ARG_PAGE_TITLE);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_read_test, container, false);
//
//        // 获取视图中的组件
//        ImageView imageView = view.findViewById(R.id.imageView);
//        TextView textView = view.findViewById(R.id.textView);
//        Button button1 = view.findViewById(R.id.button1);
//        Button button2 = view.findViewById(R.id.button2);
//
//        // 设置图片、文本和按钮的内容
//        imageView.setImageResource(R.drawable.img);
//        textView.setText("This is " + pageTitle);
//        button1.setText("Button 1");
//        button2.setText("Button 2");
//
//        // 按钮点击事件的处理
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 处理按钮1点击事件
//            }
//        });
//
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 处理按钮2点击事件
//            }
//        });
//
//        return view;
//    }


    private static final String ARG_IMAGE_RES_ID = "image_res_id";
    private static final String ARG_TEXT = "text";

    private int imageResId;
    private String text;

    public ReadTestPagerFragment() {
        // Required empty public constructor
    }

    public static ReadTestPagerFragment newInstance(int imageResId, String text) {
        ReadTestPagerFragment fragment = new ReadTestPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResId = getArguments().getInt(ARG_IMAGE_RES_ID);
            text = getArguments().getString(ARG_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read_test, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

        imageView.setImageResource(imageResId);
        textView.setText(text);

        return view;
    }
}
