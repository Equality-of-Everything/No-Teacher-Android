package com.example.android.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.util.TtsUtil;
import com.example.no_teacher_andorid.R;

import java.util.Random;

public class SpellingActivity extends AppCompatActivity {

    private EditText editTextWord;
    private Button buttonPlayAudio;
    private Button buttonCheck;
    private TextView textViewMeaning;

    private TextView textViewCorrectWord;

    private String correctWord;

    private boolean isNextWordMode = false;
    private String meaning = "This is an example word."; // 替换为单词的意思

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling);

        editTextWord = findViewById(R.id.edit_word);
        buttonPlayAudio = findViewById(R.id.button1);
        buttonCheck = findViewById(R.id.button2);
        textViewMeaning = findViewById(R.id.text_mean);
        textViewCorrectWord = findViewById(R.id.textViewCorrectWord);
        correctWord = getNextWord(); // 初始化第一个单词
//        nextButton = findViewById(R.id.button3);

        buttonCheck.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);

        // 设置单词意思
        textViewMeaning.setText(meaning);

        // 检查单词按钮点击事件
        buttonCheck.setOnClickListener(v -> {
            if (isNextWordMode) {
                getNextWordAndUpdateUI();
                playAudio();
            } else {
                checkWord();
            }
        });

        // 播放音频按钮点击事件
        buttonPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
    }

    private void playAudio() {
        if (buttonPlayAudio != null) {
            TtsUtil.getTts1(correctWord, buttonPlayAudio);
            Toast.makeText(this, "当前单词是: " + correctWord, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkWord() {
        String userWord = editTextWord.getText().toString().trim();
        if (TextUtils.isEmpty(userWord)) {
            Toast.makeText(this, "请输入单词", Toast.LENGTH_SHORT).show();
        } else if (userWord.equalsIgnoreCase(correctWord)) {
            Toast.makeText(this, "正确!", Toast.LENGTH_SHORT).show();
            editTextWord.setBackgroundColor(Color.TRANSPARENT);
            textViewCorrectWord.setVisibility(View.GONE);
//            buttonCheck.setText("下一个");
            buttonCheck.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.next, 0, 0, 0);
            isNextWordMode = true;
        } else {
            Toast.makeText(this, "错误，请再试一次!", Toast.LENGTH_SHORT).show();
            editTextWord.setBackgroundColor(Color.parseColor("#ffb5a0"));
            textViewCorrectWord.setText("正确的单词是: " + correctWord);
            textViewCorrectWord.setVisibility(View.VISIBLE);
        }
    }

    private String getNextWord() {
        String[] wordList = {"apple", "banana", "orange", "grape", "watermelon"};
        Random random = new Random();
        int index = random.nextInt(wordList.length);
        return wordList[index];
    }

    private void getNextWordAndUpdateUI() {
        correctWord = getNextWord();
        editTextWord.setText("");
        editTextWord.setBackgroundColor(Color.TRANSPARENT);
        textViewCorrectWord.setVisibility(View.GONE);
//        buttonCheck.setText("检查");
        buttonCheck.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        isNextWordMode = false;
        System.out.println("next" + correctWord);
    }



}