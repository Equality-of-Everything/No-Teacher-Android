package com.example.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

//        userId = TokenManager.getUserId(getContext());
        userId="9c6a1c47-da2e-4aec-adc9-a492d5861986";
        //设置推荐单词的请求参数
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

        viewPager = view.findViewById(R.id.view_pager);
        nextButton = view.findViewById(R.id.next_button);
        backButton = view.findViewById(R.id.back_button);
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

        return view;
    }

    // 在 ViewModel 数据变化时更新 ViewPager
    private void updateViewPagerWithWords(List<WordDetail> wordDetails) {
        List<Fragment> dynamicFragments = new ArrayList<>();
        for (WordDetail wordDetail : wordDetails) {
            // 假设 ReadTestPagerFragment 有一个接受 WordDetail 构造函数
            dynamicFragments.add(ReadTestPagerFragment.newInstance(wordDetail.getParaphrasePicture(), wordDetail.getWord(), wordDetail.getParaphrase()));
        }

        pagerAdapter = new ReadTestPagerAdapter(getChildFragmentManager(), dynamicFragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0, false); // 设置初始页面
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
        pageNumberTextView.setText((position + 1) + "/4");
    }

    private void updateButtonVisibility(int position) {
        backButton.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        nextButton.setVisibility(position == pagerAdapter.getCount() - 1 ? View.INVISIBLE : View.VISIBLE);
    }
}
