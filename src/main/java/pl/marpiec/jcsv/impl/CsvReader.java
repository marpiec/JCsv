package pl.marpiec.jcsv.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    private final char valueSeparator;

    private static StringBuilder wordBuffer = new StringBuilder();
    private static int textIndex = 0;

    public CsvReader(char valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

    public List<Map<String, String>> read(String csv) {
        int length = csv.length();
        List<String> keys = new ArrayList<String>();
        List<Map<String, String>> deserialized = new ArrayList<Map<String, String>>();

        textIndex = 0;

        while (notCsvEnd(csv) && notLineEnd(csv)) {
            String word = getNextWord(csv);
            keys.add(word);
            if (csv.charAt(textIndex)==valueSeparator) {
                textIndex++;
            }
        }
        textIndex++;

        while (textIndex < length) {
            Map<String, String> deserializedLine = new HashMap<String, String>(keys.size());
            for(String key: keys) {
                String word = getNextWord(csv);
                deserializedLine.put(key, word);
                if (notCsvEnd(csv) && csv.charAt(textIndex)==valueSeparator) {
                    textIndex++;
                }
            }
            textIndex++;

            deserialized.add(deserializedLine);
        }

        return deserialized;
    }


    private String getNextWord(String csv) {
        wordBuffer.setLength(0);

        if (csv.charAt(textIndex)=='\"') {
            textIndex++;
            while (notClosingQuote(csv)) {
                wordBuffer.append(csv.charAt(textIndex));
                textIndex++;
            }
            textIndex++;
        } else {
            while (notCsvEnd(csv) && notLineEnd(csv) && csv.charAt(textIndex)!=valueSeparator) {
                wordBuffer.append(csv.charAt(textIndex));
                textIndex++;
            }
        }

        return wordBuffer.toString();

    }



    private boolean notLineEnd(String csv) {
        if(csv.charAt(textIndex)=='\n' || csv.charAt(textIndex)=='\r') {
            if(csv.length() > textIndex + 1) {
                if(csv.charAt(textIndex + 1)=='\n' || csv.charAt(textIndex + 1)=='\r') {
                   textIndex++;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private boolean notClosingQuote(String csv) {
        if(csv.charAt(textIndex) == '\"') {
            if(csv.charAt(textIndex+1) == '\"' && csv.charAt(textIndex+2) == '\"') {
                textIndex += 2;
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean notCsvEnd(String csv) {
        return csv.length() > textIndex;
    }

}
