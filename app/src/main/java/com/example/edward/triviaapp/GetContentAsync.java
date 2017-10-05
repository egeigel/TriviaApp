package com.example.edward.triviaapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by edward on 10/3/17.
 */

public class GetContentAsync extends AsyncTask<String , Void , ArrayList<Question>> {
    String[] allQuestions;
    String result;
    Activity activity;

    public GetContentAsync(Activity activity){
        this.activity = activity;

    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {ArrayList<Question> questions=new ArrayList<>();
        String buffer;
        int flag;

        HttpURLConnection connection;
        try{
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();

            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = IOUtils.toString(connection.getInputStream(), "UTF8");
            }

            allQuestions = result.split("\n");

            for (int i =0 ;i<allQuestions.length; i++){
                ArrayList<String> answers= new ArrayList<>();
                flag = 0;
                ArrayList<String> questionContent = new ArrayList<String>(Arrays.asList(allQuestions[i].split(";")));
                Question question = new Question();
                question.questionNumber = Integer.parseInt(questionContent.get(0));
                question.questionText = questionContent.get(1);
                question.image = questionContent.get(2);
                for (int j=3; flag<1; j++) {
                    buffer = questionContent.get(j);
                    try {
                        int ansBuf = Integer.parseInt(buffer);
                        question.answer = ansBuf;
                        flag++;
                    } catch (NumberFormatException e) {
                        answers.add(buffer);
                    }
                }
                question.setAnswers(answers);
                questions.add(question);

                //answers.clear();
            }
            return questions;


        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        ((ProgressBar)activity.findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
        ((ImageView)activity.findViewById(R.id.triviaImage)).setVisibility(View.VISIBLE);
        ((TextView)activity.findViewById(R.id.readyView)).setVisibility(View.VISIBLE);
        ((Button)activity.findViewById(R.id.startButton)).setEnabled(true);
    }
}

