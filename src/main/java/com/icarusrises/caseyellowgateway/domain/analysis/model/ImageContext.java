package com.icarusrises.caseyellowgateway.domain.analysis.model;

import java.util.ArrayList;
import java.util.List;

public class ImageContext {

    private List<String> languageHints;

    public ImageContext() {
        languageHints = new ArrayList<>();
    }

    public List<String> getLanguageHints() {
        return languageHints;
    }

    public void setLanguageHints(List<String> languageHints) {
        this.languageHints = languageHints;
    }

    public void addLanguage(String lang) {
        languageHints.add(lang);
    }

    @Override
    public String toString() {
        return "ImageContext{" +
                "languageHints=" + languageHints +
                '}';
    }
}
