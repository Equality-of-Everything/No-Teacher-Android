package com.example.android.bean.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/13 14:02
 * @Decription:
 */
public class TextRes {
    @SerializedName("errorCode")
    private String errorCode;
    @SerializedName("query")
    private String query;
    @SerializedName("translation")
    private List<String> translation;
    @SerializedName("basic")
    private BasicDTO basic;
    @SerializedName("web")
    private List<WebDTO> web;
    @SerializedName("dict")
    private DictDTO dict;
    @SerializedName("webdict")
    private WebdictDTO webdict;
    @SerializedName("l")
    private String l;
    @SerializedName("tSpeakUrl")
    private String tSpeakUrl;
    @SerializedName("speakUrl")
    private String speakUrl;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public BasicDTO getBasic() {
        return basic;
    }

    public void setBasic(BasicDTO basic) {
        this.basic = basic;
    }

    public List<WebDTO> getWeb() {
        return web;
    }

    public void setWeb(List<WebDTO> web) {
        this.web = web;
    }

    public DictDTO getDict() {
        return dict;
    }

    public void setDict(DictDTO dict) {
        this.dict = dict;
    }

    public WebdictDTO getWebdict() {
        return webdict;
    }

    public void setWebdict(WebdictDTO webdict) {
        this.webdict = webdict;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getTSpeakUrl() {
        return tSpeakUrl;
    }

    public void setTSpeakUrl(String tSpeakUrl) {
        this.tSpeakUrl = tSpeakUrl;
    }

    public String getSpeakUrl() {
        return speakUrl;
    }

    public void setSpeakUrl(String speakUrl) {
        this.speakUrl = speakUrl;
    }

    public static class BasicDTO {
        @SerializedName("phonetic")
        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukphonetic;
        @SerializedName("us-phonetic")
        private String usphonetic;
        @SerializedName("uk-speech")
        private String ukspeech;
        @SerializedName("us-speech")
        private String usspeech;
        @SerializedName("explains")
        private List<String> explains;

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getUkphonetic() {
            return ukphonetic;
        }

        public void setUkphonetic(String ukphonetic) {
            this.ukphonetic = ukphonetic;
        }

        public String getUsphonetic() {
            return usphonetic;
        }

        public void setUsphonetic(String usphonetic) {
            this.usphonetic = usphonetic;
        }

        public String getUkspeech() {
            return ukspeech;
        }

        public void setUkspeech(String ukspeech) {
            this.ukspeech = ukspeech;
        }

        public String getUsspeech() {
            return usspeech;
        }

        public void setUsspeech(String usspeech) {
            this.usspeech = usspeech;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class DictDTO {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class WebdictDTO {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class WebDTO {
        @SerializedName("key")
        private String key;
        @SerializedName("value")
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
