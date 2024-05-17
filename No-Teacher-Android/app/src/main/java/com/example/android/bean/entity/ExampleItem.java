package com.example.android.bean.entity;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/17 14:58
 * @Decription:
 */
// ExampleItem.java
public class ExampleItem {
    private String partOfSpeech;
    private String definition;
    private String example;
    private String translatedExample; // 新增翻译后的例句字段

    public ExampleItem(String partOfSpeech, String definition, String example) {
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
        this.example = example;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getDefinition() {
        return definition;
    }

    public String getExample() {
        return example;
    }

    public String getTranslatedExample() {
        return translatedExample;
    }

    public void setTranslatedExample(String translatedExample) {
        this.translatedExample = translatedExample;
    }
}
