package com.example.android.bean.entity;

import androidx.lifecycle.LiveData;

import java.sql.Timestamp;

/**
 * @Author : zhang
 * @Date : Created in 2024/6/5 14:24
 * @Decription :
 */


public class WordDetailRecording {
    private String id;

    private String userId;

    private int wordId;

    private int score;

    private Timestamp time;

    public WordDetailRecording(LiveData<String> id, String userId, LiveData<Integer> wordId, LiveData<Integer> score, LiveData<Timestamp> time) {

    }

    @Override
    public String toString() {
        return "WordDetailRecording{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", wordId=" + wordId +
                ", score=" + score +
                ", time=" + time +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public WordDetailRecording(String id, String userId, int wordId, int score, Timestamp time) {
        this.id = id;
        this.userId = userId;
        this.wordId = wordId;
        this.score = score;
        this.time = time;
    }
}
