package com.example.android.util;


import android.util.Log;

import java.util.Random;

public class IFlySpeechUtils {

    public static final double sentenceLimitScore = 2;

    private static final double wordLimitScore = 2;

    private static final double benchmarkScore = 70;

    private static final double acuracyCoeff = 0.849;

    private static final double constant = 5.932;

    private static final double fluencyCoeff = 0.964;

    public static int getWordScore(Result result){
        //单词成句的打分算法
        int scoreResult;

        Random random = new Random();

        if((result.total_score == 0) || (result.total_score > 0 && result.total_score < 15)) {
            int randomNumber = 10 + random.nextInt(6);
            scoreResult = randomNumber;
        } else if(result.total_score > 15 && result.total_score < 30){
            int num = 20 + random.nextInt(31);//15到30
            scoreResult = num;
        } else if(result.total_score > 30) {
            int num = 50 + random.nextInt(50);
            scoreResult = num;
        } else {
            scoreResult = 30 + random.nextInt(10); // 默认返回原始得分
        }

//        if (result.accuracy_score < wordLimitScore) {
//            scoreResult = (int) (result.accuracy_score * (benchmarkScore / sentenceLimitScore));
//        } else {
//            scoreResult = (int)(result.accuracy_score / 5.0 * 100);
//            if (scoreResult >= 95) {
//                scoreResult = 100;
//            } else if (scoreResult >= 85) {
//                scoreResult += 5;
//            } else  if (scoreResult >= 80) {
//                scoreResult += 8;
//            } else if (scoreResult >= 70){
//                scoreResult += 10;
//            }
//        }
        return scoreResult;
    }

    public static int getSentenceScore(Result result) {
        int score = 0;
        if (result.accuracy_score < sentenceLimitScore) {
            score = (int) (35 * result.accuracy_score);
            if (score > 80 && score < 94) {
                score = score + 3;
            } else if (score > 94) {
                score = 100;
            }
        } else {
            if (result.fluency_score == 0) {
                score = (int) (70 + 30 * (1 / (1 + 1 / (Math.pow(Math.E, (acuracyCoeff * result.accuracy_score + fluencyCoeff * result.accuracy_score - fluencyCoeff))))));
            } else {
                score = (int) (70 + 30 * (1 / (1 + 1 / (Math.pow(Math.E, (acuracyCoeff * result.accuracy_score + fluencyCoeff * result.fluency_score - fluencyCoeff))))));
            }
            if (score > 80 && score < 94) {
                score = score + 3;
            } else if (score > 94) {
                score = 100;
            }
        }
        return score;
    }
}