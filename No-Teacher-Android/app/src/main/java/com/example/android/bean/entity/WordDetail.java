package com.example.android.bean.entity;

public class WordDetail {

    private int id;
    private String word;
    private int partOfSpeech;
    private String phoneticSymbol;
    private String pronunciation;
    private String paraphrase;
    private String paraphraseVideo;
    private String paraphrasePicture;

    public WordDetail() {}
    public WordDetail(int id, String word, int partOfSpeech, String phoneticSymbol,
                      String pronunciation, String paraphrase, String paraphraseVideo,
                      String paraphrasePicture) {
        this.id = id;
        this.word = word;
        this.partOfSpeech = partOfSpeech;
        this.phoneticSymbol = phoneticSymbol;
        this.pronunciation = pronunciation;
        this.paraphrase = paraphrase;
        this.paraphraseVideo = paraphraseVideo;
        this.paraphrasePicture = paraphrasePicture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(int partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    public void setPhoneticSymbol(String phoneticSymbol) {
        this.phoneticSymbol = phoneticSymbol;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }

    public String getParaphraseVideo() {
        return paraphraseVideo;
    }

    public void setParaphraseVideo(String paraphraseVideo) {
        this.paraphraseVideo = paraphraseVideo;
    }

    public String getParaphrasePicture() {
        return paraphrasePicture;
    }

    public void setParaphrasePicture(String paraphrasePicture) {
        this.paraphrasePicture = paraphrasePicture;
    }

    @Override
    public String toString() {
        return "WordDetail{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", partOfSpeech=" + partOfSpeech +
                ", phoneticSymbol='" + phoneticSymbol + '\'' +
                ", pronunciation='" + pronunciation + '\'' +
                ", paraphrase='" + paraphrase + '\'' +
                ", paraphraseVideo='" + paraphraseVideo + '\'' +
                ", paraphrasePicture='" + paraphrasePicture + '\'' +
                '}';
    }
}
