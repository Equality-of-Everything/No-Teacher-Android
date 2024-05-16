package com.example.android.fragment;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android.util.TtsUtil;
import com.example.no_teacher_andorid.R;

import java.util.Locale;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/8 14:29
 * @Decription:
 */
public class ReadTestPagerFragment extends Fragment {

    private static final String ARG_IMAGE_RES_ID = "image_res_id";
    private static final String ARG_TEXT = "text";
    private static final String ARG_COUNT_TEXT = "count_text";

    private int imageResId;
    private String text;
    private String countText;

    private Button btnSpeak;
//    private TextToSpeech mTTS;

    public ReadTestPagerFragment() {
        // Required empty public constructor
    }

    public static ReadTestPagerFragment newInstance(int imageResId, String text, String countText) {
        ReadTestPagerFragment fragment = new ReadTestPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        args.putString(ARG_TEXT, text);
        args.putString(ARG_COUNT_TEXT, countText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResId = getArguments().getInt(ARG_IMAGE_RES_ID);
            text = getArguments().getString(ARG_TEXT);
            countText = getArguments().getString(ARG_COUNT_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_test, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView1 = view.findViewById(R.id.textView);
        TextView textView2 = view.findViewById(R.id.textView2);
        Button btnSpeak = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

        imageView.setImageResource(imageResId);
        textView1.setText(text);
        textView2.setText(countText);

        // Use TtsUtil to set up the TTS buttons
        TtsUtil.getTts1(text, btnSpeak);

        return view;
    }

}