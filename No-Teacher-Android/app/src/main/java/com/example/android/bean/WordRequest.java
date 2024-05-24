package com.example.android.bean;

public class WordRequest {
    private String userId;

    private int currentPage;

    public WordRequest(String userId, int currentPage) {
        this.userId = userId;
        this.currentPage = currentPage;
    }

    public WordRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
