package com.iflytek.voicedemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.util.Result;
import com.example.no_teacher_andorid.R;

import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;
import com.example.android.util.XmlResultParser;
import com.iflytek.speech.setting.IseSettings;


/**
 * 语音评测demo
 */
public class IseDemo extends Activity implements OnClickListener {
    private static String TAG = IseDemo.class.getSimpleName();

    private final static String PREFER_NAME = "ise_settings";
    private final static int REQUEST_CODE_SETTINGS = 1;

    private EditText mEvaTextEditText;
    private TextView mResultEditText;
    private Button mIseStartButton;
    private Toast mToast;

    // 评测语种
    private String language;
    // 评测题型
    private String category;
    // 结果等级
    private String result_level;

    private String mLastResult;
    private SpeechEvaluator mIse;


    // 评测监听接口
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {

        @Override
        public void onResult(EvaluatorResult result, boolean isLast) {
            Log.d(TAG, "evaluator result :" + isLast);

            if (isLast) {
                StringBuilder builder = new StringBuilder();
                builder.append(result.getResultString());

                if (!TextUtils.isEmpty(builder)) {
                    mResultEditText.setText(builder.toString());
                }
                mIseStartButton.setEnabled(true);
                mLastResult = builder.toString();

                showTip("评测结束");
            }
        }

        @Override
        public void onError(SpeechError error) {
            mIseStartButton.setEnabled(true);
            if (error != null) {
                showTip("error:" + error.getErrorCode() + "," + error.getErrorDescription());
                mResultEditText.setText("");
                mResultEditText.setHint("请点击“开始评测”按钮");
            } else {
                Log.d(TAG, "evaluator over");
            }
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            Log.d(TAG, "evaluator begin");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            Log.d(TAG, "evaluator stoped");
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前音量：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.isedemo);

        mIse = SpeechEvaluator.createEvaluator(IseDemo.this, null);
        initUI();
        setEvaText();
    }

    private void initUI() {
        findViewById(R.id.image_ise_set).setOnClickListener(IseDemo.this);
        mEvaTextEditText = (EditText) findViewById(R.id.ise_eva_text);
        mResultEditText = (TextView) findViewById(R.id.ise_result_text);
        mIseStartButton = (Button) findViewById(R.id.ise_start);
        mIseStartButton.setOnClickListener(IseDemo.this);
        findViewById(R.id.ise_parse).setOnClickListener(IseDemo.this);
        findViewById(R.id.ise_stop).setOnClickListener(IseDemo.this);
        findViewById(R.id.ise_cancel).setOnClickListener(IseDemo.this);

        mToast = Toast.makeText(IseDemo.this, "", Toast.LENGTH_LONG);
    }

