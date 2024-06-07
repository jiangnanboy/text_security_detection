package com.sy.offensive_text_detection;

import com.sy.util.CollectionUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * @author sy
 * @date 2024/1/7 15:51
 */
public class Init {
    public static String[] model;
    public static Tokenizer tokenizer;

    public static void initModel(String text_detction_model_path, String text_detction_tokenize_path) throws FileNotFoundException {
        System.out.println("Init and Load offensive text detector and tokenizer...");
        List<String> scriptList = CollectionUtil.newArrayList();
        Scanner sc = new Scanner(new File(text_detction_model_path));
        while (sc.hasNextLine()) {
            scriptList.add(sc.nextLine());
        }
        model = scriptList.toArray(new String[0]);
        tokenizer = Tokenizer.loadFromFile(text_detction_tokenize_path);
        }
    }
