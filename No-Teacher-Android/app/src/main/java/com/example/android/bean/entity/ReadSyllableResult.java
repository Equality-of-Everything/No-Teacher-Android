package com.example.android.bean.entity;

import com.example.android.util.Result;
import com.iflytek.ise.result.util.ResultFormatUtil;

public class ReadSyllableResult extends Result {

    public ReadSyllableResult() {
        language = "cn";
        category = "read_syllable";
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[总体结果]\n")
                .append("评测内容：" + content + "\n")
                .append("朗读时长：" + time_len + "\n")
                .append("总分：" + total_score + "\n\n")
                .append("[朗读详情]").append(ResultFormatUtil.formatDetails_CN(sentences));

        return buffer.toString();
    }
}