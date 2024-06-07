package com.sy;

import com.alibaba.fastjson2.JSON;
import com.sy.text_detect_main.ResultV1;
import com.sy.text_detect_main.ResultV2;
import com.sy.text_detect_main.TextDetectionMain;

/**
 * @author sy
 * @date 2024/1/7 15:51
 */
public class MainTest {
    public static void main(String...args) {
        var testInput = "一，凡是未开工的违规项目，一律不得开工建设；二，凡是不符合产业政策、准入标准、环保要求的违规项目一律停建。";
//        testInput = "草你妈的，什么傻逼玩意！";
        var textDetectionMain = new TextDetectionMain();
        try {
            ResultV1 result = textDetectionMain.detectionV1(testInput);
            System.out.println(JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("========================================");
        try {
            ResultV2 result = textDetectionMain.detectionV2(testInput);
            System.out.println(JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
