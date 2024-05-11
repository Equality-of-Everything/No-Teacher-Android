package com.example.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.android.adapter.ReadTestPagerAdapter;
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
    private Button backButton;
    private TextView pageNumberTextView;
    private ReadTestPagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        viewPager = view.findViewById(R.id.view_pager);
        nextButton = view.findViewById(R.id.next_button);
        backButton = view.findViewById(R.id.back_button);
        pageNumberTextView = view.findViewById(R.id.page_number_text_view);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ReadTestPagerFragment.newInstance(R.drawable.img_1, "Angry!", "100"));
        fragments.add(ReadTestPagerFragment.newInstance(R.drawable.img_2, "Sleep!", "90"));
        fragments.add(ReadTestPagerFragment.newInstance(R.drawable.img_4, "I don't want to work!", "80"));

        pagerAdapter = new ReadTestPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

        nextButton.setOnClickListener(v -> nextPage());
        backButton.setOnClickListener(v -> prevPage());

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }

        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updatePageNumber(position);
                updateButtonVisibility(position);
            }
        });

        return view;
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
        pageNumberTextView.setText((position + 1) + "/3");
    }

    private void updateButtonVisibility(int position) {
        backButton.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        nextButton.setVisibility(position == pagerAdapter.getCount() - 1 ? View.INVISIBLE : View.VISIBLE);
    }
}
