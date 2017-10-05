package com.example.edward.triviaapp;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by edward on 10/3/17.
 */

public class Question implements Serializable {
    int questionNumber;
    String questionText;
    String image;
    ArrayList<String> answers;
    int answer;

    public Question(){
        questionNumber=0;
        questionText="";
        image="";
        answers = new ArrayList<>();
        answer=0;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return getQuestionNumber()+". "+getQuestionText()+getAnswers().toString() +"Answer is: "+getAnswer();
    }
}
