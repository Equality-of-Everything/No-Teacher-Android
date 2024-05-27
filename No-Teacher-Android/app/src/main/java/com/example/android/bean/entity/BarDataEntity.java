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

    public List<Type> parseData(){
        typeList = new ArrayList<>();

        // 定义三个固定的品类及其比例
        Type type1 = new Type();
        type1.setTypeName("优秀");
        type1.setTypeScale(150);
        typeList.add(type1);

        Type type2 = new Type();
        type2.setTypeName("良好");
        type2.setTypeScale(60);
        typeList.add(type2);

        Type type3 = new Type();
        type3.setTypeName("一般");
        type3.setTypeScale(90);
        typeList.add(type3);

        return typeList;
    }
}

//public class BarDataEntity implements Serializable {
//    private List<Type> typeList;
//
//    public List<Type> getTypeList() {
//        return typeList;
//    }
//
//    public static class Type implements Serializable {
//        private String typeName;//类型名称
//        private int sale;//销量
//        private double typeScale;//类型占比
//
//        public String getTypeName() {
//            return typeName;
//        }
//
//        public void setTypeName(String typeName) {
//            this.typeName = typeName;
//        }
//
//        public int getSale() {
//            return sale;
//        }
//
//        public void setSale(int sale) {
//            this.sale = sale;
//        }
//
//        public double getTypeScale() {
//            return typeScale;
//        }
//
//        public void setTypeScale(double typeScale) {
//            this.typeScale = typeScale;
//        }
//    }
//
//    public List<Type> parseData(){
//        typeList = new ArrayList<>();
//        Random r = new Random();
//        int all=0;
//        for (int i= 0;i<=6;i++){
//            Type type = new Type();
//            type.setSale(r.nextInt(100));
//            type.setTypeName("品类" + i);
//            typeList.add(type);
//        }
//        for (int i= 0;i<=6;i++){
//            all+= typeList.get(i).getSale();
//        }
//        for (int i= 0;i<=6;i++){
//            double typeScale = (double) typeList.get(i).getSale()/all;
//            typeList.get(i).setTypeScale(typeScale);
//            System.out.println("==>"+typeList.get(i).getTypeScale());
//        }
//        return typeList;
//    }
//}

