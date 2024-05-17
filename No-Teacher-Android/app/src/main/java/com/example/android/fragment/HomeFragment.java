package com.example.android.fragment;

import static com.example.android.constants.BuildConfig.USER_SERVICE;
import static com.example.android.constants.BuildConfig.WORD_SERVICE;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.android.adapter.ImageAdapter;
import com.example.android.api.ApiService;
import com.example.android.bean.entity.Article;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.ui.activity.ImageViewActivity;
import com.example.android.ui.activity.MainActivity;
import com.example.android.ui.activity.ReadActivity;
import com.example.android.ui.activity.SelectLevelActivity;
import com.example.android.ui.activity.UserTestActivity;
import com.example.android.adapter.ArticleAdapter;
import com.example.android.util.DataManager;
import com.example.android.util.TokenManager;
import com.example.android.viewmodel.HomeViewModel;
import com.example.android.viewmodel.SelectLevelViewModel;
import com.example.android.viewmodel.UserTestViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.ActivityUserTestBinding;
import com.example.no_teacher_andorid.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

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

    private SwipeRefreshLayout swipeRefreshLayout;//用于下拉刷新的控件
    private List<Article> articles = new ArrayList<>();
    private View footerView;

    private int currentPage = 0;
    private int totalPages = 0;
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
                binding.btnSetDifficult.setEnabled(false);
            }
        });

        DataManager.getInstance().getIsSelectLiveData().observe(getActivity(), isSelect -> {
            if(isSelect) {
                binding.btnTest.setEnabled(false);
                binding.btnSetDifficult.setEnabled(false);
            }
        });


        viewModel.getArticleNum(getContext());
        totalPages = viewModel.getTotalPages();
        // 设置 RecyclerView 和适配器
        //设置ListView和SwipeRefreshLayout以实现下拉上拉刷新数据
        setupRecyclerView();
        setupListView();
        setupSwipeRefreshLayout();

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
     * @description 设置刷新监听器
     * @date 2024/5/14 9:43
     */
    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefresh;
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (currentPage < totalPages) {
                loadData(false); // 加载下一页数据
            } else {
                swipeRefreshLayout.setRefreshing(false); // 如果已经是最后一页，停止刷新动画
                Toast.makeText(getActivity(), "No more articles to load", Toast.LENGTH_SHORT).show();
            }
        });
//        swipeRefreshLayout = binding.swipeRefresh;
//        swipeRefreshLayout.setOnRefreshListener(() -> loadData(true));
    }

    /**
     * @param isRefresh:
     * @return void
     * @author Lee
     * @description 向后端请求更多数据
     * @date 2024/5/14 9:31
     */
    private void loadData(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 0; // 如果是从头刷新，重置为第一页
        } else {
            currentPage++; // 否则递增页码以加载下一页
        }

        if (currentPage <= totalPages) {
            viewModel.fetchArticles(getActivity(), lexile, currentPage);
            viewModel.getArticleLiveData().observe(getViewLifecycleOwner(), newArticles -> {
                if (isRefresh) {
                    articles.clear(); // 如果是从头刷新，则清除旧数据
                }
                articles.addAll(newArticles); // 添加新加载的数据
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            });
        } else {
            currentPage--; // 如果超过总页数，回退一页保持原样
            swipeRefreshLayout.setRefreshing(false);
        }

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
                listView.setOnItemClickListener((parent, view, position, id) -> {
                    Article clickedArticle = articles.get(position);
                    Intent intent = new Intent(getActivity(), ReadActivity.class);
                    // 传递文章数据
                    intent.putExtra("title", clickedArticle.getTitle());
                    intent.putExtra("imageUrl", clickedArticle.getCover()); // 假设是图片的URL
                    intent.putExtra("content", clickedArticle.getContent());
                    startActivity(intent);
                });
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //检查是否到达了底部
//                if(scrollState == SCROLL_STATE_IDLE && listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1) {
//                    loadData(false);
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

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
