package com.example.android.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.adapter.ImageAdapter;
import com.example.android.api.ApiService;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.ui.activity.ImageViewActivity;
import com.example.android.viewmodel.HomeViewModel;
import com.example.android.viewmodel.UserTestViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityUserTestBinding;
import com.example.no_teacher_andorid.databinding.FragmentHomeBinding;

import java.util.ArrayList;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/4/24 15:08
 * @Decription:
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        // 正确范围的 ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // 设置 RecyclerView 和适配器
        setupRecyclerView();

        ApiService apiService = RetrofitManager.getInstance(getActivity()).getApi(ApiService.class);
        viewModel.setApiService(apiService);

        binding.btnTest.setOnClickListener(v -> wordTestOnClick());

        return binding.getRoot();

//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        recyclerView = view.findViewById(R.id.recycler_a);
//
//        // 获取 Fragment 的上下文对象
//        Context context = getContext();
//
//        // 构造固定的图片资源列表
//        ArrayList<Integer> imageResourceList = new ArrayList<>();
//        imageResourceList.add(R.drawable.img_4); // 替换为你的图片资源 ID
//        imageResourceList.add(R.drawable.img_2); // 替换为你的图片资源 ID
//        imageResourceList.add(R.drawable.img_3); // 替换为你的图片资源 ID
//        imageResourceList.add(R.drawable.img);
//
//        ImageAdapter adapter = new ImageAdapter(context, imageResourceList);
//        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(ImageView imageView, int imageResId) {
//                // 创建一个意图
//                Intent intent = new Intent(requireContext(), ImageViewActivity.class);
//                // 将图片资源 ID 作为额外数据放入意图中
//                intent.putExtra("image", imageResId);
//
//                // 如果你使用了转场动画，需要在这里设置转场动画
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity());
//
//                // 使用 Fragment 的方法 startActivityForResult() 来启动新的 Activity
//                startActivity(intent, options.toBundle());
//            }
//        });
//
//        recyclerView.setAdapter(adapter);
//
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
//        view = binding.getRoot();
//
//        viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
//        binding.setViewModel(viewModel);
//
//        ApiService apiService = RetrofitManager.getInstance(getActivity()).getApi(ApiService.class);
//        viewModel.setApiService(apiService);
//
//        binding.btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                wordTestOnClick(v);
//            }
//        });
//        return view;
    }

    private void setupRecyclerView() {
        ArrayList<Integer> imageResourceList = new ArrayList<>();
        imageResourceList.add(R.drawable.img_4);
        imageResourceList.add(R.drawable.img_2);
        imageResourceList.add(R.drawable.img_3);
        imageResourceList.add(R.drawable.img);

        ImageAdapter adapter = new ImageAdapter(requireContext(), imageResourceList);
        adapter.setOnItemClickListener((imageView, imageResId) -> {
            Intent intent = new Intent(requireContext(), ImageViewActivity.class);
            intent.putExtra("image", imageResId);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity());
            startActivity(intent, options.toBundle());
        });
        binding.recyclerA.setAdapter(adapter);
    }

    private void wordTestOnClick() {
        viewModel.requestTestWord(requireContext());
    }


//    private void wordTestOnClick(View view) {
//        viewModel.requestTestWord(getActivity());
//
//    }


}
