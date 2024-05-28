package com.example.android.fragment;

import static android.service.controls.ControlsProviderService.TAG;

import static java.lang.String.format;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.util.XmlResultParser;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.android.util.IFlySpeechUtils;
import com.example.android.util.TtsUtil;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    private String imageUrl;
    private String text;
    private String countText;

    private Button btnSpeak;
//    private TextToSpeech mTTS;

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

//    public static Fragment newInstance(WordDetail wordDetail) {
//        ReadTestPagerFragment fragment = new ReadTestPagerFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_IMAGE_RES_ID, wordDetail.getParaphrasePicture());
//        args.putString(ARG_TEXT, wordDetail.getWord());
//        args.putString(ARG_COUNT_TEXT, wordDetail.getParaphrase());
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(ARG_IMAGE_RES_ID);
            text = getArguments().getString(ARG_TEXT);
            countText = getArguments().getString(ARG_COUNT_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice_test, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView1 = view.findViewById(R.id.textView);
        TextView textView2 = view.findViewById(R.id.textView2);
        Button btnSpeak = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

        Glide.with(this)
                .load(imageUrl)
                .into(imageView);
        textView1.setText(text);
        textView2.setText(countText);

        // Use TtsUtil to set up the TTS buttons
        TtsUtil.getTts1(text, btnSpeak);

        SpeechUtility.createUtility(getActivity(), SpeechConstant.APPID +"=5e62dc3d");
        mIse = SpeechEvaluator.createEvaluator(getActivity(), null);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG,"进来");


                startSentenceTest();
            }
        });

        return view;
    }

    private void startSentenceTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int num = 0;
                String directory = "sentence";
                //j 句子的序号
                for(int j=1;j<=10;j++){
                    flag =true;
                    flagNum++;
                    //i 每个句子中的音频文件序号
                    for(int i=1;i<=15;i++){
                        num++;
                        Log.e("hkk","i="+i);

                        AssetManager assetManager = getActivity().getAssets();
                        InputStream inputStream = null;
                        OutputStream outputStream = null;

                        String audioName ="audio_"+String.valueOf(i)+".mp3";
                        try {
                            // 打开资产文件并准备从中读取数据
                            inputStream = assetManager.open(directory+ File.separator+j+File.separator+audioName);

                            File folder1 = new File(getActivity().getExternalFilesDir(null)+File.separator+directory, String.valueOf(j));
                            if (!folder1.exists()) {
                                folder1.mkdirs();
                            }
                            // 获取外部存储器上的文件对象
                            File file = new File(getActivity().getExternalFilesDir(null)+File.separator+directory+File.separator+j, audioName);

                            if (file.exists()) {
                                // 文件已经存在，无需重复创建
                                Log.d(TAG, "File already exists: " + file.getAbsolutePath());
                                //讯飞开始录音
                                mTestContent =lines.get(num-1);
//                                mTestContent =reduceString(mTestContent);
                                setParams();
                                //通过科大讯飞的语音评测服务 mIse 对音频进行评测。评测内容为句子或单词，根据 isWords() 方法的返回值确定
                                int ret = mIse.startEvaluating(isWords() ? "[word]\n" + format(mTestContent):format(mTestContent), null, mEvaluatorListener);
                                if (ret != ErrorCode.SUCCESS) {
                                    android.util.Log.d(TAG, "onStopRecording: "+"识别失败,错误码：" + ret);
                                } else {
                                    byte[] audioData = FucUtil.readAudioFile(getActivity(),file.getAbsolutePath());
                                    if(audioData != null) {
                                        //防止写入音频过早导致失败
                                        try{
                                            new Thread().sleep(200);
                                        }catch (InterruptedException e) {
                                            Log.d(TAG,"InterruptedException :"+e);
                                        }
                                        mIse.writeAudio(audioData,0,audioData.length);
                                        mIse.stopEvaluating();
                                    }
                                }
                                synchronized (lock) {
                                    try {
                                        lock.wait(); // 工作线程暂停等待回调
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                continue;
                            }
                            // 创建输出流并将读取的数据写入文件
                            outputStream = new FileOutputStream(file);
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = inputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }

                            // 文件写入成功
                            Log.d("TAG", "Saved file to: " + file.getAbsolutePath());

                            //讯飞开始录音
                            mTestContent =lines.get(num-1);
//                            mTestContent =reduceString(mTestContent);
                            setParams();
                            int ret = mIse.startEvaluating(isWords() ? "[word]\n" + format(mTestContent):format(mTestContent), null, mEvaluatorListener);
                            if (ret != ErrorCode.SUCCESS) {
                                android.util.Log.d(TAG, "onStopRecording: "+"识别失败,错误码：" + ret);
                            } else {
                                byte[] audioData = FucUtil.readAudioFile(getActivity(),file.getAbsolutePath());
                                if(audioData != null) {
                                    //防止写入音频过早导致失败
                                    try{
                                        new Thread().sleep(200);
                                    }catch (InterruptedException e) {
                                        Log.d(TAG,"InterruptedException :"+e);
                                    }
                                    mIse.writeAudio(audioData,0,audioData.length);
                                    mIse.stopEvaluating();
                                }
                            }
                            synchronized (lock) {
                                try {
                                    lock.wait(); // 工作线程暂停等待回调
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (FileNotFoundException e) {
                            num--;
                            flag =true;
                            flagNum++;
                            // 处理文件未找到错误
                            Log.e("TAG", "Failed to find file: " + e.getMessage());
                            break;
                        } catch (IOException e) {
                            // 处理文件读取或写入错误
                            Log.e("TAG", "Failed to read/write file: " + e.getMessage());
                            break;
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
                }
                BufferedWriter writer = null;
                try {
                    String fileName = "sentence_xf_transform.txt";

                    // 获取外部存储器上的文件对象
                    File file = new File(getActivity().getExternalFilesDir(null), fileName);
                    writer = new BufferedWriter(new FileWriter(file, true));

                    writer.newLine(); // 插入换行符
                    writer.newLine(); // 插入换行符
                    writer.append("sentence - "+50+" end ------");
                    writer.flush();

                } catch ( IOException e) {
                    e.printStackTrace();
                }finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
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
        category =  isWords() ? "read_word" : "read_sentence";

        Log.e("hkkk","category="+category);
//        category = "read_word";
        // 设置结果等级（中文仅支持complete）
        result_level =  "complete";
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_bos ="3000";
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String vad_eos =  "3000";
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        String speech_timeout =  "60000";
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
        String sample_rate = "8000";

        mIse.setParameter(SpeechConstant.LANGUAGE, language);
//        mIse.setParameter("ent", "en_vip");
//        mIse.setParameter(SpeechConstant.SUBJECT, "ise");

        mIse.setParameter(SpeechConstant.ISE_CATEGORY, category);
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mIse.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        mIse.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, result_level);
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT_AUE,"opus");
        mIse.setParameter(SpeechConstant.AUDIO_SOURCE,audio_source);

//        mIse.setParameter(SpeechConstant.AUDIO_FORMAT_AUE,"lame");
        mIse.setParameter(SpeechConstant.SAMPLE_RATE,sample_rate);
        mIse.setParameter(SpeechConstant.RESULT_TYPE,result_type);
        mIse.setParameter("extra_ability",extra_ability);
        mIse.setParameter("rst",rst);
        mIse.setParameter("plev",plev);
        mIse.setParameter("is_unite","0");
//        mIse.setParameter("ent","en_vip");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
//        mIse.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
//        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, Environment.getExternalStorageDirectory().getAbsolutePath() + "ise.wav");
        //通过writeaudio方式直接写入音频时才需要此设置
        mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }


    private boolean isWords(){
        boolean isWords = format(mTestContent).split(" ").length == 1;
        Log.d("===wpt===","wordStr=" + mTestContent+ ",isWords=" + isWords);
        return isWords;
    }

    /**
     * @param null:
     * @return null
     * @author Lee
     * @description 评测监听接口
     * @date 2024/5/27 15:55
     */
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {

        @Override
        public void onResult(EvaluatorResult result, boolean isLast) {
            android.util.Log.d(TAG, "evaluator result :" + isLast);

            if (isLast) {
                StringBuilder builder = new StringBuilder();
                builder.append(result.getResultString());
                mLastResult = builder.toString();
                XmlResultParser resultParser = new XmlResultParser();
                Result scoreResult = resultParser.parse(mLastResult);
//                Result scoreResult = resultParser.parse(mLastResult);

                android.util.Log.d(TAG, "评测:评测结束"+"\n"+mLastResult);
                if (null != scoreResult) {


                    android.util.Log.d(TAG, "评测:评测结束解析"+"\n"+scoreResult.language);
                    if ((scoreResult.is_rejected&&scoreResult.total_score<0.5)||scoreResult.accuracy_score<0.5){
                        if(TextUtils.isEmpty(scoreResult.except_info)){
//                            YUtils.showToast("系统检测为乱说类型，打分失败");

                        }else {
//                            android.util.Log.e(TAG, "hk :" + isLast);
                            if (scoreResult.except_info.equals("28673")){
//                                YUtils.showToast("系统检测为无语音或音量小，打分失败");
                                Log.e(TAG,"系统检测为无语音或音量小，打分失败");
                            }else if (scoreResult.except_info.equals("28676")){
//                                YUtils.showToast("系统检测为乱说类型，打分失败");
                                Log.e(TAG,"系统检测为乱说类型，打分失败");
                            }else if (scoreResult.except_info.equals("28680")){
//                                YUtils.showToast("系统检测为信噪比低类型，打分失败");
                                Log.e(TAG,"系统检测为信噪比低类型，打分失败");
                            }else if (scoreResult.except_info.equals("28690")){
//                                YUtils.showToast("系统检测为截幅类型，打分失败");
                                Log.e(TAG,"系统检测为截幅类型，打分失败");
                            }else if (scoreResult.except_info.equals("28689")){
//                                YUtils.showToast("未检测到音频输入，请检测音频或录音设备是否正常");
                                Log.e(TAG,"未检测到音频输入，请检测音频或录音设备是否正常");
                            }else {
//                                YUtils.showToast(scoreResult.except_info);
                                Log.e(TAG,scoreResult.except_info);
                            }
                        }

                        BufferedWriter writer = null;
                        try {

                            StringBuilder stringBuilder =new StringBuilder(mTestContent);
                            Log.e(TAG,"mTestContent ="+mTestContent);
                            stringBuilder.append("  ------系统检测为乱说!!!  ");
                            String content =stringBuilder.toString();


                            String fileName = "sentence_xf_transform.txt";

                            // 获取外部存储器上的文件对象
                            File file = new File(getActivity().getExternalFilesDir(null), fileName);
                            writer = new BufferedWriter(new FileWriter(file, true));


                            if(flag){
                                flag =false;
                                if(flagNum ==1){
//                                    writer.append("sentence - "+1+" start ------");
                                    writer.newLine(); // 插入换行符
                                    writer.newLine(); // 插入换行符
//                        writer.flush();
                                }else if(flagNum%2!=0){
                                    writer.newLine(); // 插入换行符
//                                    writer.append("sentence - "+(flagNum/2)+" end ------");
                                    writer.newLine(); // 插入换行符
//                                    writer.append("sentence - "+(flagNum/2+1)+" start ------");
                                    writer.newLine(); // 插入换行符
                                    writer.newLine(); // 插入换行符
//                        writer.flush();
                                }
                            }
                            writer.append(content);
                            writer.newLine(); // 插入换行符


                            writer.flush();



                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (writer != null) {
                                try {
                                    writer.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        synchronized (lock) {
                            lock.notify(); // 唤醒工作线程继续执行
                        }

                    }else if(scoreResult.accuracy_score<2){

                        int score = isWords() ? IFlySpeechUtils.getWordScore(scoreResult) : IFlySpeechUtils.getSentenceScore(scoreResult);

                        BufferedWriter writer = null;
                        try {

                            StringBuilder stringBuilder =new StringBuilder(mTestContent);
                            Log.e(TAG,"mTestContent ="+mTestContent);
                            stringBuilder.append("  ------time_len (时长):  ")
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
                                    .append(score);
                            String content =stringBuilder.toString();


                            String fileName = "sentence_xf_transform.txt";

                            // 获取外部存储器上的文件对象
                            File file = new File(getActivity().getExternalFilesDir(null), fileName);
                            writer = new BufferedWriter(new FileWriter(file, true));


                            if(flag){
                                flag =false;
                                if(flagNum ==1){
                                    writer.append("sentence - "+1+" start ------");
                                    writer.newLine(); // 插入换行符
                                    writer.newLine(); // 插入换行符
//                        writer.flush();
                                }else if(flagNum%2!=0){
                                    writer.newLine(); // 插入换行符
                                    writer.append("sentence - "+(flagNum/2)+" end ------");
                                    writer.newLine(); // 插入换行符
                                    writer.append("sentence - "+(flagNum/2+1)+" start ------");
                                    writer.newLine(); // 插入换行符
                                    writer.newLine(); // 插入换行符
//                        writer.flush();
                                }
                            }
                            writer.append(content);
                            writer.newLine(); // 插入换行符


                            writer.flush();



                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (writer != null) {
                                try {
                                    writer.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        synchronized (lock) {
                            lock.notify(); // 唤醒工作线程继续执行
                        }
                    }else {

                        int score = isWords() ? IFlySpeechUtils.getWordScore(scoreResult) : IFlySpeechUtils.getSentenceScore(scoreResult);
                        BufferedWriter writer = null;
                        try {

                            StringBuilder stringBuilder =new StringBuilder(mTestContent);
                            Log.e(TAG,"mTestContent ="+mTestContent);
                            stringBuilder.append("  ------time_len (时长):  ")
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
                                    .append(score);
                            String content =stringBuilder.toString();


                            String fileName = "sentence_xf_transform.txt";

                            // 获取外部存储器上的文件对象
                            File file = new File(getActivity().getExternalFilesDir(null), fileName);
                            writer = new BufferedWriter(new FileWriter(file, true));


                            if(flag){
                                flag =false;
                                if(flagNum ==1){
                                    writer.append("sentence - "+1+" start ------");
                                    writer.newLine(); // 插入换行符
                                    writer.newLine(); // 插入换行符
//                        writer.flush();
                                }else if(flagNum%2!=0){
                                    writer.newLine(); // 插入换行符
                                    writer.append("sentence - "+(flagNum/2)+" end ------");
                                    writer.newLine(); // 插入换行符
                                    writer.append("sentence - "+(flagNum/2+1)+" start ------");
                                    writer.newLine(); // 插入换行符
                                    writer.newLine(); // 插入换行符
//                        writer.flush();
                                }
                            }
                            writer.append(content);
                            writer.newLine(); // 插入换行符


                            writer.flush();



                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (writer != null) {
                                try {
                                    writer.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        synchronized (lock) {
                            lock.notify(); // 唤醒工作线程继续执行
                        }
                    }

                } else {
                    android.util.Log.d(TAG, "评测:评测结束解析"+"\n"+"解析结果为空");
//                    YUtils.showToast("系统检测为乱说类型，打分失败");
                }
            }
        }

        @Override
        public void onError(SpeechError error) {

            if(error != null) {
                android.util.Log.d(TAG, "评测:error:"+ error.getErrorCode() + "," + error.getErrorDescription());
//                YUtils.showToast("系统检测为乱说类型，打分失败");
            } else {
                android.util.Log.d(TAG, "评测:evaluator over");
            }
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            android.util.Log.d(TAG, "评测:evaluator begin");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            android.util.Log.d(TAG, "评测:evaluator stoped");
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            android.util.Log.d(TAG, "评测:当前音量：" + volume);
            android.util.Log.d(TAG, "评测:返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                Log.d(TAG, "session id =" + sid);
            }
        }

    };


}













































