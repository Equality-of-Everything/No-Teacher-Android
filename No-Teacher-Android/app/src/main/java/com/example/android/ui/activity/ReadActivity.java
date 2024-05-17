package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.bean.entity.ReaderPage;
import com.example.no_teacher_andorid.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ReadActivity extends AppCompatActivity {

    private TextView articleTitle;
    private ImageView articleCover;
    private MaterialToolbar back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        ReaderPage readerPage = findViewById(R.id.readerPage);

        String title = getIntent().getStringExtra("title");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String content = getIntent().getStringExtra("content");
        articleTitle = findViewById(R.id.article_title);
        articleCover = findViewById(R.id.articleCover);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        // 设置标题
        articleTitle.setText(title);
        // 设置图片
        if (articleCover != null && imageUrl != null) {
            // 圆角角度
            RequestOptions requestOptions = new RequestOptions()
                    .transform(new RoundedCorners(20));
            Glide.with(this)
                    .load(imageUrl).
                    apply(requestOptions).
                    into(articleCover);
        }

        // 设置内容
        readerPage.setContent(content);

    }
}