package com.example.android.fragment;

import static com.example.android.constants.BuildConfig.WORD_SERVICE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.adapter.ArticleAdapter;
import com.example.android.adapter.DifficultyAdapter;
import com.example.android.api.ApiService;
import com.example.android.bean.entity.Article;
import com.example.android.http.retrofit.RetrofitManager;

import com.example.android.ui.activity.ReadActivity;
import com.example.android.viewmodel.HomeViewModel;
import com.example.no_teacher_andorid.R;
import com.example.no_teacher_andorid.databinding.FragmentCItemBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CItemFragment extends Fragment{
    private RecyclerView difficultyRV;
    private ListView articleRV;
    private ArticleAdapter adapter;
    private FragmentCItemBinding binding;
    private HomeViewModel viewModel;
    private int lexile = 110;
    private int currentPage = 0;
    private static int typeId;
    private String category;
    public static CItemFragment newInstance(String category,int typeId) {
        CItemFragment fragment = new CItemFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        args.putInt("typeId",typeId);
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_c_item, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        //设置文章列表
        ApiService apiService = RetrofitManager.getInstance(getActivity(),WORD_SERVICE).getApi(ApiService.class);
        viewModel.setApiService(apiService);
        //获取到当前点击的类别
        category = getArguments().getString("category");
        typeId =getArguments().getInt("typeId");
//        //测试是否连接上
//        TextView categoryTextView = view.findViewById(R.id.tvDifficulty);
//        if (categoryTextView != null) { // 防止空指针异常
//            categoryTextView.setText(category); // 设置文本内容
//        }


        //设置难度列表
        difficultyRV = binding.getRoot().findViewById(R.id.difficultyRV);
        List<Integer> difficultyItemList = new ArrayList<>(Arrays.asList(110,150,200));
        //创建难度列表适配器
        DifficultyAdapter difficultyadapter = new DifficultyAdapter(getContext(),difficultyItemList);
        //设置水平布局
        difficultyRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        //设置点击事件
        difficultyadapter.setOnItemClickListener((difficulty) ->{
            Log.d("DifficultyAdapter", "onDifficultyItemClick: 分类 - " +category+ "Id-" + typeId + ", 难度 - " +difficulty);
            lexile = difficulty;
            setupArticle();

        });


        //设置适配器
        difficultyRV.setAdapter(difficultyadapter);
        setupArticle();
        return binding.getRoot();
    }

    private void setupArticle() {
        final int FIRST_CATEGORY_ID = 0;
        //获取文章列表
        articleRV = binding.getRoot().findViewById(R.id.contentRV);
        viewModel.getArticleLiveData().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                adapter = new ArticleAdapter(getActivity(), R.layout.citem_list_article, articles);
                //设置适配器
                articleRV.setAdapter(adapter);
                //文章详情点击事件
                articleRV.setOnItemClickListener((parent, view, position, id) -> {
                    Article clickedArticle = articles.get(position);
                    Intent intent = new Intent(getActivity(), ReadActivity.class);
                    // 传递文章数据
                    intent.putExtra("title", clickedArticle.getTitle());
                    intent.putExtra("imageUrl", clickedArticle.getCover());
                    intent.putExtra("content", clickedArticle.getContent());
                    startActivity(intent);
                });
            }
        });
//        viewModel.fetchArticles(getActivity(),lexile,currentPage);
        Log.e("typeIdAAAAAAA", typeId +""+lexile);
        if (typeId == FIRST_CATEGORY_ID) {
            // 对第一个类别使用特殊的方法获取数据，**精选
            viewModel.fetchArticles(getActivity(), lexile, currentPage);
        } else {
            // 对其他类别使用常规方法获取数据
            viewModel.fetchAllArticle(getActivity(), typeId, lexile);
        }
    }
}
