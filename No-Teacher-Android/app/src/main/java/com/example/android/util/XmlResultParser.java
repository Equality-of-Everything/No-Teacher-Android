package com.example.android.util;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/28 9:38
 * @Decription :
 */
import android.text.TextUtils;
import android.util.Xml;

import com.example.android.bean.entity.Phone;
import com.example.android.bean.entity.ReadSentenceResult;
import com.example.android.bean.entity.ReadSyllableResult;
import com.example.android.bean.entity.ReadWordResult;
import com.example.android.bean.entity.Sentence;
import com.example.android.bean.entity.Syll;
import com.example.android.bean.entity.YuYinWord;

//import com.iflytek.ise.result.ReadSentenceResult;
//import com.iflytek.ise.result.ReadWordResult;
//import com.iflytek.ise.result.entity.Word;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class XmlResultParser {

    public Result parse(String xml) {
        if (TextUtils.isEmpty(xml)) {
            return null;
        }

        XmlPullParser pullParser = Xml.newPullParser();

        try {
            InputStream ins = new ByteArrayInputStream(xml.getBytes());
            pullParser.setInput(ins, "utf-8");
            FinalResult finalResult = null;

            int eventType = pullParser.getEventType();
            while (XmlPullParser.END_DOCUMENT != eventType) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("FinalResult".equals(pullParser.getName())) {
                            // 只有一个总分的结果
                            finalResult = new FinalResult();
                        } else if ("ret".equals(pullParser.getName())) {
                            finalResult.ret = getInt(pullParser, "value");
                        } else if ("total_score".equals(pullParser.getName())) {
                            finalResult.total_score = getFloat(pullParser, "value");
                        } else if ("xml_result".equals(pullParser.getName())) {
                            // 详细结果
                            return parseResult(pullParser);
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if ("FinalResult".equals(pullParser.getName())) {
                            return finalResult;
                        }
                        break;

                    default:
                        break;
                }
                eventType = pullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Result parseResult(XmlPullParser pullParser) {
        Result result = null;
        // <rec_paper>标签是否已扫描到
        boolean rec_paperPassed = false;
        Sentence sentence = null;
        YuYinWord word = null;
        Syll syll = null;
        Phone phone = null;

        int eventType;
        try {
            eventType = pullParser.getEventType();
            while (XmlPullParser.END_DOCUMENT != eventType) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("rec_paper".equals(pullParser.getName())) {
                            rec_paperPassed = true;
                        } else if ("read_syllable".equals(pullParser.getName())) {
                            if (!rec_paperPassed) {
                                result = new ReadSyllableResult();
                            } else {
                                readTotalResult(result, pullParser);
                            }
                        } else if ("read_word".equals(pullParser.getName())) {
                            if (!rec_paperPassed) {
                                result = new ReadWordResult();
                                String lan = getLanguage(pullParser);
                                result.language = (null == lan)? "cn": lan;
                            } else {
                                readTotalResult(result, pullParser);
                            }
                        } else if ("read_sentence".equals(pullParser.getName()) ||
                                "read_chapter".equals(pullParser.getName())) {
                            if (!rec_paperPassed) {
                                result = new ReadSentenceResult();
                                String lan = getLanguage(pullParser);
                                result.language = (null == lan)? "cn": lan;
                            } else {
                                readTotalResult(result, pullParser);
                            }
                        } else if ("sentence".equals(pullParser.getName())) {
                            if (null == result.sentences) {
                                result.sentences = new ArrayList<Sentence>();
                            }
                            sentence = createSentence(pullParser);
                        } else if ("word".equals(pullParser.getName())) {
                            if (null != sentence && null == sentence.words) {
                                sentence.words = new ArrayList<YuYinWord>();
                            }
                            word = createWord(pullParser);
                        } else if ("syll".equals(pullParser.getName())) {
                            if (null != word && null == word.sylls) {
                                word.sylls = new ArrayList<Syll>();
                            }
                            syll = createSyll(pullParser);
                        } else if ("phone".equals(pullParser.getName())) {
                            if (null != syll && null == syll.phones) {
                                syll.phones = new ArrayList<Phone>();
                            }
                            phone = createPhone(pullParser);
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if ("phone".equals(pullParser.getName())) {
                            syll.phones.add(phone);
                        } else if ("syll".equals(pullParser.getName())) {
                            word.sylls.add(syll);
                        } else if ("word".equals(pullParser.getName())) {
                            sentence.words.add(word);
                        } else if ("sentence".equals(pullParser.getName())) {
                            result.sentences.add(sentence);
                        } else if ("read_syllable".equals(pullParser.getName())
                                || "read_word".equals(pullParser.getName())
                                || "read_sentence".equals(pullParser.getName())) {
                            return result;
                        }
                        break;

                    default:
                        break;
                }

                eventType = pullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void readTotalResult(Result result, XmlPullParser pullParser) {
        result.beg_pos = getInt(pullParser, "beg_pos");
        result.end_pos = getInt(pullParser, "end_pos");
        result.content = getContent(pullParser);
        result.total_score = getFloat(pullParser, "total_score");
        result.time_len = getInt(pullParser, "time_len");
        result.except_info = getExceptInfo(pullParser);
        result.is_rejected = getIsRejected(pullParser);
        result.accuracy_score = getFloat(pullParser,"accuracy_score");
        result.integrity_score = getFloat(pullParser,"integrity_score");
        result.standard_score = getFloat(pullParser,"standard_score");
        result.fluency_score = getFloat(pullParser,"fluency_score");
    }

    private Phone createPhone(XmlPullParser pullParser) {
        Phone phone;
        phone = new Phone();
        phone.beg_pos = getInt(pullParser, "beg_pos");
        phone.end_pos = getInt(pullParser, "end_pos");
        phone.content = getContent(pullParser);
        phone.dp_message = getInt(pullParser, "dp_message");
        phone.time_len = getInt(pullParser, "time_len");
        return phone;
    }

    private Syll createSyll(XmlPullParser pullParser) {
        Syll syll;
        syll = new Syll();
        syll.beg_pos = getInt(pullParser, "beg_pos");
        syll.end_pos = getInt(pullParser, "end_pos");
        syll.content = getContent(pullParser);
        syll.symbol = getSymbol(pullParser);
        syll.dp_message = getInt(pullParser, "dp_message");
        syll.time_len = getInt(pullParser, "time_len");
        return syll;
    }

    private YuYinWord createWord(XmlPullParser pullParser) {
        YuYinWord word;
        word = new YuYinWord();
        word.beg_pos = getInt(pullParser, "beg_pos");
        word.end_pos = getInt(pullParser, "end_pos");
        word.content = getContent(pullParser);
        word.symbol = getSymbol(pullParser);
        word.time_len = getInt(pullParser, "time_len");
        word.dp_message = getInt(pullParser, "dp_message");
        word.total_score = getFloat(pullParser, "total_score");
        word.global_index = getInt(pullParser, "global_index");
        word.index = getInt(pullParser, "index");
        return word;
    }

    private Sentence createSentence(XmlPullParser pullParser) {
        Sentence sentence;
        sentence = new Sentence();
        sentence.beg_pos = getInt(pullParser, "beg_pos");
        sentence.end_pos = getInt(pullParser, "end_pos");
        sentence.content = getContent(pullParser);
        sentence.time_len = getInt(pullParser, "time_len");
        sentence.index = getInt(pullParser, "index");
        sentence.word_count = getInt(pullParser, "word_count");
        sentence.total_score = getFloat(pullParser, "total_score");
        sentence.accuracy_score = getFloat(pullParser,"accuracy_score");
        sentence.standard_score = getFloat(pullParser,"standard_score");
        sentence.fluency_score = getFloat(pullParser,"fluency_score");
        return sentence;
    }

    private String getLanguage(XmlPullParser pullParser) {
        return pullParser.getAttributeValue(null, "lan");
    }

    private String getExceptInfo(XmlPullParser pullParser) {
        return pullParser.getAttributeValue(null, "except_info");
    }

    private boolean getIsRejected(XmlPullParser pullParser) {
        String isRejected = pullParser.getAttributeValue(null, "is_rejected");
        if (null == isRejected) {
            return false;
        }

        return Boolean.parseBoolean(isRejected);
    }

    private String getSymbol(XmlPullParser pullParser) {
        return pullParser.getAttributeValue(null, "symbol");
    }

    private float getFloat(XmlPullParser pullParser, String attrName) {
        String val = pullParser.getAttributeValue(null, attrName);
        if (null == val) {
            return 0f;
        }
        return Float.parseFloat(val);
    }

    private String getContent(XmlPullParser pullParser) {
        return pullParser.getAttributeValue(null, "content");
    }

    private int getInt(XmlPullParser pullParser, String attrName) {
        String val = pullParser.getAttributeValue(null, attrName);
        if (null == val) {
            return 0;
        }
        return Integer.parseInt(val);
    }
}