    @Override
    public void onClick(View view) {
        if (null == mIse) {
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }

        switch (view.getId()) {
            case R.id.image_ise_set:
                Intent intent = new Intent(IseDemo.this, IseSettings.class);
                startActivityForResult(intent, REQUEST_CODE_SETTINGS);
                break;
            case R.id.ise_start:
                if (mIse == null) {
                    return;
                }

                String evaText = mEvaTextEditText.getText().toString();
                mLastResult = null;
                mResultEditText.setText("");
                mResultEditText.setHint("请朗读以上内容");
                mIseStartButton.setEnabled(false);

                setParams();
                int ret = mIse.startEvaluating(evaText, null, mEvaluatorListener);
                //以下方法为通过直接写入音频的方式进行评测业务
				/*if (ret != ErrorCode.SUCCESS) {
					showTip("识别失败,错误码：" + ret);
				} else {
					showTip(getString(R.string.text_begin_ise));
					byte[] audioData = FucUtil.readAudioFile(IseDemo.this,"isetest.wav");
					if(audioData != null) {
						//防止写入音频过早导致失败
						try{
							new Thread().sleep(100);
						}catch (InterruptedException e) {
							Log.d(TAG,"InterruptedException :"+e);
						}
						mIse.writeAudio(audioData,0,audioData.length);
						mIse.stopEvaluating();
					}
				}*/


                break;
            case R.id.ise_parse:
                // 解析最终结果
                if (!TextUtils.isEmpty(mLastResult)) {
                    // 拦截不支持的解析类型
                    if ("complete".equals(result_level)) {
                        if ("simple_expression".equals(category) || "read_choice".equals(category)
                                || "topic".equals(category) || "retell".equals(category)
                                || "picture_talk".equals(category) || "oral_translation".equals(category)) {
                            showTip("不支持解析该类型");
                            return;
                        }
                    }

                    XmlResultParser resultParser = new XmlResultParser();
                    Result result = resultParser.parse(mLastResult);

                    if (null != result) {
                        mResultEditText.setText(result.toString());
                    } else {
                        showTip("解析结果为空");
                    }
                }
                break;
            case R.id.ise_stop:
                if (mIse.isEvaluating()) {
                    mResultEditText.setHint("评测已停止，等待结果中...");
                    mIse.stopEvaluating();
                }
                break;
            case R.id.ise_cancel: {
                mIse.cancel();
                mIseStartButton.setEnabled(true);
                mResultEditText.setText("");
                mResultEditText.setHint("请点击“开始评测”按钮");
                mLastResult = null;
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE_SETTINGS == requestCode) {
            setEvaText();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mIse) {
            mIse.destroy();
            mIse = null;
        }
    }

    // 设置评测试题
    private void setEvaText() {
        SharedPreferences pref = getSharedPreferences(PREFER_NAME, MODE_PRIVATE);
        language = pref.getString(SpeechConstant.LANGUAGE, "zh_cn");
        category = pref.getString(SpeechConstant.ISE_CATEGORY, "read_sentence");

        String text = "";
        if ("en_us".equals(language)) {
            switch (category) {
                case "read_word": // 词语
                    text = getString(R.string.text_en_word);
                    break;
                case "read_sentence": // 句子
                    text = getString(R.string.text_en_sentence);
                    break;
                case "read_chapter": // 篇章
                    text = getString(R.string.text_en_chapter);
                    break;
                case "simple_expression": // 英文情景反应
                    text = new String(Base64.decode(getString(R.string.text_en_simple_expression), Base64.DEFAULT));
                    break;
                case "read_choice": // 英文选择题
                    text = new String(Base64.decode(getString(R.string.text_en_read_choice), Base64.DEFAULT));
                    break;
                case "topic": // 英文自由题
                    text = new String(Base64.decode(getString(R.string.text_en_topic), Base64.DEFAULT));
                    break;
                case "retell": // 英文复述题
                    text = new String(Base64.decode(getString(R.string.text_en_retell), Base64.DEFAULT));
                    break;
                case "picture_talk": // 英文看图说话
                    text = new String(Base64.decode(getString(R.string.text_en_picture_talk), Base64.DEFAULT));
                    break;
                case "oral_translation": // 英文口头翻译
                    text = new String(Base64.decode(getString(R.string.text_en_oral_translation), Base64.DEFAULT));
                    break;
            }

        } else {
            // 中文评测
            switch (category) {
                case "read_syllable":
                    text = getString(R.string.text_cn_syllable);
                    break;
                case "read_word":
                    text = getString(R.string.text_cn_word);
                    break;
                case "read_sentence":
                    text = getString(R.string.text_cn_sentence);
                    break;
                case "read_chapter":
                    text = getString(R.string.text_cn_chapter);
                    break;
            }
        }

        mEvaTextEditText.setText(text);
        mResultEditText.setText("");
        mLastResult = null;
        mResultEditText.setHint("请点击“开始评测”按钮");
    }

    private void showTip(String str) {
        if (!TextUtils.isEmpty(str)) {
            mToast.cancel();
            mToast.setText(str);
            mToast.show();
        }
    }

    private void setParams() {

        SharedPreferences pref = getSharedPreferences(PREFER_NAME, MODE_PRIVATE);
        // 设置评测语言
        language = pref.getString(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置需要评测的类型
        category = pref.getString(SpeechConstant.ISE_CATEGORY, "read_sentence");
        // 设置结果等级（中文仅支持complete）
        result_level = pref.getString(SpeechConstant.RESULT_LEVEL, "complete");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        String vad_bos = pref.getString(SpeechConstant.VAD_BOS, "5000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        String vad_eos = pref.getString(SpeechConstant.VAD_EOS, "1800");
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        String speech_timeout = pref.getString(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");

        // 设置流式版本所需参数 : ent sub plev
        if ("zh_cn".equals(language)) {
            mIse.setParameter("ent", "cn_vip");
        }
        if ("en_us".equals(language)) {
            mIse.setParameter("ent", "en_vip");
        }
        mIse.setParameter(SpeechConstant.SUBJECT, "ise");
        mIse.setParameter("plev", "0");

        // 设置评分百分制 使用 ise_unite  rst  extra_ability 参数
        mIse.setParameter("ise_unite", "1");
        mIse.setParameter("rst", "entirety");
        mIse.setParameter("extra_ability", "syll_phone_err_msg;pitch;multi_dimension");

        mIse.setParameter(SpeechConstant.LANGUAGE, language);
        mIse.setParameter(SpeechConstant.ISE_CATEGORY, category);
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mIse.setParameter(SpeechConstant.VAD_BOS, vad_bos);
        mIse.setParameter(SpeechConstant.VAD_EOS, vad_eos);
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, speech_timeout);
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, result_level);
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT_AUE, "opus");
        // 设置音频保存路径，保存音频格式支持pcm、wav，
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH,
                getExternalFilesDir("msc").getAbsolutePath() + "/ise.wav");
        //通过writeaudio方式直接写入音频时才需要此设置
        //mIse.setParameter(SpeechConstant.AUDIO_SOURCE,"-1");
    }

}
