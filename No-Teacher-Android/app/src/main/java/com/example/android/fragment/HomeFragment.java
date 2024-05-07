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

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.adapter.ImageAdapter;
import com.example.android.api.ApiService;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.ui.activity.ImageViewActivity;
import com.example.android.ui.adapter.ArticleAdapter;
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        // 正确范围的 ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // 设置 RecyclerView 和适配器
        setupRecyclerView();
        setupListView();

        ApiService apiService = RetrofitManager.getInstance(getActivity(),WORD_SERVICE).getApi(ApiService.class);
        viewModel.setApiService(apiService);

        binding.btnTest.setOnClickListener(v -> wordTestOnClick());

        return binding.getRoot();
    }

    private void setupListView() {
        listView = binding.getRoot().findViewById(R.id.list_article);
        viewModel.getArticleLiveData().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                adapter = new ArticleAdapter(getActivity(), R.layout.item_list_article, articles);
                listView.setAdapter(adapter);
            }
        });
        viewModel.fetchArticles(); // ViewModel method to load articles data
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
        viewModel.requestTestWords(requireContext(), currentPage);
    }


}
