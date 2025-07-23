package com.untildawn.controllers.utils;//package org.example.Controllers.InGameMenuController;
//
//import edu.stanford.nlp.pipeline.*;
//import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
//import edu.stanford.nlp.ling.CoreAnnotations;
//import edu.stanford.nlp.util.CoreMap;
//
//import java.util.*;
//
//public class SentimentAnalyzer {
//
//    private final StanfordCoreNLP pipeline;
//
//    public SentimentAnalyzer() {
//        Properties props = new Properties();
//        props.setProperty("annotators", "tokenize,ssplit,pos,parse,sentiment");
//        pipeline = new StanfordCoreNLP(props);
//    }
//
//    public int getXpDelta(String message) {
//        if (message == null || message.isEmpty()) return 10;
//
//        String lowerMsg = message.toLowerCase();
//
//        Map<String, Integer> keywordScores = Map.ofEntries(
//                // good words
//                Map.entry("you rock", 9),
//                Map.entry("awesome", 8),
//                Map.entry("amazing", 9),
//                Map.entry("keep it up", 7),
//                Map.entry("well played", 8),
//                Map.entry("nicely done", 7),
//                Map.entry("good job", 8),
//                Map.entry("good luck", 6),
//                Map.entry("you're the best", 10),
//                Map.entry("nice try", 6),
//                Map.entry("you got this", 7),
//                Map.entry("proud of you", 9),
//                Map.entry("that was kind", 8),
//                Map.entry("cheers", 5),
//                Map.entry("thankful", 6),
//                Map.entry("peace", 5),
//                Map.entry("beautiful", 9),
//                Map.entry("smile", 4),
//                Map.entry("bless you", 5),
//                Map.entry("happy to help", 6),
//                // bad words
//                Map.entry("sucks", -8),
//                Map.entry("worst", -10),
//                Map.entry("you are nothing", -15),
//                Map.entry("shut your mouth", -14),
//                Map.entry("get a life", -12),
//                Map.entry("go away", -9),
//                Map.entry("jerk", -10),
//                Map.entry("freak", -8),
//                Map.entry("you suck", -13),
//                Map.entry("lame", -7),
//                Map.entry("disgusting", -10),
//                Map.entry("cringe", -6),
//                Map.entry("pathetic", -11),
//                Map.entry("what a joke", -9),
//                Map.entry("you're trash", -13),
//                Map.entry("toxic", -7),
//                Map.entry("idiotic", -10),
//                Map.entry("retard", -15),
//                Map.entry("fat", -8),
//                Map.entry("kill yourself", -20),
//                Map.entry("shit", -15),
//                Map.entry("fuck", -20)
//
//        );
//
//        int keywordScore = 0;
//        boolean keywordMatched = false;
//
//        for (Map.Entry<String, Integer> entry : keywordScores.entrySet()) {
//            if (lowerMsg.contains(entry.getKey())) {
//                keywordScore += entry.getValue();
//                keywordMatched = true;
//            }
//        }
//
//        // Sentiment analysis
//        Annotation annotation = pipeline.process(message);
//        int sentimentScore = 0;
//        int count = 0;
//
//        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
//            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
//            int score = switch (sentiment) {
//                case "Very positive" -> 20;
//                case "Positive" -> 10;
//                case "Neutral" -> 0;
//                case "Negative" -> -10;
//                case "Very negative" -> -20;
//                default -> 0;
//            };
//            sentimentScore += score;
//            count++;
//        }
//
//        int averageSentiment = count == 0 ? 0 : sentimentScore / count;
//
//        int finalScore;
//        if (!keywordMatched && averageSentiment == 0) {
//            finalScore = 10;
//        } else {
//            finalScore = keywordScore + averageSentiment;
//        }
//
//        return Math.max(-20, Math.min(20, finalScore));
//    }
//}
