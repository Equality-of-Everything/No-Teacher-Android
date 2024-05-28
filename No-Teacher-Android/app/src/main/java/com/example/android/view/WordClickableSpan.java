package com.example.android.view;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.android.adapter.ExamplesAdapter;
import com.example.android.api.DictionaryAPI;
import com.example.android.bean.entity.ExampleItem;
import com.example.android.bean.entity.TextRes;
import com.example.android.bean.entity.WordInfo;
import com.example.android.bean.entity.Words;
import com.example.android.http.RetrofitClient;
import com.example.android.util.TextTranslator;
import com.example.android.util.ToastManager;
import com.example.android.util.TtsUtil;
import com.example.no_teacher_andorid.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
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
        TextView translatedTextView = bottomSheetDialog.findViewById(R.id.text_view_translated_word);
        TextView britishPhoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic2);
        TextView americanPhoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic1);
        ListView examplesListView = bottomSheetDialog.findViewById(R.id.list_view_examples);

        if (textViewWord != null) {
            textViewWord.setText(clickedWord);
        }

        // 获取按钮并设置点击事件
        Button ukButton = bottomSheetDialog.findViewById(R.id.uk_button);
        Button enButton = bottomSheetDialog.findViewById(R.id.en_button);
        if (ukButton != null && enButton != null) {
            TtsUtil.getTts1(clickedWord, ukButton);
            TtsUtil.getTts2(clickedWord, enButton);
        }

        // 获取 BottomSheetBehavior 并设置回调
        View bottomSheetInternal = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheetInternal);

        // 禁用拖动行为
        behavior.setDraggable(false);


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

                    // Collect all examples
                    List<ExampleItem> exampleItems = new ArrayList<>();
                    for (Words.MeaningsDTO meaning : wordDetails.getMeanings()) {
                        for (Words.MeaningsDTO.DefinitionsDTO definition : meaning.getDefinitions()) {
                            String partOfSpeech = "词性：" + meaning.getPartOfSpeech();
                            String definitionText = "定义：" + definition.getDefinition();
                            String example = definition.getExample();
                            if (example != null) {
                                exampleItems.add(new ExampleItem(partOfSpeech, definitionText, example));
                            }
                        }
                    }

                    // Set examples to ListView
                    ExamplesAdapter adapter = new ExamplesAdapter(widget.getContext(), exampleItems);
                    examplesListView.setAdapter(adapter);
                    // Translate examples
                    translateExamples(adapter, translatedTextView);

                    // Translate the clickedWord
                    TextTranslator translator = new TextTranslator(clickedWord);
                    Call<TextRes> translationCall = translator.getCall("en", "zh-CHS"); // English to Chinese
                    translationCall.enqueue(new Callback<TextRes>() {
                        @Override
                        public void onResponse(Call<TextRes> call, Response<TextRes> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().getTranslation() != null && !response.body().getTranslation().isEmpty()) {
                                if(response.body().getTranslation()==null) return;
                                String translatedText = response.body().getTranslation().get(0);
                                if (translatedTextView != null) {
                                    translatedTextView.setText(translatedText);
                                }
                            } else {
                                Toast.makeText(widget.getContext(), "翻译失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TextRes> call, Throwable t) {
                            Toast.makeText(widget.getContext(), "翻译请求失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ToastManager.showCustomToast(widget.getContext(), "未找到该单词的详细信息");
//                    Toast.makeText(widget.getContext(), "未找到该单词的详细信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Words>> call, Throwable t) {
                ToastManager.showCustomToast(widget.getContext(), "请求失败：" + t.getMessage());
//                Toast.makeText(widget.getContext(), "请求失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheetDialog.show();
    }

    private void translateExamples(ExamplesAdapter adapter, TextView translatedTextView) {
        if (adapter != null && translatedTextView != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                ExampleItem item = adapter.getItem(i);
                if (item != null) {
                    TextTranslator translator = new TextTranslator(item.getExample());
                    Call<TextRes> translationCall = translator.getCall("en", "zh-CHS"); // English to Chinese
                    translationCall.enqueue(new Callback<TextRes>() {
                        @Override
                        public void onResponse(Call<TextRes> call, Response<TextRes> response) {
                            if (response.isSuccessful() && response.body() != null && response.body().getTranslation() != null && !response.body().getTranslation().isEmpty()) {
                                if(response.body().getTranslation()==null) return;
                                String translatedText = response.body().getTranslation().get(0);
                                item.setTranslatedExample(translatedText);
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(translatedTextView.getContext(), "翻译失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TextRes> call, Throwable t) {
                            Toast.makeText(translatedTextView.getContext(), "翻译请求失败：" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }
}