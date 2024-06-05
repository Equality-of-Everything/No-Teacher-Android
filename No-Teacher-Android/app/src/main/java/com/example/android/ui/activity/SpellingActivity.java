package com.example.android.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.fragment.BFragment;
import com.example.android.util.ToastManager;
import com.example.android.util.TtsUtil;
import com.example.no_teacher_andorid.R;

import java.util.Random;

public class SpellingActivity extends AppCompatActivity {

    private EditText editTextWord;
    private Button buttonPlayAudio;
    private Button buttonCheck;

    private Button btnFin;//实验按钮
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
        btnFin  = findViewById(R.id.finish);

        correctWord = getNextWord(); // 初始化第一个单词
//        nextButton = findViewById(R.id.button3);

        buttonCheck.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);

        // 设置单词意思
        textViewMeaning.setText(meaning);

        // 检查单词按钮点击事件
        buttonCheck.setOnClickListener(v -> {
            if (isNextWordMode) {
                getNextWordAndUpdateUI();
                playAudio();//刷新音频
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

        //实验完成按钮
        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置返回数据
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                // 结束当前活动，返回到上一个活动
                finish();
            }
        });

    }

    private void playAudio() {
        if (buttonPlayAudio != null) {
            TtsUtil.getTts1(correctWord, buttonPlayAudio);
        }
    }

    private void checkWord() {
        String userWord = editTextWord.getText().toString().trim();
        if (TextUtils.isEmpty(userWord)) {
            ToastManager.showCustomToast(this, "请输入单词");
//            Toast.makeText(this, "请输入单词", Toast.LENGTH_SHORT).show();
        } else if (userWord.equalsIgnoreCase(correctWord)) {
            ToastManager.showCustomToast(this, "正确");
//            Toast.makeText(this, "正确!", Toast.LENGTH_SHORT).show();
            editTextWord.setBackgroundColor(Color.TRANSPARENT);
            textViewCorrectWord.setVisibility(View.GONE);
//            buttonCheck.setText("下一个");
            buttonCheck.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.next, 0, 0, 0);
            isNextWordMode = true;
        } else {ToastManager.showCustomToast(this, "错误");
//            Toast.makeText(this, "错误，请再试一次!", Toast.LENGTH_SHORT).show();
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