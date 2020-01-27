package com.kino.metaservice.objects;

import org.apache.commons.lang3.StringUtils;

public class Metadata {

    private String attribute;
    private String[] values;
    private String[] synonyms;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String[] getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String[] synonyms) {
        this.synonyms = synonyms;
    }

    public boolean synonymContains(String value) {
        for(String s : synonyms) {
            if(StringUtils.equals(s, value)) {
                return true;
            }
        }
        return false;
    }

}
