package com.example.android.bean.entity;

import java.util.ArrayList;
import java.util.List;
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
        for (int i = 0; i <= 6; i++) {
            Source source = new Source();
            source.setBadCount(r.nextInt(100));
            source.setGoodCount(r.nextInt(100));
            source.setOtherCount(r.nextInt(100));
            source.setScale(r.nextInt(100));
            source.setSource("品类" + i);
            source.setAllCount(source.getBadCount() + source.getGoodCount() + source.getOtherCount());
            list.add(source);
        }
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