package com.example.edward.triviaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        questions = (ArrayList<Question>)getIntent().getExtras().get("QUESTIONS");
        answerChoices = (RadioGroup)findViewById(R.id.answerGroup);
        questionImage = (ImageView)findViewById(R.id.questionImage);
        numberText = (TextView)findViewById(R.id.numberText);
        timeText = (TextView)findViewById(R.id.timeText);
        questionText = (TextView)findViewById(R.id.questionText);

        for(Question q : questions){
            new GetImageAsync(TriviaActivity.this , questionImage).execute(q.getImage());
            numberText.setText("Q"+q.getQuestionNumber());


        }

    }
}
