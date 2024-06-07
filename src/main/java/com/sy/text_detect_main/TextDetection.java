package com.sy.text_detect_main;

import com.sy.advertising_text_detection.AdDetector;
import com.sy.offensive_text_detection.OffensiveTextDetector;
import com.sy.sensitivity_word_detection.WordDetector;

import java.util.Optional;

/**
 * @author sy
 * @date 2024/1/7 15:51
 */
public class TextDetection {
    private WordDetector wordDetector = null;
    private OffensiveTextDetector offensiveTextDetector = null;
    private AdDetector adDetector = null;

    public TextDetection(WordDetector wordDetector) {
        this(wordDetector, null);
    }

    public TextDetection(WordDetector wordDetector, OffensiveTextDetector offensiveTextDetector) {
        this(wordDetector, offensiveTextDetector, null);
    }

    public TextDetection(WordDetector wordDetector, OffensiveTextDetector offensiveTextDetector, AdDetector adDetector) {
        this.wordDetector = wordDetector;
        this.offensiveTextDetector = offensiveTextDetector;
        this.adDetector = adDetector;
    }

    public ResultV1 detectionV1(String text) {
        var isSpam = false;
        text = text.trim();
        ResultV1.Builder docBuilder = new ResultV1.Builder();
        if(Optional.ofNullable(this.wordDetector).isPresent()) {
            if(this.wordDetector.findWords(text).size() > 0) { // sensitive words
                isSpam = true;
            }
            docBuilder.sensitiveWordResultList(this.wordDetector.findWords(text))
                    .htmlHighlightWords(this.wordDetector.htmlHighlightWords(text));
        }

        if(Optional.ofNullable(this.offensiveTextDetector).isPresent()) {
            double offensiveTextDetPro = offensiveTextDetector.detection(text);// Anomaly probability
            if(offensiveTextDetPro > 0.5) {
                isSpam = true;
            }
            docBuilder.offensiveTextDetPro(offensiveTextDetPro);
        }

        if(Optional.ofNullable(this.adDetector).isPresent()) {
            double adTextDetPro = adDetector.detection(text); // Advertising probability
            if(adTextDetPro > 0.5) {
                isSpam = true;
            }
            docBuilder.advertisingTextDetPro(adTextDetPro);
        }

        docBuilder.isSpam(isSpam);
        return docBuilder.build();
    }

    public ResultV2 detectionV2(String text) {
        var isSpam = false;
        text = text.trim();
        ResultV2.Builder docBuilder = new ResultV2.Builder();
        double offensiveTextDetPro = offensiveTextDetector.detection(text);// Anomaly probability
        if(offensiveTextDetPro >= 0.9) {
            isSpam = true;
            docBuilder.offensiveTextDetPro(offensiveTextDetPro);
        } else if(offensiveTextDetPro >= 0.8){
            if(this.wordDetector.findWords(text).size() == 0) { // sensitive words
                isSpam = false;
                docBuilder.offensiveTextDetPro(offensiveTextDetPro);
            } else {
                isSpam = true;
                docBuilder.sensitiveWordResultList(this.wordDetector.findWords(text))
                        .htmlHighlightWords(this.wordDetector.htmlHighlightWords(text));
                docBuilder.offensiveTextDetPro(offensiveTextDetPro);
            }
        } else {
            if(this.wordDetector.findWords(text).size() > 0) { // sensitive words
                isSpam = true;
                docBuilder.sensitiveWordResultList(this.wordDetector.findWords(text))
                        .htmlHighlightWords(this.wordDetector.htmlHighlightWords(text));
            } else {
                isSpam = false;
            }
            docBuilder.offensiveTextDetPro(offensiveTextDetPro);
        }

        docBuilder.isSpam(isSpam);
        return docBuilder.build();
    }

}




