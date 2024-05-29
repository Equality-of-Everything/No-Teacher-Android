package com.example.android.util;


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
        if (result.accuracy_score < wordLimitScore) {
            scoreResult = (int) (result.accuracy_score * (benchmarkScore / sentenceLimitScore));
        } else {
            scoreResult = (int)(result.accuracy_score / 5.0 * 100);
            if (scoreResult >= 95) {
                scoreResult = 100;
            } else if (scoreResult >= 85) {
                scoreResult += 5;
            } else  if (scoreResult >= 80) {
                scoreResult += 8;
            } else if (scoreResult >= 70){
                scoreResult += 10;
            }
        }
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