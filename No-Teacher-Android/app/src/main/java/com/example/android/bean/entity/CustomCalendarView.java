package com.example.android.bean.entity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.android.adapter.DayViewAdapter;
import com.example.no_teacher_andorid.R;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/24 17:08
 * @Decription:
 */
public class CustomCalendarView extends CalendarView {

    private Set<Long> checkedInDates = new HashSet<>();
    private Drawable checkedInDrawable;
    private DayViewAdapter mDayViewAdapter;

    public CustomCalendarView(Context context) {
        super(context);
        init(context);
    }

    public CustomCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        checkedInDrawable = context.getResources().getDrawable(R.drawable.checked_in_background); // 假设已签到背景图
        setDayViewAdapter(new DayViewAdapter() {
            @Override
            public void makeCellView(CalendarCellView parent) {
                TextView dayOfMonth = new TextView(getContext());
                dayOfMonth.setGravity(Gravity.CENTER);
                parent.addView(dayOfMonth);
                parent.setDayOfMonthTextView(dayOfMonth);
            }

            @Override
            public void bindDayView(CalendarCellView view, Calendar calendar) {
                TextView dayOfMonthTextView = view.getDayOfMonthTextView();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                long dateInMillis = calendar.getTimeInMillis();
                if (checkedInDates.contains(dateInMillis)) {
                    view.setBackgroundDrawable(checkedInDrawable);
                } else {
                    view.setBackgroundDrawable(null);
                }
                dayOfMonthTextView.setText(String.valueOf(day));
            }
        });
    }

    private void setDayViewAdapter(DayViewAdapter dayViewAdapter) {
        mDayViewAdapter = dayViewAdapter;
    }



    public void setCheckedInDates(Set<Long> checkedInDates) {
        this.checkedInDates = checkedInDates;
        invalidate();
    }
}
