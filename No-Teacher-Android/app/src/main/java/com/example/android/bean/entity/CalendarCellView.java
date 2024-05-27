package com.example.android.bean.entity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.no_teacher_andorid.R;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/24 17:14
 * @Decription:
 */
public class CalendarCellView extends FrameLayout {

    private TextView dayOfMonthTextView;

    public CalendarCellView(Context context) {
        super(context);
        init(context);
    }

    public CalendarCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_cell_view, this);
        dayOfMonthTextView = findViewById(R.id.dayOfMonthTextView);
    }

    public TextView getDayOfMonthTextView() {
        return dayOfMonthTextView;
    }

    public void setDayOfMonthTextView(TextView dayOfMonthTextView) {
        this.dayOfMonthTextView = dayOfMonthTextView;
    }
}
