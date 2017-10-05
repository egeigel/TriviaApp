package com.example.edward.triviaapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {

    RadioGroup answerChoices;
    ArrayList<Question> questions;
    ImageView questionImage;
    TextView numberText;
    TextView timeText;
    TextView questionText;
    ProgressBar imageProgress;
    Button nextBtn;
    Button quitBtn;
    int score = 0;
    int currCorrectAnswer = 0;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        // connect to views
        questions = (ArrayList<Question>)getIntent().getExtras().get(MainActivity.QUESTIONS_KEY);
        imageProgress = (ProgressBar)findViewById(R.id.imageProgress);
        answerChoices = (RadioGroup)findViewById(R.id.answerGroup);
        questionImage = (ImageView)findViewById(R.id.questionImage);
        numberText = (TextView)findViewById(R.id.numberText);
        timeText = (TextView)findViewById(R.id.timeText);
        questionText = (TextView)findViewById(R.id.questionText);
        nextBtn = (Button) findViewById(R.id.nextButton);
        quitBtn = (Button) findViewById(R.id.quitButton);

        // start timer
        CountDownTimer countDown = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time Left: " + millisUntilFinished / 1000 + " seconds");
            }
            public void onFinish() {
                goToStats();
            }
        }.start();

        // show first question
        showQuestion(0);

        // set next button to show next question
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check answer
                if (currCorrectAnswer == answerChoices.getCheckedRadioButtonId()){
                    score++;
                }
                // go to next or finish activity
                if (index < questions.size()){
                    showQuestion(index);
                } else {
                    goToStats();
                }
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showQuestion(int questionIndex){
        Question q = questions.get(index);
        setImage(q);
        // reset q & a
        answerChoices.removeAllViews();
        numberText.setText("Q" + (q.getQuestionNumber() + 1));
        currCorrectAnswer = q.getAnswer();
        // dynamically generate radio buttons for answers
        ArrayList<String> answers = q.getAnswers();
        questionText.setText(q.getQuestionText());
        for (int i = 0; i < answers.size(); i++){
            RadioButton rb = new RadioButton(TriviaActivity.this);
            rb.setText(answers.get(i));
            rb.setId(i);
            answerChoices.addView(rb);
        }
        index++;
    }

    private void goToStats(){
        Intent toStats = new Intent(TriviaActivity.this, StatsActivity.class);
        // pass percentage of correct answers
        double finalScore = (double)score / (double)questions.size() * 100;
        toStats.putExtra(MainActivity.SCORE_KEY, finalScore);
        toStats.putExtra(MainActivity.QUESTIONS_KEY, questions);
        startActivity(toStats);
        finish();
    }

    private void setImage(Question q){
        if (!q.getImage().toString().trim().isEmpty()) {
            new GetImageAsync(TriviaActivity.this, questionImage).execute(q.getImage());
        } else {
            questionImage.setVisibility(View.INVISIBLE);
        }
    }
}
