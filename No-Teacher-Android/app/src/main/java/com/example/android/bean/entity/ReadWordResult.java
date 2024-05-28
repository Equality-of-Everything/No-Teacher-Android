package com.example.android.bean.entity;

import com.example.android.util.Result;

public class ReadWordResult extends Result {

    public ReadWordResult() {
        category = "read_word";
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        if ("cn".equals(language)) {
            buffer.append("[总体结果]\n")
                    .append("评测内容：" + content + "\n")
                    .append("朗读时长：" + time_len + "\n")
                    .append("总分：" + total_score + "\n\n")
                    .append("[朗读详情]")
                    .append(ResultFormatUtil.formatDetails_CN(sentences));
        } else {
            if (is_rejected) {
                buffer.append("检测到乱读，")
                        .append("except_info:" + except_info + "\n\n");	// except_info代码说明详见《语音评测参数、结果说明文档》
            }

            buffer.append("[总体结果]\n")
                    .append("评测内容：" + content + "\n")
                    .append("总分：" + total_score + "\n\n")
                    .append("[朗读详情]")
                    .append(ResultFormatUtil.formatDetails_EN(sentences));
        }

        return buffer.toString();
    }
}

