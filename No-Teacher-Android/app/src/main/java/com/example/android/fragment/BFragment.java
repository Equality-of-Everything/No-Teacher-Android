package com.example.android.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.android.adapter.ReadTestPagerAdapter;
import com.example.android.bean.entity.WordDetail;
import com.example.android.ui.activity.SpellingActivity;
import com.example.android.util.TokenManager;
import com.example.android.viewmodel.BFragmentViewModel;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/4/24 15:19
 * @Decription:
 */
// BFragment.java

public class BFragment extends Fragment {
    private static final int SPELLING_REQUEST_CODE = 1;

    private ViewPager viewPager;
    private Button nextButton;
    private BFragmentViewModel viewModel;
    private Button backButton;
    private Button moreButton;
    private TextView pageNumberTextView;
    private ReadTestPagerAdapter pagerAdapter;
    private String userId;
    private int currentPage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        // 初始化viewModel
        viewModel = new ViewModelProvider(this).get(BFragmentViewModel.class);

        userId = TokenManager.getUserId(getContext());
        viewPager = view.findViewById(R.id.view_pager);
        nextButton = view.findViewById(R.id.next_button);
        backButton = view.findViewById(R.id.back_button);
        moreButton = view.findViewById(R.id.more_button);
        pageNumberTextView = view.findViewById(R.id.page_number_text_view);

        nextButton.setOnClickListener(v -> nextPage());
        backButton.setOnClickListener(v -> prevPage());

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updatePageNumber(position);
                updateButtonVisibility(position);
            }
        });

        // 观察ViewModel中推荐单词列表的LiveData
        viewModel.getRecommendWordsLiveData().observe(getViewLifecycleOwner(), new Observer<List<WordDetail>>() {
            @Override
            public void onChanged(List<WordDetail> wordDetails) {
                if (wordDetails != null) {
                    updateViewPagerWithWords(wordDetails);
                }
            }
        });

        // 首次加载单词数据
        viewModel.setRecommendWords(getContext(), userId, currentPage);

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpellCheckDialog();
                loadMore();
            }
            private void showSpellCheckDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("单词拼写检查")
                        .setMessage("是否检查单词拼写？")
                        .setPositiveButton("准备好了", (dialog, which) -> startSpellingActivity())
                        .setNegativeButton("复习一下", (dialog, which) -> dialog.dismiss())
                        .show();
            }
            private void startSpellingActivity() {
                // 启动拼写测试Activity，并等待返回结果
                Intent intent = new Intent(getActivity(), SpellingActivity.class);
                startActivityForResult(intent, SPELLING_REQUEST_CODE);
            }
            private void loadMore() {
                currentPage++;
                viewModel.setRecommendWords(getContext(), userId, currentPage); // 请求下一页数据
                // 观察LiveData以处理新获取的数据
                viewModel.getRecommendWordsLiveData().observe(getViewLifecycleOwner(), new Observer<List<WordDetail>>() {
                    @Override
                    public void onChanged(List<WordDetail> newWordDetails) {
                        getView().post(new Runnable() {
                            @Override
                            public void run() {
                                if (newWordDetails != null) {
                                    pagerAdapter.clearFragments();
                                    // 使用新的单词数据创建新的Fragment列表
                                    List<Fragment> updatedFragments = new ArrayList<>();
                                    for (WordDetail wordDetail : newWordDetails) {
                                        ReadTestPagerFragment fragment = ReadTestPagerFragment.newInstance(wordDetail.getParaphrasePicture(), wordDetail.getWord(), wordDetail.getParaphrase());
                                        updatedFragments.add(fragment);
                                    }
                                    pagerAdapter.updateData(updatedFragments);
                                    viewPager.setAdapter(pagerAdapter);
                                    pagerAdapter.notifyDataSetChanged();
                                    // 日志记录或调试用
                                    pagerAdapter.logFragmentsWords();
                                    // 跳转到第一个页面
                                    viewPager.setCurrentItem(0,false);
                                    updatePageNumber(0);
                                    // 更新按钮状态
                                    updateButtonVisibility(0);
                                } else {
                                    // 错误处理，如提示用户加载失败
                                    Toast.makeText(getContext(), "加载数据失败，请重试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPELLING_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getBooleanExtra("need_refresh", false)) {
                // 当从SpellingActivity返回且需要刷新数据时，刷新数据
            }
        }
    }



    private void updateViewPagerWithWords(List<WordDetail> wordDetails) {
        List<Fragment> updatedFragments = new ArrayList<>();
        for (WordDetail wordDetail : wordDetails) {
            // 假设 ReadTestPagerFragment 有一个接受 WordDetail 构造函数
            ReadTestPagerFragment fragment = ReadTestPagerFragment.newInstance(wordDetail.getParaphrasePicture(), wordDetail.getWord(), wordDetail.getParaphrase());
            updatedFragments.add(fragment);
        }

        pagerAdapter = new ReadTestPagerAdapter(getChildFragmentManager(), updatedFragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(0, false); // 设置初始页面
        updatePageNumber(0);
    }

    private void nextPage() {
        int nextItem = viewPager.getCurrentItem() + 1;
        if (nextItem < pagerAdapter.getCount()) {
            viewPager.setCurrentItem(nextItem, true);
            Log.d("BFragment", "Next page: " + nextItem);
        }
    }

    private void prevPage() {
        int prevItem = viewPager.getCurrentItem() - 1;
        if (prevItem >= 0) {
            viewPager.setCurrentItem(prevItem, true);
        }
    }

    private void updatePageNumber(int position) {
        int total = pagerAdapter.getCount();
        pageNumberTextView.setText((position + 1) + "/" + total);
    }

    private void updateButtonVisibility(int position) {
        backButton.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        nextButton.setVisibility(position == pagerAdapter.getCount() - 1 ? View.INVISIBLE : View.VISIBLE);
        moreButton.setVisibility(position == pagerAdapter.getCount() - 1 ? View.VISIBLE : View.GONE);
    }
}
