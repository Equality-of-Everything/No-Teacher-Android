package com.example.android.bean.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/28 9:43
 * @Decription:
 */
public class SourceEntity {
    private List<Source> list;

    public List<Source> getList() {
        return list;
    }

    public void parseData() {
        list = new ArrayList<>();
        Random r = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.getDefault());

        // 生成过去6天的日期
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 6; i++) {
            Source source = new Source();
            source.setBadCount(r.nextInt(100));
            source.setGoodCount(r.nextInt(100));
            source.setOtherCount(r.nextInt(100));
            source.setScale(r.nextInt(100));
            source.setSource(sdf.format(calendar.getTime()));
            source.setAllCount(source.getBadCount() + source.getGoodCount() + source.getOtherCount());
            list.add(source);
            calendar.add(Calendar.DAY_OF_YEAR, -1); // 向前一天
        }

        // 添加“今日”的数据项
        Source todaySource = new Source();
        todaySource.setBadCount(r.nextInt(100));
        todaySource.setGoodCount(r.nextInt(100));
        todaySource.setOtherCount(r.nextInt(100));
        todaySource.setScale(r.nextInt(100));
        todaySource.setSource("今日");
        todaySource.setAllCount(todaySource.getBadCount() + todaySource.getGoodCount() + todaySource.getOtherCount());
        list.add(todaySource);
    }

    public static class Source {
        private String source;
        private int badCount;
        private int goodCount;
        private int otherCount;
        private int allCount;
        private int scale;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getBadCount() {
            return badCount;
        }

        public void setBadCount(int badCount) {
            this.badCount = badCount;
        }

        public int getGoodCount() {
            return goodCount;
        }

        public void setGoodCount(int goodCount) {
            this.goodCount = goodCount;
        }

        public int getOtherCount() {
            return otherCount;
        }

        public void setOtherCount(int otherCount) {
            this.otherCount = otherCount;
        }

        public int getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }
    }
}