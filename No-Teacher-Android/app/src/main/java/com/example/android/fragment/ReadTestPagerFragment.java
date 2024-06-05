package com.example.android.fragment;

import static android.service.controls.ControlsProviderService.TAG;

import static java.lang.String.format;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bean.entity.WordDetail;
import com.example.android.util.ToastManager;
import com.example.android.util.XmlResultParser;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.android.util.IFlySpeechUtils;
import com.example.android.util.TtsUtil;
import com.example.android.viewmodel.BFragmentViewModel;
import com.example.no_teacher_andorid.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.speech.util.FucUtil;
import com.example.android.util.Result;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/8 14:29
 * @Decription:
 */
public class ReadTestPagerFragment extends Fragment {

    private static final String ARG_IMAGE_RES_ID = "image_res_id";
    private static final String ARG_TEXT = "text";
    private static final String ARG_COUNT_TEXT = "count_text";
    private WordDetail wordDetail;
    private String imageUrl;
    private String text;
    private String countText;

    private Button btnSpeak;
//    private TextToSpeech mTTS;

    private BFragmentViewModel viewModel;

    //科大讯飞
    private SpeechEvaluator mIse;
    private String mTestContent = "";
    private boolean flag;   //判断是否变更文件夹
    private int flagNum;
    // 评测语种
    private String language;
    // 评测题型
    private String category;
    // 结果等级
    private String result_level;
    private String mLastResult;
    private final Object lock = new Object();
    private List<String> lines ;
    {
        lines = new ArrayList<>();
    }

    //录制音频
    private String curWord;//当前界面上显示的单词

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String outputFilePath;
    private int recordingIndex = 1;
    private boolean isRecording = false;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 210;
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 220;

    private enum RecorderState {
        IDLE,
        INITIALIZING,
        READY,
        RECORDING,
        ERROR,
        STOPPED
    }
    private RecorderState currentState = RecorderState.IDLE;


    public ReadTestPagerFragment() {
        // Required empty public constructor
    }

    public static ReadTestPagerFragment newInstance(String imageResId, String text, String countText) {
        ReadTestPagerFragment fragment = new ReadTestPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_RES_ID, imageResId);
        args.putString(ARG_TEXT, text);
        args.putString(ARG_COUNT_TEXT, countText);
        fragment.setArguments(args);
        return fragment;
    }
    public String getWord() {
        // 正确获取并返回单词，这里假设单词通过Bundle传递
        return getArguments().getString(ARG_TEXT);
    }

//    public static Fragment newInstance(WordDetail wordDetail) {
//        ReadTestPagerFragment fragment = new ReadTestPagerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_IMAGE_RES_ID, wordDetail.getParaphrasePicture());
//        args.putString(ARG_TEXT, wordDetail.getWord());
//        args.putString(ARG_COUNT_TEXT, wordDetail.getParaphrase());
//        fragment.setArguments(args);
//        return fragment;
//    }


    public String getCurWord() {
        return curWord;
    }

    public void setCurWord(String curWord) {
        this.curWord = curWord;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(ARG_IMAGE_RES_ID);
            text = getArguments().getString(ARG_TEXT);
            countText = getArguments().getString(ARG_COUNT_TEXT);
            wordDetail = new WordDetail(getArguments().getString("paraphrasePicture"),
                    getArguments().getString("word"),
                    getArguments().getString("paraphrase"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_voice_test, container, false);

        View view = inflater.inflate(R.layout.fragment_voice_test, container, false);
        //初始化viewModel
        viewModel = new ViewModelProvider(this).get(BFragmentViewModel.class);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView1 = view.findViewById(R.id.textView);
        TextView textView2 = view.findViewById(R.id.textView2);
        Button btnSpeak = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

        curWord = "hello";
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);
        textView1.setText(text);
        textView2.setText(countText);

        // Use TtsUtil to set up the TTS buttons
        TtsUtil.getTts1(text, btnSpeak);

//        SpeechUtility.createUtility(getContext(), SpeechConstant.APPID +"=" + "5e62dc3d");
        mIse = SpeechEvaluator.createEvaluator(getContext(), null);

        if(mIse != null) {
            setParams();
        } else {
            Log.e("mIse", "语音评测初始化失败");
        }

        //请求录音的相关权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    REQUEST_RECORD_AUDIO_PERMISSION);
        } else {
            // 初始化录音设置
            initRecording();
        }

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isRecording) {
                    startRecording();

                    ToastManager.showCustomToast(getActivity(), "开始录音");
                } else {
                    stopRecording();
                    ToastManager.showCustomToast(getActivity(), "结束录音");

                    //开始句子测评
                    startSentenceTest();
                }
                isRecording = !isRecording;

            }
        });

        return view;
    }

