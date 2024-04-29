package com.example.android.bean.entity;

public class Word {
    String word;
    String definition;
    Boolean is_known;

    public Word() {
    }

    public Word(String word, String definition, Boolean is_known) {
        this.word = word;
        this.definition = definition;
        this.is_known = false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Boolean getIs_known() {
        return is_known;
    }

    public void setIs_known(Boolean is_known) {
        this.is_known = is_known;
    }
}
