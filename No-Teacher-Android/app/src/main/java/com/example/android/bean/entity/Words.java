package com.example.android.bean.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/14 10:10
 * @Decription:
 */
public class Words {

    @SerializedName("word")
    private String word;
    @SerializedName("phonetic")
    private String phonetic;
    @SerializedName("phonetics")
    private List<PhoneticsDTO> phonetics;
    @SerializedName("meanings")
    private List<MeaningsDTO> meanings;
    @SerializedName("license")
    private LicenseDTO license;
    @SerializedName("sourceUrls")
    private List<String> sourceUrls;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public List<PhoneticsDTO> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<PhoneticsDTO> phonetics) {
        this.phonetics = phonetics;
    }

    public List<MeaningsDTO> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<MeaningsDTO> meanings) {
        this.meanings = meanings;
    }

    public LicenseDTO getLicense() {
        return license;
    }

    public void setLicense(LicenseDTO license) {
        this.license = license;
    }

    public List<String> getSourceUrls() {
        return sourceUrls;
    }

    public void setSourceUrls(List<String> sourceUrls) {
        this.sourceUrls = sourceUrls;
    }

    public static class LicenseDTO {
        @SerializedName("name")
        private String name;
        @SerializedName("url")
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class PhoneticsDTO {
        @SerializedName("text")
        private String text;
        @SerializedName("audio")
        private String audio;
        @SerializedName("sourceUrl")
        private String sourceUrl;
        @SerializedName("license")
        private LicenseDTO license;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }

        public String getSourceUrl() {
            return sourceUrl;
        }

        public void setSourceUrl(String sourceUrl) {
            this.sourceUrl = sourceUrl;
        }

        public LicenseDTO getLicense() {
            return license;
        }

        public void setLicense(LicenseDTO license) {
            this.license = license;
        }

        public static class LicenseDTO {
            @SerializedName("name")
            private String name;
            @SerializedName("url")
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class MeaningsDTO {
        @SerializedName("partOfSpeech")
        private String partOfSpeech;
        @SerializedName("definitions")
        private List<DefinitionsDTO> definitions;
        @SerializedName("synonyms")
        private List<?> synonyms;
        @SerializedName("antonyms")
        private List<String> antonyms;

        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        public void setPartOfSpeech(String partOfSpeech) {
            this.partOfSpeech = partOfSpeech;
        }

        public List<DefinitionsDTO> getDefinitions() {
            return definitions;
        }

        public void setDefinitions(List<DefinitionsDTO> definitions) {
            this.definitions = definitions;
        }

        public List<?> getSynonyms() {
            return synonyms;
        }

        public void setSynonyms(List<?> synonyms) {
            this.synonyms = synonyms;
        }

        public List<String> getAntonyms() {
            return antonyms;
        }

        public void setAntonyms(List<String> antonyms) {
            this.antonyms = antonyms;
        }

        public static class DefinitionsDTO {
            @SerializedName("definition")
            private String definition;
            @SerializedName("synonyms")
            private List<?> synonyms;
            @SerializedName("antonyms")
            private List<?> antonyms;
            @SerializedName("example")
            private String example;

            public String getDefinition() {
                return definition;
            }

            public void setDefinition(String definition) {
                this.definition = definition;
            }

            public List<?> getSynonyms() {
                return synonyms;
            }

            public void setSynonyms(List<?> synonyms) {
                this.synonyms = synonyms;
            }

            public List<?> getAntonyms() {
                return antonyms;
            }

            public void setAntonyms(List<?> antonyms) {
                this.antonyms = antonyms;
            }

            public String getExample() {
                return example;
            }

            public void setExample(String example) {
                this.example = example;
            }
        }
    }
}
