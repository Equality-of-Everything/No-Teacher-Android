package com.example.android.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
public class BFragment extends Fragment {
    private ViewPager viewPager;
    private Button nextButton;
    private BFragmentViewModel viewModel;
    private Button backButton;
    private Button moreButton;
    private TextView pageNumberTextView;
    private ReadTestPagerAdapter pagerAdapter;
    private String userId ;
    private int currentPage=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_b, container, false);
        //初始化viewModel
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
        viewModel.setRecommendWords(getContext(), userId, currentPage);
        //观察ViewModel中推荐单词列表的LiveData
        viewModel.getRecommendWordsLiveData().observe(getViewLifecycleOwner(), new Observer<List<WordDetail>>() {
            @Override
            public void onChanged(List<WordDetail> wordDetails) {
                if (wordDetails != null){
                    updateViewPagerWithWords(wordDetails);
                }
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSpellCheckDialog();
            }
            private void showSpellCheckDialog(){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("单词拼写检查")
                        .setMessage("是否检查单词拼写？")
                        .setPositiveButton("准备好了", (dialog, which) -> startSpellingActivity())
                            // 处理用户点击“是”按钮的事件
                        .setNegativeButton("复习一下", (dialog, which) -> dialog.dismiss())
                            // 处理用户点击“否”按钮的事件
                        .show();
            }
            private void startSpellingActivity() {
                // 这里可以启动您的拼写测试Activity，并且可能需要传递当前页的WordDetail列表作为参数
                // 注意：您需要定义一个用于接收拼写结果的接口或使用ActivityResultContracts
                Intent intent = new Intent(getActivity(), SpellingActivity.class);
                // 可能需要序列化WordDetail列表并放入Intent
                startActivity(intent);
            }
        });

        return view;
    }
//                currentPage++; // 增加页码
//                viewModel.setRecommendWords(getContext(), userId, currentPage); // 请求下一页数据
//
//                // 观察LiveData以处理新获取的数据
//                viewModel.getRecommendWordsLiveData().observe(getViewLifecycleOwner(), new Observer<List<WordDetail>>() {
//                    @Override
//                    public void onChanged(List<WordDetail> newWordDetails) {
//                        if (newWordDetails != null) {
//                            // 创建新的Fragment列表
//                            List<Fragment> additionalFragments = new ArrayList<>();
//                            for (WordDetail detail : newWordDetails) {
//                                additionalFragments.add(ReadTestPagerFragment.newInstance(detail.getParaphrasePicture(), detail.getWord(), detail.getParaphrase()));
//                            }
//
//                            // 将新Fragment添加到现有Adapter的Fragment列表中
//                            pagerAdapter.addFragments(additionalFragments);
//                            pagerAdapter.updateData(newWordDetails);
//
//                            // 更新ViewPager的当前项，确保在正确的页面上
//                            int nextPosition =currentPage*4 ;
//                            viewPager.setCurrentItem(nextPosition, true);
//
//                            // 更新按钮状态和页面编号
//                            updateButtonVisibility(nextPosition);
//
//                            updatePageNumber(nextPosition);
////                            pagerAdapter.notifyDataSetChanged(); // 通知数据集变更
//                        }
//                    }
//               });

    // 在 ViewModel 数据变化时更新 ViewPager
    private void updateViewPagerWithWords(List<WordDetail> wordDetails) {
        List<Fragment> updatedFragments  = new ArrayList<>();
        for (WordDetail wordDetail : wordDetails) {
            // 假设 ReadTestPagerFragment 有一个接受 WordDetail 构造函数
            updatedFragments .add(ReadTestPagerFragment.newInstance(wordDetail.getParaphrasePicture(), wordDetail.getWord(), wordDetail.getParaphrase()));
        }

        pagerAdapter = new ReadTestPagerAdapter(getChildFragmentManager(), updatedFragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0, false); // 设置初始页面
        updatePageNumber(0);
    }

    private void nextPage() {
        int nextItem = viewPager.getCurrentItem() + 1;
        if (nextItem < pagerAdapter.getCount()) {
            viewPager.setCurrentItem(nextItem, true);
        }
    }

    private void prevPage() {
        int prevItem = viewPager.getCurrentItem() - 1;
        if (prevItem >= 0) {
            viewPager.setCurrentItem(prevItem, true);
        }
    }

    private void updatePageNumber(int position) {
        int total=currentPage*4+4;
        pageNumberTextView.setText((position + 1) + "/"+total);
    }

    private void updateButtonVisibility(int position) {
        backButton.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        nextButton.setVisibility(position == pagerAdapter.getCount() - 1 ? View.INVISIBLE : View.VISIBLE);
        moreButton.setVisibility(position == pagerAdapter.getCount() - 1 ? View.VISIBLE : View.GONE);
    }
}
