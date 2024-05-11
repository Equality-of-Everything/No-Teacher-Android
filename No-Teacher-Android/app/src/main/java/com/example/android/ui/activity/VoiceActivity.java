package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.adapter.ReadTestPagerAdapter;
import com.example.android.fragment.ReadTestPagerFragment;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;
import java.util.List;

public class VoiceActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button nextButton;
    private Button backButton;
    private TextView pageNumberTextView;
    private ReadTestPagerAdapter pagerAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);



        viewPager = findViewById(R.id.view_pager);
        nextButton = findViewById(R.id.next_button);
        backButton = findViewById(R.id.back_button);
        pageNumberTextView = findViewById(R.id.page_number_text_view);


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ReadTestPagerFragment.newInstance(R.drawable.img_1, "Angry!","100"));
        fragments.add(ReadTestPagerFragment.newInstance(R.drawable.img_2, "Sleep!","90"));
        fragments.add(ReadTestPagerFragment.newInstance(R.drawable.img_4, "I don not want to work!","80"));

        pagerAdapter = new ReadTestPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

        nextButton.setOnClickListener(v -> nextPage());
        backButton.setOnClickListener(v -> prevPage());

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updatePageNumber(position);
                updateButtonVisibility(position);
            }
        });
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
        pageNumberTextView.setText( (position + 1) + "/3");
    }

    private void updateButtonVisibility(int position) {
        backButton.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        nextButton.setVisibility(position == pagerAdapter.getCount() - 1 ? View.INVISIBLE : View.VISIBLE);
    }

}