package me.shib.java.lib.diction;

import java.util.ArrayList;

public final class DictionWord {

    private String word;
    private ArrayList<DictionDesc> descriptions;
    private ArrayList<String> hyponyms;

    DictionWord(String word) {
        this.word = word;
        descriptions = new ArrayList<>();
        hyponyms = new ArrayList<>();
    }

    void addDescription(String wordType, String description) {
        descriptions.add(new DictionDesc(wordType, description));
    }

    void addHyponym(String hyponym) {
        hyponyms.add(hyponym);
    }

    public String getWord() {
        return word;
    }

    public ArrayList<DictionDesc> getDescriptions() {
        return descriptions;
    }

    public ArrayList<String> getHyponyms() {
        return hyponyms;
    }

    public class DictionDesc {
        private String wordType;
        private String description;

        private DictionDesc(String wordType, String description) {
            this.wordType = wordType;
            this.description = description;
        }

        public String getWordType() {
            return wordType;
        }

        public String getDescription() {
            return description;
        }
    }

}
