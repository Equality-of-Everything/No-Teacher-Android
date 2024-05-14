package com.example.android.fragment;

import static com.example.android.constants.BuildConfig.USER_SERVICE;
import static com.example.android.constants.BuildConfig.WORD_SERVICE;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.adapter.ImageAdapter;
import com.example.android.api.ApiService;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.ui.activity.ImageViewActivity;
import com.example.android.ui.activity.MainActivity;
import com.example.android.ui.activity.SelectLevelActivity;
import com.example.android.ui.activity.UserTestActivity;
import com.example.android.adapter.ArticleAdapter;
import com.example.android.util.TokenManager;
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
    private ListView listView;
    private ArticleAdapter adapter;
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private int currentPage = 0;
    private int lexile = 110;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);


        // 正确范围的 ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.isTest(getContext(), TokenManager.getUserId(getContext()));
        viewModel.getIsTestLiveData().observe(getActivity(), isTest -> {
            if (isTest) {
                binding.btnTest.setEnabled(false);
            }
        });;

        // 设置 RecyclerView 和适配器
        setupRecyclerView();
        setupListView();

        ApiService apiService = RetrofitManager.getInstance(getActivity(),WORD_SERVICE).getApi(ApiService.class);
        viewModel.setApiService(apiService);

        binding.btnTest.setOnClickListener(v -> wordTestOnClick());

        viewModel.getNavigateToWordTest().observe(getViewLifecycleOwner(), shouldNavigate -> {
            if(shouldNavigate) {
                Intent intent = new Intent(getActivity(), UserTestActivity.class);
                startActivity(intent);
                getActivity().finish();  // 如果需要结束当前Activity，正确地调用
            } else {
                Toast.makeText(getActivity(), "Verification failed.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnSetDifficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectLevelActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 显示推荐文章列表
     * @date 2024/5/8 14:13
     */
    private void setupListView() {
        listView = binding.getRoot().findViewById(R.id.list_article);
        viewModel.getArticleLiveData().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                adapter = new ArticleAdapter(getActivity(), R.layout.item_list_article, articles);
                listView.setAdapter(adapter);
            }
        });
        viewModel.fetchArticles(getActivity(),lexile,currentPage); // ViewModel method to load articles data
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
//        viewModel.requestTestWords(requireContext(), currentPage);
        Intent intent = new Intent();
        intent.setClass(getActivity(), UserTestActivity.class);
        startActivity(intent);
    }


}
