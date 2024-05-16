package com.example.android.bean.entity;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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
    private Context context;
    public WordClickableSpan(WordInfo wordInfo, Context context) {
        this.wordInfo = wordInfo;
        this.context = context;
    }

    //修改页面字体颜色，去除下划线
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(ContextCompat.getColor(context, R.color.word_clickable_color)); // Set the desired text color
        ds.setUnderlineText(false); // Remove underline
    }



    @Override
    public void onClick(@NonNull View widget) {
        String clickedWord = wordInfo.getWord();

        // 显示 BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(widget.getContext());
        bottomSheetDialog.setContentView(R.layout.dialog_bottom_word);
        TextView textViewWord = bottomSheetDialog.findViewById(R.id.text_view_word);
        TextView britishPhoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic2);
        TextView americanPhoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic1);
        TextView definitionTextView = bottomSheetDialog.findViewById(R.id.text_view_definition);

        if (textViewWord != null) {
            textViewWord.setText(clickedWord);
        }

        // 获取按钮并设置点击事件
        Button ukButton = bottomSheetDialog.findViewById(R.id.uk_button);
        Button enButton = bottomSheetDialog.findViewById(R.id.en_button);
        if (ukButton != null && enButton != null) {
            TtsUtil.getTts1(clickedWord, ukButton);
            TtsUtil.getTts2(clickedWord,enButton);
        }

        // 使用 Retrofit 获取单词详情
        DictionaryAPI api = RetrofitClient.getDictionaryAPI();
        Call<List<Words>> call = api.getWordDetails(clickedWord);

        call.enqueue(new Callback<List<Words>>() {
            @Override
            public void onResponse(Call<List<Words>> call, Response<List<Words>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Words wordDetails = response.body().get(0);

                    // 设置音标
                    List<Words.PhoneticsDTO> phonetics = wordDetails.getPhonetics();
                    if (phonetics != null && !phonetics.isEmpty()) {
                        if (phonetics.size() > 0 && americanPhoneticTextView != null) {
                            americanPhoneticTextView.setText(phonetics.get(0).getText());
                        }
                        if (phonetics.size() > 1 && britishPhoneticTextView != null) {
                            britishPhoneticTextView.setText(phonetics.get(1).getText());
                        }
                    }

                    // 设置释义
                    if (definitionTextView != null) {
                        StringBuilder definitions = new StringBuilder();
                        for (Words.MeaningsDTO meaning : wordDetails.getMeanings()) {
                            definitions.append(meaning.getPartOfSpeech()).append("\n");
                            for (Words.MeaningsDTO.DefinitionsDTO definition : meaning.getDefinitions()) {
                                definitions.append(definition.getDefinition()).append("\n");
                            }
                        }
                        definitionTextView.setText(definitions.toString());
                    }
                } else {
                    Toast.makeText(widget.getContext(), "未找到该单词的详细信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Words>> call, Throwable t) {
                Toast.makeText(widget.getContext(), "请求失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheetDialog.show();
    }





    //    @Override
//    public void onClick(@NonNull View widget) {
//        if (wordInfo == null) {
//            Toast.makeText(widget.getContext(), "Word information is not available", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String clickedWord = wordInfo.getWord();
//
//        // 显示 BottomSheetDialog
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(widget.getContext());
//        bottomSheetDialog.setContentView(R.layout.dialog_bottom_word);
//        TextView textViewWord = bottomSheetDialog.findViewById(R.id.text_view_word);
//        TextView phoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic);
//
//        if (textViewWord != null) {
//            textViewWord.setText(clickedWord);
//        } else {
//            Toast.makeText(widget.getContext(), "Failed to find text view for word", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // 获取按钮并设置点击事件
//        Button ukButton = bottomSheetDialog.findViewById(R.id.uk_button);
//        Button enButton = bottomSheetDialog.findViewById(R.id.en_button);
//        if (ukButton != null && enButton != null) {
//            TtsUtil.getTts1(clickedWord, ukButton);
//            TtsUtil.getTts2(clickedWord, enButton);
//        } else {
//            Toast.makeText(widget.getContext(), "Failed to find pronunciation buttons", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        //  API 获取单词音标
//        TextTranslator textTranslator = new TextTranslator(clickedWord);
//        Call<TextRes> call = textTranslator.getCall("en", "zh");
//
//        call.enqueue(new Callback<TextRes>() {
//            @Override
//            public void onResponse(Call<TextRes> call, Response<TextRes> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    TextRes textRes = response.body();
//                    if (phoneticTextView != null && textRes.getBasic() != null) {
//                        phoneticTextView.setText(textRes.getBasic().getPhonetic());
//                    } else {
//                        Toast.makeText(widget.getContext(), "Failed to find phonetic text view or phonetic data", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(widget.getContext(), "未找到该单词的音标", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TextRes> call, Throwable t) {
//                Toast.makeText(widget.getContext(), "请求失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        bottomSheetDialog.show();
//    }
}