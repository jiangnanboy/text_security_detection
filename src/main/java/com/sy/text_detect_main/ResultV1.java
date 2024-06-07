package com.sy.text_detect_main;

import com.sy.sensitivity_word_detection.SensitiveWordResult;

import java.util.List;

/**
 * @author sy
 * @date 2024/1/7 15:51
 */
public class ResultV1 {

    private final  boolean isSpam;
    private final  List<SensitiveWordResult> sensitiveWordResultList;
    private final  String htmlHighlightWords;
    private final  double offensiveTextDetPro;
    private final  double advertisingTextDetPro;

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

    public double getAdvertisingTextDetPro() {
        return advertisingTextDetPro;
    }

    public static class Builder {
        private boolean isSpam;

        private List<SensitiveWordResult> sensitiveWordResultList;
        private String htmlHighlightWords;
        private double offensiveTextDetPro;
        private double advertisingTextDetPro;

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

        public Builder advertisingTextDetPro(double advertisingTextDetPro) {
            this.advertisingTextDetPro = advertisingTextDetPro;
            return this;
        }

        public ResultV1 build() {
            return new ResultV1(this);
        }
    }

    private ResultV1(Builder builder) {
        isSpam = builder.isSpam;
        sensitiveWordResultList = builder.sensitiveWordResultList;
        htmlHighlightWords = builder.htmlHighlightWords;
        offensiveTextDetPro = builder.offensiveTextDetPro;
        advertisingTextDetPro = builder.advertisingTextDetPro;
    }

}

