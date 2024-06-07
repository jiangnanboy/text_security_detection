package com.sy.advertising_text_detection;

import ai.onnxruntime.*;
import com.sy.util.CollectionUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sy.advertising_text_detection.Init.*;

/**
 * @author sy
 * @date 2024/1/7 15:51
 */
public class AdDetector {

    public AdDetector(String ad_detect_model_path, String ad_dict_path, String stop_words_path) {
        try {
            initModel(ad_detect_model_path);
        } catch (OrtException e) {
            e.printStackTrace();
        }
        initDict(ad_dict_path);
        initStopWords(stop_words_path);
    }

    /**
     * The probability of ad
     * @param sent
     * @return
     */
    public double detection(String sent) {
        double prob = 0;
        try {
            Map<String, OnnxTensor> inputMap = parse(sent);
            prob = infer(inputMap);
        } catch (OrtException e) {
            e.printStackTrace();
        }
        return prob;
    }

    public Map<String, OnnxTensor> parse(String sent) throws OrtException {
        return parse(sent, 500);
    }

    public Map<String, OnnxTensor> parse(String sent, int maxLength) throws OrtException {
        var tokenList = CollectionUtil.newArrayList();
        for(var i=0;i<sent.length();i++) {
            tokenList.add(sent.substring(i, i+1));
        }
//        tokenList = tokenList.stream().filter(token -> !Init.stopWords.contains(token)).collect(Collectors.toList());
        if(tokenList.size() > maxLength) {
            tokenList = tokenList.subList(0, maxLength - 1);
        } else if(tokenList.size() < maxLength) {
            var range = maxLength - tokenList.size();
            for(var i=0; i<range; i++) {
                tokenList.add("<pad>");
            }
        }
        var tokenIds = tokenList.stream().map(token -> Init.dict.getOrDefault(token, 0L)).collect(Collectors.toList());
        var inputIds = new long[tokenIds.size()];
        for(var i=0; i<tokenIds.size(); i++) {
            inputIds[i] = tokenIds.get(i);
        }
        var shape = new long[]{1, inputIds.length};
        var ObjInputIds = OrtUtil.reshape(inputIds, shape);
        var inputOnnx = OnnxTensor.createTensor(Init.env, ObjInputIds);
        Map<String, OnnxTensor> inputMap = CollectionUtil.newHashMap();
        inputMap.put("input_1", inputOnnx);
        return inputMap;
    }

    public double infer(Map<String, OnnxTensor> inputs) {
        var prob = 0.;
        try (OrtSession.Result result = Init.session.run(inputs)) {
            var onnxValue = result.get(0);
            var labels = (float[][])onnxValue.getValue();
            var resultLabels = labels[0];
            var softmaxResults = softmax(resultLabels);
            prob = getProb(softmaxResults);
        } catch (OrtException e) {
            e.printStackTrace();
        }
        return prob;
    }

    public double getMaxProb(double[] probabilities) {
        double maxVal = Float.NEGATIVE_INFINITY;
        for (var i = 0; i < probabilities.length; i++) {
            if (probabilities[i] > maxVal) {
                maxVal = probabilities[i];
            }
        }
        return maxVal;
    }

    public double getProb(double[] probabilities) {
        var prob = probabilities[1];
        return prob;
    }

    private double[] softmax(float[] input) {
        List<Float> inputList = CollectionUtil.newArrayList();
        for(var i=0; i<input.length; i++) {
            inputList.add(input[i]);
        }
        var inputSum = inputList.stream().mapToDouble(Math::exp).sum();
        var output = new double[input.length];
        for (var i=0; i<input.length; i++) {
            output[i] = (Math.exp(input[i]) / inputSum);
        }
        return output;
    }

}


