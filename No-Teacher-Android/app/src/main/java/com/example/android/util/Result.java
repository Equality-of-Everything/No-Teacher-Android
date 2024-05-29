package com.example.android.util;

import com.example.android.bean.entity.Sentence;

import java.util.ArrayList;

public class Result {
    /**
     * 评测语种：en（英文）、cn（中文）
     */
    public String language;
    /**
     * 评测种类：read_syllable（cn单字）、read_word（词语）、read_sentence（句子）
     */
    public String category;
    /**
     * 开始帧位置，每帧相当于10ms
     */
    public int beg_pos;
    /**
     * 结束帧位置
     */
    public int end_pos;
    /**
     * 评测内容
     */
    public String content;
    /**
     * 总得分
     */
    public float total_score;
    /**
     * 时长（cn）
     */
    public int time_len;
    /**
     * 异常信息（en）
     */
    public String except_info;
    /**
     * 是否乱读（cn）
     */
    public boolean is_rejected;
    /**
     * 流利度分
     */
    public float fluency_score;
    /**
     * 准确度分
     */
    public float accuracy_score;
    /**
     * 标准度分
     */
    public float standard_score;
    /**
     * 完整度分
     */
    public float integrity_score;
    /**
     * xml结果中的sentence标签
     */
    public ArrayList<Sentence> sentences;
}
