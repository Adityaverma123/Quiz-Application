package com.example.quizapplication;

public class SolutionModel {
    String question,correctAns;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public SolutionModel() {
    }

    public SolutionModel(String question, String correctAns) {
        this.question = question;
        this.correctAns = correctAns;
    }
}
