package me.shib.java.lib.diction;

import rita.wordnet.RiWordnet;

import java.util.ArrayList;


public final class DictionService {

    private RiWordnet wordnet;

    public DictionService() {
        wordnet = new RiWordnet();
    }

    private POS getPos(String posTypeCharacter) {
        if (posTypeCharacter.equalsIgnoreCase("n")) {
            return POS.noun;
        } else if (posTypeCharacter.equalsIgnoreCase("v")) {
            return POS.verb;
        } else if (posTypeCharacter.equalsIgnoreCase("a")) {
            return POS.adjective;
        } else {
            return POS.adverb;
        }
    }

    private PosSenseIds[] getPosSenseIds(String word) {
        ArrayList<PosSenseIds> posSenseIdsList = new ArrayList<>();
        String[] posList = wordnet.getPos(word);
        for (String p : posList) {
            PosSenseIds posIds = new PosSenseIds(getPos(p));
            posIds.ids = wordnet.getSenseIds(word, p);
            posSenseIdsList.add(posIds);
        }
        if (posSenseIdsList.size() == 0) {
            return null;
        }
        PosSenseIds[] posSenseIds = new PosSenseIds[posSenseIdsList.size()];
        return posSenseIdsList.toArray(posSenseIds);
    }

    public DictionWord getDictionWord(String word) {
        DictionWord dictWord = new DictionWord(word);
        PosSenseIds[] posSenseIds = getPosSenseIds(dictWord.getWord());
        if ((null == posSenseIds) || (posSenseIds.length == 0)) {
            return null;
        }
        for (PosSenseIds posid : posSenseIds) {
            for (int i = 0; i < posid.ids.length; i++) {
                dictWord.addDescription(posid.pos.toString(), wordnet.getDescription(posid.ids[i]));
            }
            String[] hyponyms = wordnet.getHyponyms(word, posid.pos.toPosStr());
            if ((null != hyponyms) && (hyponyms.length > 0)) {
                for (String hyponym : hyponyms) {
                    dictWord.addHyponym(hyponym);
                }
            }
        }
        return dictWord;
    }

    private enum POS {
        noun("n"), verb("v"), adjective("a"), adverb("r");
        private String posStr;

        POS(String posStr) {
            this.posStr = posStr;
        }

        private String toPosStr() {
            return posStr;
        }
    }

    private class PosSenseIds {
        private POS pos;
        private int[] ids;

        private PosSenseIds(POS pos) {
            this.pos = pos;
        }
    }

}
