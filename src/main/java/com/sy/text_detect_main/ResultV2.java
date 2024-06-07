package com.sy.text_detect_main;

import com.sy.sensitivity_word_detection.SensitiveWordResult;

import java.util.List;

/**
 * @author sy
 * @date 2024/1/7 15:51
 */
public class ResultV2 {

    private final  boolean isSpam;
    private final  List<SensitiveWordResult> sensitiveWordResultList;
    private final  String htmlHighlightWords;
    private final  double offensiveTextDetPro;

    public boolean getIsSpam() {
        return isSpam;
    }

    public List<SensitiveWordResult> getSensitiveWordResultList() {
        return sensitiveWordResultList;
    }

    public String getHtmlHighlightWords() {
        return htmlHighlightWords;
    }

    public double getOffensiveTextDetPro() {
        return offensiveTextDetPro;
    }

    public static class Builder {
        private boolean isSpam;

        private List<SensitiveWordResult> sensitiveWordResultList;
        private String htmlHighlightWords;
        private double offensiveTextDetPro;

//        public Builder(boolean flag) {
//            this.flag = flag;
//        }

        public Builder isSpam(boolean isSpam) {
            this.isSpam = isSpam;
            return this;
        }

        public Builder sensitiveWordResultList(List<SensitiveWordResult> sensitiveWordResultList) {
            this.sensitiveWordResultList = sensitiveWordResultList;
            return this;
        }

        public Builder htmlHighlightWords(String htmlHighlightWords) {
            this.htmlHighlightWords = htmlHighlightWords;
            return this;
        }

        public Builder offensiveTextDetPro(double offensiveTextDetPro) {
            this.offensiveTextDetPro = offensiveTextDetPro;
            return this;
        }

        public ResultV2 build() {
            return new ResultV2(this);
        }
    }

    private ResultV2(Builder builder) {
        isSpam = builder.isSpam;
        sensitiveWordResultList = builder.sensitiveWordResultList;
        htmlHighlightWords = builder.htmlHighlightWords;
        offensiveTextDetPro = builder.offensiveTextDetPro;
    }

}

