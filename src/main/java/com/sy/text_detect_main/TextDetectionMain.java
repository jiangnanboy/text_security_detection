package com.sy.text_detect_main;

import com.sy.advertising_text_detection.AdDetector;
import com.sy.offensive_text_detection.OffensiveTextDetector;
import com.sy.sensitivity_word_detection.SimpleSenDetectionProcessor;
import com.sy.util.PropertiesReader;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sy
 * @date 2024/1/7 15:51
 */
public class TextDetectionMain {
    TextDetection textDetection;

    public TextDetectionMain () {
        var path = TextDetectionMain.class.getClassLoader().getResource("").getPath();
        if(path.substring(0, 1).equals("/")) {
            path = path.substring(1);
        }
        var simpleSenDetectionProcessor = SimpleSenDetectionProcessor.newInstance();
        var wordDetector = simpleSenDetectionProcessor.getKWSeeker("sensitive_words_path");

        var offensive_text_detction_model_path = path + PropertiesReader.get("offensive_text_detction_model_path");
        var offensive_text_detction_tokenize_path = path + PropertiesReader.get("offensive_text_detction_tokenize_path");
        var offensiveTextDetector = new OffensiveTextDetector(offensive_text_detction_model_path, offensive_text_detction_tokenize_path);

        var ad_detect_model_path = path + PropertiesReader.get("ad_detect_model_path");
        var ad_dict_path = path + PropertiesReader.get("ad_dict_path");
        var stop_words_path = path + PropertiesReader.get("stop_words_path");
        var adDetector = new AdDetector(ad_detect_model_path, ad_dict_path, stop_words_path);

        textDetection = new TextDetection(wordDetector, offensiveTextDetector, adDetector);
    }

    public ResultV1 detectionV1(String text) throws Exception {
        if(StringUtils.isNotBlank(text)) {
            return this.textDetection.detectionV1(text);
        } else {
            throw new Exception("No text!");
        }
    }

    public ResultV2 detectionV2(String text) throws Exception {
        if(StringUtils.isNotBlank(text)) {
            return this.textDetection.detectionV2(text);
        } else {
            throw new Exception("No text!");
        }
    }

}


