package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.health.TimerStat;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.bean.entity.ReadLog;
import com.example.android.bean.entity.ReaderPage;
import com.example.android.util.GsonUtils;
import com.example.android.util.TokenManager;
import com.example.android.viewmodel.ReadActivityViewModel;
import com.example.no_teacher_andorid.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ReadActivity extends AppCompatActivity {

    private TextView articleTitle;
    private ImageView articleCover;
    private MaterialToolbar back;
    // 单位S
    private static final long TIME_OFFSET = 10L;
    // 最少要求阅读时长(秒)
    private final long MIN_DURATION = 5L;
    // 阅读时长(秒)
    private  long readDuration = 0L;

    private Timer timer = null;
    private Timestamp startTime = null;
    private Timestamp endTime = null;
    private ReadActivityViewModel readActivityViewModel;
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
        readActivityViewModel = new ReadActivityViewModel();

    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new PostReadTimeTask(new WeakReference(this)),TIME_OFFSET,TIME_OFFSET);
        }
    }

    // 定时任务类
    class PostReadTimeTask extends TimerTask{
        private final WeakReference<ReadActivity> activityRef;

        public PostReadTimeTask(WeakReference<ReadActivity> activity) {
            this.activityRef = activity;
        }
        @Override
        public void run() {
            ReadActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            Timer timer = activity.timer;
            long readDuration = activity.readDuration;
            activity.readDuration = readDuration + ReadActivity.TIME_OFFSET;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        startTime = timeStampFormate(new Timestamp(System.currentTimeMillis()));
    }

    private Timestamp timeStampFormate(Timestamp timestamp) {
        final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            String formattedDate = dateFormat.format(timestamp);
            Date parsedDate = dateFormat.parse(formattedDate);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        endTime = timeStampFormate(new Timestamp(System.currentTimeMillis()));
        String id = UUID.randomUUID().toString();
        int articleId = getIntent().getIntExtra("articleId", -1);
        ReadLog readLog = new ReadLog();
        if (readDuration >= MIN_DURATION) {
            // 阅读时长超过5秒
            // 插入阅读记录
            readLog.setId(id);
            readLog.setReadDuration(readDuration);
            readLog.setUserId(TokenManager.getUserId(this));
            readLog.setStartTime(startTime);
            readLog.setEndTime(endTime);
            readLog.setArticleId(articleId);
//            String json_readLog = GsonUtils.getGsonInstance().toJson(readLog);
            readActivityViewModel.insertReadLog(readLog,this);
            Log.e("ReadActivity-onStop",readLog.toString());
        }
    }
}