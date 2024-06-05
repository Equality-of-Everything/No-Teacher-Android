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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bean.entity.WordDetail;
import com.example.android.fragment.BFragment;
import com.example.android.util.ToastManager;
import com.example.android.util.TtsUtil;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;

public class SpellingActivity extends AppCompatActivity {

    private EditText editTextWord;
    private Button buttonPlayAudio;
    private Button buttonCheck;

    private Button btnFin;//实验按钮
    private TextView textViewMeaning;

    private TextView textViewCorrectWord;
    private TextView meaning;
    private String correctWord;
    private int currentWordIndex = 0;

    private boolean isNextWordMode = false;
    private List<String> WordList = new ArrayList<>();
    private List<String> ParaphraseList= new ArrayList<>();

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
        meaning = findViewById(R.id.text_mean);
        btnFin  = findViewById(R.id.finish);

        //        nextButton = findViewById(R.id.button3);
        buttonCheck.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        if (getIntent()!=null){
            ArrayList<String> wordList = (ArrayList<String>) getIntent().getSerializableExtra("WORDS_LIST");
            ArrayList<String> meaningList = (ArrayList<String>) getIntent().getSerializableExtra("MEANING_LIST");
            if (meaningList != null && !meaningList.isEmpty()) {
                // 成功接收到数据，现在可以遍历或使用receivedWords列表
                ParaphraseList.clear();
                for (String meaning : meaningList) {
                    // 将单词和释义分别添加到列表中
                    ParaphraseList.add(meaning);
                    Log.d("DDDDDDDDDDD", String.valueOf(ParaphraseList));
                }
            } else {
                // 没有接收到数据或列表为空，处理这种情况
            }
            if (wordList != null && !wordList.isEmpty()) {
                // 成功接收到数据，现在可以遍历或使用receivedWords列表
                WordList.clear();

                for (String word : wordList) {
                    // 将单词和释义分别添加到列表中
                    WordList.add(word);
                    Log.d("DDDDDDDDDDD", String.valueOf(WordList));
                }
            } else {
                // 没有接收到数据或列表为空，处理这种情况
                Toast.makeText(this, "没有接收到单词数据", Toast.LENGTH_SHORT).show();
            }
        }else {
            // Intent为空，处理异常情况
            Toast.makeText(this, "Intent数据为空", Toast.LENGTH_SHORT).show();
        }
        correctWord = WordList.get(0);
        meaning.setText(ParaphraseList.get(0));
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
        String word = WordList.get(currentWordIndex);
        return word;
    }
    private String getNextParaphrase() {
        String paraphrase = ParaphraseList.get(currentWordIndex);
        return paraphrase;
    }

    private void getNextWordAndUpdateUI() {
        currentWordIndex++;
        correctWord = getNextWord();
        meaning.setText(getNextParaphrase());
        editTextWord.setText("");
        editTextWord.setBackgroundColor(Color.TRANSPARENT);
        textViewCorrectWord.setVisibility(View.GONE);
//        buttonCheck.setText("检查");
        buttonCheck.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
        isNextWordMode = false;
        System.out.println("next" + correctWord);
    }



}