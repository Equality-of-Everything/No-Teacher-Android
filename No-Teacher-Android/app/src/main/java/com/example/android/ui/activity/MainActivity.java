package com.example.android.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.android.adapter.CustomViewPager;
import com.example.android.adapter.FragmentAdapter;
import com.example.no_teacher_andorid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private CustomViewPager mViewPager;
    private BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

//        /**
//         *改变系统导航颜色
//         */
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
    }


    public void initView() {
        // BottomNavigationView
        navigation = findViewById(R.id.nav_view);
        // 去除背景底色
        navigation.setItemIconTintList(null);
        // 实例化adapter，得到fragment
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewpager);
        // 建立连接
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setSwipeEnabled(false);//所有的fragment都不能左右滑动切换

        setNavigation();
    }

    /**
     * 设置底部导航栏
     */
    public void setNavigation() {

        // 底部导航栏点击事件
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                resetIcon();
                switchMenu(item);
                return true;
            }
        });

        //viewpager监听事件，当viewpager滑动时得到对应的fragment碎片
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
                switchMenu(navigation.getMenu().getItem(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    /**
     * 导航栏切换方法
     */
    private void switchMenu(MenuItem item){
        switch (item.getItemId()) {
            case R.id.navigation_a:
                item.setIcon(R.drawable.email);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.navigation_b:
                mViewPager.setCurrentItem(1);
                item.setIcon(R.drawable.email);
                break;
            case R.id.navigation_c:
                mViewPager.setCurrentItem(2);
                item.setIcon(R.drawable.email);
                break;
            case R.id.navigation_d:
                mViewPager.setCurrentItem(3);
                item.setIcon(R.drawable.mine);
                break;
            default:
        }
    }
}