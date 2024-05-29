package com.example.android.bean.entity;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.android.ui.activity.ReadActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/11 14:20
 * @Decription:
 */
public class ReaderPage extends AppCompatTextView {
    private SpannableString mSpannableString;
    private List<WordInfo> mWordInfos;
    private ReadActivity readActivity;

    public ReaderPage(Context context) {
        super(context);
        initialize();
    }

    public ReaderPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ReaderPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public void setReadActivity(ReadActivity readActivity) {
        this.readActivity = readActivity;
    }


    private void initialize() {
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setContent(String content) {
        mSpannableString = new SpannableString(Html.fromHtml(content));
        mWordInfos = getWordInfos(content);
        setClickableSpans();
        setText(mSpannableString);
    }

    private List<String> splitWord(@NonNull String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("[a-zA-Z-']+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }

    private List<WordInfo> getWordInfos(String text) {
        List<String> words = splitWord(text);
        List<WordInfo> wordInfos = new ArrayList<>(words.size());
        int startIndex = 0;
        for (String word : words) {
            int start = text.indexOf(word, startIndex);
            int end = start + word.length();
            startIndex = end;
            WordInfo wordInfo = new WordInfo();
            wordInfo.setStart(start);
            wordInfo.setEnd(end);
            wordInfo.setWord(word);
            wordInfos.add(wordInfo);
        }
        return wordInfos;
    }

    private void setClickableSpans() {
        for (WordInfo info : mWordInfos) {
            mSpannableString.setSpan(new WordClickableSpan(info, getContext(),readActivity),
                    info.getStart(), info.getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}