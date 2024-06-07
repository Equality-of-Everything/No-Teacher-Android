package com.example.android.bean.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/27 14:45
 * @Decription:
 */

public class BarDataEntity implements Serializable {
    private List<Type> typeList;

    public List<Type> getTypeList() {
        return typeList;
    }

    public static class Type implements Serializable {
        private String typeName; // 类型名称
        private double typeScale; // 类型比例

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public double getTypeScale() {
            return typeScale;
        }

        public void setTypeScale(double typeScale) {
            this.typeScale = typeScale;
        }
    }

    public List<Type> parseData(List<Type> types){
        typeList = new ArrayList<>();

        // 定义三个固定的品类及其比例
//        Type type1 = new Type();
//        type1.setTypeName("优秀");
//        type1.setTypeScale(150);
//        typeList.add(type1);
//
//        Type type2 = new Type();
//        type2.setTypeName("良好");
//        type2.setTypeScale(60);
//        typeList.add(type2);
//
//        Type type3 = new Type();
//        type3.setTypeName("一般");
//        type3.setTypeScale(90);
//        typeList.add(type3);

        typeList.addAll(types);

        return typeList;
    }
}

