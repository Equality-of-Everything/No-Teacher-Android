package com.example.android.adapter;

import com.example.android.bean.entity.CalendarCellView;

import java.util.Calendar;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/24 17:11
 * @Decription:
 */
public abstract class DayViewAdapter {

    /**
     * This method is called to create the cell view.
     *
     * @param parent The parent view to which the cell view will be added.
     */
    public abstract void makeCellView(CalendarCellView parent);

    /**
     * This method is called to bind the data to the cell view.
     *
     * @param view     The cell view to bind data to.
     * @param calendar The calendar representing the date of the cell view.
     */
    public abstract void bindDayView(CalendarCellView view, Calendar calendar);
}

