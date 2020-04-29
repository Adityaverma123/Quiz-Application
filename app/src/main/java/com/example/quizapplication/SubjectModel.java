package com.example.quizapplication;

public class SubjectModel {
    private String title;
    private String description;

    public SubjectModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SubjectModel(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