//    public void setCurWord(String curWord) {
//        this.curWord = curWord;
//    }
//
//    public String getCurWord() {
//        return curWord;
//    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 初始化录音设置
     * @date 2024/5/29 14:16
     */
    private void initRecording() {
        File directory = new File(getActivity().getExternalCacheDir().getAbsolutePath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        outputFilePath = directory.getAbsolutePath() + "/recording.wav";
        Log.e("Recording", "Output file path: " + outputFilePath);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(outputFilePath);
        currentState = RecorderState.READY;
    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 开始录音
     * @date 2024/5/29 14:16
     */
    private void startRecording() {
        if (currentState == RecorderState.READY) {
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                currentState = RecorderState.RECORDING;
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
                currentState = RecorderState.ERROR;
            }
        }
    }

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 停止录音
     * @date 2024/5/29 14:16
     */
    private void stopRecording() {
        if (currentState == RecorderState.RECORDING) {
            try {
                mediaRecorder.stop();
                mediaRecorder.release(); // Release the MediaRecorder

                File dir = new File(Environment.getExternalStorageDirectory(), "AudioRecorder");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, "/recording.wav");

                mediaRecorder = new MediaRecorder(); // Reinitialize the MediaRecorder for subsequent recordings
                initRecording(); // Reinitialize recording settings
                currentState = RecorderState.READY;

                // Check if the recording file exists
                File recordingFile = new File(outputFilePath);
                if (recordingFile.exists() && recordingFile.isFile()) {
                    // Recording file generated
                    Log.d("Recording", "Recording file generated at: " + outputFilePath);
                } else {
                    // Recording file not generated
                    Log.e("Recording", "Recording file not generated");
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION ||
                requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION ||
                requestCode == REQUEST_READ_EXTERNAL_STORAGE_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.RECORD_AUDIO) &&
                        grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    initRecording();
                }
            }
        }
    }

    private void startSentenceTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                OutputStream outputStream = null;

                try{
                    File file = new File(Environment.getExternalStorageDirectory(), "Android/data/com.example.no_teacher_andorid/cache/recording.wav");
                    if (file.exists()) {
                        // 进行你的操作，比如播放音频或者处理音频数据
                        playAudio(file);
                        //通过科大讯飞的语音评测服务 mIse 对音频进行评测。评测内容为句子或单词，根据 isWords() 方法的返回值确定
//                        int ret = mIse.startEvaluating(isWords() ? "[word]\n" + format(mTestContent) : format(mTestContent), null, mEvaluatorListener);
                        if (mIse != null) {
                            String content;

                            if (curWord != null){
                                    Log.e("curWord", curWord);
                            }
                            if (curWord.split(" ").length == 1) {
                                content = "[word]\n" + curWord+"\n";
                            } else {
                                content = "[word]\n" + curWord+"\n";
                            }
                            int ret = mIse.startEvaluating(content, null, mEvaluatorListener);
                            Log.e("mIse", "mIse初始化完成，开始接入语音测评");
//                            int ret = mIse.startEvaluating(format(curWord), null, mEvaluatorListener);
//                            int ret = mIse.startEvaluating(curWord, null, mEvaluatorListener);//接入语音评测
                            if (ret == ErrorCode.SUCCESS) {
                                byte[] audioData = FucUtil.readAudioFile(getActivity(), file.getAbsolutePath());
                                if (audioData != null) {
                                    try {
                                        Thread.sleep(500);  // 等待200毫秒
                                        mIse.writeAudio(audioData, 0, audioData.length);
                                        mIse.stopEvaluating();
                                    } catch (InterruptedException e) {
                                        Log.d("writeAudio", "写入音频时中断异常");
                                    }
                                }
                            } else {
                                Log.d("writeAudio", "写入音频文件失败");
                            }
                        } else {
                            Log.e("mIse", "未初始化");
                        }

                        synchronized (lock) {
                            try {
                                lock.wait(); // 工作线程暂停等待回调
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Log.e(TAG, "Thread interrupted", e);
                            }
                        }

                        Log.e("开始测评", "begin");
                    } else {
                        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "File does not exist", Toast.LENGTH_SHORT).show());
                    }
                } finally {
                    // 关闭输入流和输出流
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.e("hkk","到末尾");


            }
        }).start();
    }

    private void playAudio(File audioFile) {
        releaseMediaPlayer();  // 确保之前的 MediaPlayer 被释放
        Log.e("MediaPlayer", "播放录音：" + audioFile.getPath());
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioFile.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> {
                Log.e("MediaPlayer", "播放完成");
                releaseMediaPlayer();
            });
        } catch (IOException e) {
            Log.e("MediaPlayer", "播放错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }



    /**
     * @param :
     * @return void
     * @author Lee
     * @description 配置参数
     * @date 2024/5/27 15:49
     */
    private void setParams() {
        // 清空参数
//        mIse.setParameter(SpeechConstant.PARAMS, null);
        // 设置评测语言
        language = "en_us";
        // 设置需要评测的类型
        category =  "read_word";

        Log.e("hkkk","category="+category);
        // 设置结果等级（中文仅支持complete）
        result_level =  "complete";
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_bos ="5000";
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String vad_eos =  "1800";
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        String speech_timeout =  "-1";
        //评测结果格式
        String result_type ="xml";
        //返回结果与分控制
        String rst = "entirety";
        //plev:：0（给出全部信息，英文包含accuracy_score、serr_msg、 syll_accent、fluency_score、standard_score、pitch等信息的返回）
        String plev = "0";
        /**
         * 拓展能力（生效条件ise_unite="1", rst="entirety"）
         * 多维度分信息显示（准确度分、流畅度分、完整度打分）
         * extra_ability值为multi_dimension（字词句篇均适用,如选多个能力，用分号；隔开）
         * 单词基频信息显示（基频开始值、结束值）
         * extra_ability值为pitch ，仅适用于单词和句子题型
         * 音素错误信息显示（声韵、调型是否正确）
         * extra_ability值为syll_phone_err_msg（字词句篇均适用,如选多个能力，用分号；隔开）
         */
        String extra_ability = "multi_dimension";
        //录音源
        String audio_source = "1";
        //采样率
        String sample_rate = "16000";

        mIse.setParameter(SpeechConstant.LANGUAGE, language);
        mIse.setParameter("ent", "en_vip");
        mIse.setParameter(SpeechConstant.SUBJECT, "ise");

        mIse.setParameter("plev", "0");

        // 设置评分百分制 使用 ise_unite  rst  extra_ability 参数
        mIse.setParameter("ise_unite", "1");
        mIse.setParameter("rst", "entirety");
        mIse.setParameter("extra_ability", "syll_phone_err_msg;pitch;multi_dimension");


        mIse.setParameter(SpeechConstant.ISE_CATEGORY, category);
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mIse.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        mIse.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, result_level);
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT_AUE,"opus");
        mIse.setParameter(SpeechConstant.AUDIO_SOURCE,audio_source);

        mIse.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIse.setParameter(SpeechConstant.SAMPLE_RATE,sample_rate);
        mIse.setParameter(SpeechConstant.RESULT_TYPE,result_type);
        mIse.setParameter("extra_ability",extra_ability);
//        mIse.setParameter("rst",rst);
//        mIse.setParameter("plev",plev);
        mIse.setParameter("is_unite","0");
//        mIse.setParameter("ent","en_vip");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
//        mIse.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
//        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + "ise.wav");
        //通过writeaudio方式直接写入音频时才需要此设置
//        mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }


    private boolean isWords(){
        boolean isWords = format(mTestContent).split(" ").length == 1;
        Log.d("isWords测评","wordStr=" + mTestContent+ ",isWords=" + isWords);
        return isWords;
    }

    /**
     * @param null:
     * @return null
     * @author Lee
     * @description 评测监听接口(监听评测结果)
     * @date 2024/5/27 15:55
     */
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {
        @Override
        public void onResult(EvaluatorResult result, boolean isLast) {
            Log.e("EvaluatorResult", "" + isLast);
            if (isLast) {
                StringBuilder builder = new StringBuilder(result.getResultString());
                mLastResult = builder.toString();
                XmlResultParser resultParser = new XmlResultParser();
                Result scoreResult = resultParser.parse(mLastResult);
                Log.e("EvaluatorResult", "评测结束\n" + mLastResult);
                if (scoreResult != null) {

                    //显示语音评测结果
                    BufferedWriter writer = null;
                    try {
                        StringBuilder contentBuilder = new StringBuilder(mTestContent);
                        contentBuilder.append("  ------time_len (时长):  ")
                                .append(scoreResult.time_len)
                                .append("  ------ total_score(总分):  ")
                                .append(scoreResult.total_score)
                                .append("  ------ accuracy_score(准确度分):  ")
                                .append(scoreResult.accuracy_score)
                                .append("  ------ fluency_score(流利度):  ")
                                .append(scoreResult.fluency_score)
                                .append("  ------ integrity_score(完整性):  ")
                                .append(scoreResult.integrity_score)
                                .append("  ------ 转化分: ")
                                .append(isWords() ? IFlySpeechUtils.getWordScore(scoreResult) : IFlySpeechUtils.getSentenceScore(scoreResult));

                        String content = contentBuilder.toString();

                        String fileName = "evaluation_results.txt";
                        File file = new File(getActivity().getExternalFilesDir(null), fileName);
                        writer = new BufferedWriter(new FileWriter(file, true));
                        writer.append(content);
                        writer.newLine();  // 插入换行符
                        writer.flush();
                        Log.d(TAG, "评测结果已写入文件: " + content);

                        displayResultsFromFile();//调用方法读取评测结果文件
                    } catch (IOException e) {
                        Log.e(TAG, "写入文件时发生错误", e);
                    } finally {
                        if (writer != null) {
                            try {
                                writer.close();
                            } catch (IOException e) {
                                Log.e(TAG, "关闭文件写入器时发生错误", e);
                            }
                        }
                    }

                } else {
                    Log.e("EvaluatorResult", "评测:评测结束解析\n解析结果为空");
                }
            }
        }

        @Override
        public void onError(SpeechError error) {
            Log.e("评测出错", error+"");
        }

        @Override
        public void onBeginOfSpeech() {
            Log.d(TAG, "评测:evaluator begin");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "评测:evaluator stoped");
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            Log.d(TAG, "评测:当前音量：" + volume);
            Log.d(TAG, "评测:返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                Log.d(TAG, "session id =" + sid);
            }
        }
    };

    /**
     * @param :
     * @return void
     * @author Lee
     * @description 读取测评结果文件
     * @date 2024/5/30 9:56
     */
    private void displayResultsFromFile() {
        Activity activity = getActivity();
        if (activity == null) return;

        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(activity.getExternalFilesDir(null), "evaluation_results.txt");
                StringBuilder fileContent = new StringBuilder();
                if (file.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            fileContent.append(line).append("\n");
                        }

                        // 显示Toast需要在UI线程执行
                        String finalContent = fileContent.toString();
                        activity.runOnUiThread(() -> Toast.makeText(activity, finalContent, Toast.LENGTH_LONG).show());
                    } catch (IOException e) {
                        Log.e(TAG, "读取文件时发生错误", e);
                    }
                } else {
                    activity.runOnUiThread(() -> Toast.makeText(activity, "File does not exist", Toast.LENGTH_SHORT).show());
                }
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mIse == null) {
            mIse = SpeechEvaluator.createEvaluator(getContext(), null);
        } else{
            Log.e("mIse onResume", "Error");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mIse) {
            mIse.destroy();
//            mIse = null;
        }
    }


}













































