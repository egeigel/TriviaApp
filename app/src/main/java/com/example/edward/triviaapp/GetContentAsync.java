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
        try {
            // establish connection
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            // get result of connection
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = IOUtils.toString(connection.getInputStream(), "UTF8");
            }

            // create array of questions
            allQuestions = result.split("\n");

            for (int i = 0 ;i<allQuestions.length; i++){
                // create array with the elements of a question
                ArrayList<String> answers = new ArrayList<>();
                ArrayList<String> questionContent = new ArrayList<String>(Arrays.asList(allQuestions[i].split(";")));
                flag = 0;
                // create and populate question object
                Question question = new Question();
                question.questionNumber = Integer.parseInt(questionContent.get(0));
                question.questionText = questionContent.get(1);
                question.image = questionContent.get(2);
                // add possible answers & correct answer
                for (int j=3; j < questionContent.size() - 1; j++) {
                    buffer = questionContent.get(j);
                    answers.add(buffer);
                }
                String ans = questionContent.get(questionContent.size()-1);
                question.setAnswer(Integer.valueOf(ans));
                question.setAnswers(answers);
                questions.add(question);
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
        ((TextView)activity.findViewById(R.id.readyView)).setText("Trivia Ready");
        ((Button)activity.findViewById(R.id.startButton)).setEnabled(true);
    }
}

