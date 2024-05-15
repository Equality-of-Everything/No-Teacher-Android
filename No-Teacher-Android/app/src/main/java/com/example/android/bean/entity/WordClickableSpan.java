package com.example.android.bean.entity;

import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.android.api.DictionaryAPI;
import com.example.android.http.RetrofitClient;
import com.example.android.util.TextTranslator;
import com.example.android.util.TtsUtil;
import com.example.no_teacher_andorid.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/11 14:23
 * @Decription:
 */
public class WordClickableSpan extends ClickableSpan {

    private WordInfo wordInfo;

    public WordClickableSpan(WordInfo wordInfo) {
        this.wordInfo = wordInfo;
    }

    @Override
    public void onClick(@NonNull View widget) {
        if (wordInfo == null) {
            Toast.makeText(widget.getContext(), "Word information is not available", Toast.LENGTH_SHORT).show();
            return;
        }

        String clickedWord = wordInfo.getWord();

        // 显示 BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(widget.getContext());
        bottomSheetDialog.setContentView(R.layout.dialog_bottom_word);
        TextView textViewWord = bottomSheetDialog.findViewById(R.id.text_view_word);
        TextView phoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic);

        if (textViewWord != null) {
            textViewWord.setText(clickedWord);
        } else {
            Toast.makeText(widget.getContext(), "Failed to find text view for word", Toast.LENGTH_SHORT).show();
            return;
        }

        // 获取按钮并设置点击事件
        Button ukButton = bottomSheetDialog.findViewById(R.id.uk_button);
        Button enButton = bottomSheetDialog.findViewById(R.id.en_button);
        if (ukButton != null && enButton != null) {
            TtsUtil.getTts(clickedWord, ukButton, enButton);
        } else {
            Toast.makeText(widget.getContext(), "Failed to find pronunciation buttons", Toast.LENGTH_SHORT).show();
            return;
        }

        //  API 获取单词音标
        TextTranslator textTranslator = new TextTranslator(clickedWord);
        Call<TextRes> call = textTranslator.getCall("en", "zh");

        call.enqueue(new Callback<TextRes>() {
            @Override
            public void onResponse(Call<TextRes> call, Response<TextRes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TextRes textRes = response.body();
                    if (phoneticTextView != null && textRes.getBasic() != null) {
                        phoneticTextView.setText(textRes.getBasic().getPhonetic());
                    } else {
                        Toast.makeText(widget.getContext(), "Failed to find phonetic text view or phonetic data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(widget.getContext(), "未找到该单词的音标", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TextRes> call, Throwable t) {
                Toast.makeText(widget.getContext(), "请求失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetDialog.show();
    }

//    @Override
//    public void onClick(@NonNull View widget) {
//        String clickedWord = wordInfo.getWord();
//
//        // 显示 BottomSheetDialog
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(widget.getContext());
//        bottomSheetDialog.setContentView(R.layout.dialog_bottom_word);
//        TextView textViewWord = bottomSheetDialog.findViewById(R.id.text_view_word);
//        TextView britishPhoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic2);
//        TextView americanPhoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic1);
//        TextView definitionTextView = bottomSheetDialog.findViewById(R.id.text_view_definition);
//
//        if (textViewWord != null) {
//            textViewWord.setText(clickedWord);
//        }
//
//        // 获取按钮并设置点击事件
//        Button ukButton = bottomSheetDialog.findViewById(R.id.uk_button);
//        Button enButton = bottomSheetDialog.findViewById(R.id.en_button);
//        if (ukButton != null && enButton != null) {
//            TtsUtil.getTts(clickedWord, ukButton, enButton);
//        }
//
//        // 使用 Retrofit 获取单词详情
//        DictionaryAPI api = RetrofitClient.getDictionaryAPI();
//        Call<List<Words>> call = api.getWordDetails(clickedWord);
//
//        call.enqueue(new Callback<List<Words>>() {
//            @Override
//            public void onResponse(Call<List<Words>> call, Response<List<Words>> response) {
//                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
//                    Words wordDetails = response.body().get(0);
//                    if (britishPhoneticTextView != null) britishPhoneticTextView.setText(wordDetails.getPhonetic());
//                    if (americanPhoneticTextView != null) americanPhoneticTextView.setText(wordDetails.getPhonetic());
//                    if (definitionTextView != null) {
//                        StringBuilder definitions = new StringBuilder();
//                        for (Words.MeaningsDTO meaning : wordDetails.getMeanings()) {
//                            definitions.append(meaning.getPartOfSpeech()).append("\n");
//                            for (Words.MeaningsDTO.DefinitionsDTO definition : meaning.getDefinitions()) {
//                                definitions.append(definition.getDefinition()).append("\n");
//                            }
//                        }
//                        definitionTextView.setText(definitions.toString());
//                    }
//                } else {
//                    Toast.makeText(widget.getContext(), "未找到该单词的详细信息", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Words>> call, Throwable t) {
//                Toast.makeText(widget.getContext(), "请求失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        bottomSheetDialog.show();
//    }
}