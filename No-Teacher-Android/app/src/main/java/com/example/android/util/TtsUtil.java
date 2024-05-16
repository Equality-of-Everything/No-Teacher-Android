package com.example.android.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/11 16:35
 * @Decription:
 */
public class TtsUtil {
    public static void getTts1(String word, Button ukButton) {
        String uk = "http://dict.youdao.com/dictvoice?audio=" + word + "&type=1";
//        String en = "http://dict.youdao.com/dictvoice?audio=" + word + "&type=2";
        ukButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTts(v.getContext(), uk);
            }
        });
//        enButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playTts(v.getContext(), en);
//            }
//        });
    }

    public static void getTts2(String word,Button enButton) {

        String en = "http://dict.youdao.com/dictvoice?audio=" + word + "&type=2";
                enButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTts(v.getContext(), en);
            }
        });

    }



    private static void playTts(Context context, String url) {
        MediaPlayer.create(context, Uri.parse(url)).start();
    }
}
