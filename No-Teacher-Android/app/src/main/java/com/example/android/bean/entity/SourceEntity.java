package com.example.android.bean.entity;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

    private SimpleDateFormat sdf;

    public void parseData(List<WordDetailRecording> wordDetailRecordings) {
        list = new ArrayList<>();
        Random r = new Random();
        sdf = new SimpleDateFormat("MM-dd", Locale.getDefault());

        // 生成过去6天的日期，最后一个日期为今日
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -wordDetailRecordings.size()); // 向前6天

        for (int i = 0; i < wordDetailRecordings.size(); i++) {
            Log.e("EEEEEEEEEEEEEEEEEEEEEE", calendar.getTime().toString());
            Map<String, Integer> dataCount = new HashMap<>();
            dataCount = getDataCount(sdf.format(calendar.getTime()), wordDetailRecordings);
            Source source = new Source();
            source.setBadCount(dataCount.get("bad"));
            source.setGoodCount(dataCount.get("good"));
            source.setOtherCount(dataCount.get("other"));
            source.setScale(r.nextInt(5));
            source.setSource(sdf.format(calendar.getTime()));
            source.setAllCount(source.getBadCount() + source.getGoodCount() + source.getOtherCount());
            list.add(source);
            calendar.add(Calendar.DAY_OF_YEAR, 1); // 向后一天
        }

    }

    private Map<String, Integer> getDataCount(String date, List<WordDetailRecording> wordDetailRecordings) {
        Map<String, Integer> res = new HashMap<>();
        int bad = 0;
        int good = 0;
        int other = 0;
        for (WordDetailRecording wordDetailRecording : wordDetailRecordings) {
            if (wordDetailRecording.getTime().toString().contains(date)) {
                if (0 <= wordDetailRecording.getScore() && wordDetailRecording.getScore() <= 60) {
                    bad++;
                } else if (60 < wordDetailRecording.getScore() && wordDetailRecording.getScore() <= 80) {
                    good++;
                }else{
                    other++;
                }
            }
        }
        res.put("bad", bad);
        res.put("good", good);
        res.put("other", other);
        return res;
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