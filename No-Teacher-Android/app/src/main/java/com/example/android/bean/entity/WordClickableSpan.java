package com.example.android.bean.entity;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.android.api.YoudaoDictAPI;
import com.example.android.util.DictionaryUtil;
import com.example.android.util.PhoneticsUtil;
import com.example.android.util.TextTranslator;
import com.example.android.util.TtsUtil;
import com.example.no_teacher_andorid.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
    public void onClick(View widget) {
        // 获取被点击的单词
        String clickedWord = wordInfo.getWord();

        // 调用翻译方法获取音标
        translateWord(clickedWord, widget);
    }

    private void translateWord(String clickedWord, View widget) {
        TextTranslator translator = new TextTranslator(clickedWord);
        Call<TextRes> call = translator.getCall("auto", "auto");
        call.enqueue(new Callback<TextRes>() {
            @Override
            public void onResponse(Call<TextRes> call, Response<TextRes> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getBasic() != null) {
                    TextRes textRes = response.body();
                    String usPhonetic = textRes.getBasic().getUsphonetic();
                    String ukPhonetic = textRes.getBasic().getUkphonetic();

                    // 显示 BottomSheetDialog
                    showBottomSheetDialog(clickedWord, usPhonetic, ukPhonetic, widget.getContext());
                } else {
                    Log.e("Translation", "Failed to get translation response");
                }
            }


            @Override
            public void onFailure(Call<TextRes> call, Throwable t) {
                Log.e("Translation", "Translation request failed", t);
            }
        });
    }

    private void showBottomSheetDialog(String word, String usPhonetic, String ukPhonetic, Context context) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.dialog_bottom_word);
        TextView textViewWord = bottomSheetDialog.findViewById(R.id.text_view_word);
        TextView britishPhoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic2);
        TextView americanPhoneticTextView = bottomSheetDialog.findViewById(R.id.text_view_phonetic1);

        if (textViewWord != null) {
            textViewWord.setText(word);
        }

        // 显示美式音标和英式音标
        if (americanPhoneticTextView != null) {
            americanPhoneticTextView.setText("美式音标: " + usPhonetic);
        }
        if (britishPhoneticTextView != null) {
            britishPhoneticTextView.setText("英式音标: " + ukPhonetic);
        }

        bottomSheetDialog.show();
    }
}
