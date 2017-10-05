package com.example.edward.triviaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get stats
        int result = (int)(getIntent().getExtras().getDouble(MainActivity.SCORE_KEY));

        // show stats
        TextView resultView = (TextView) findViewById(R.id.percentageView);
        resultView.setText(String.valueOf(result) + "%");
        ProgressBar resultsBar = (ProgressBar) findViewById(R.id.percentageBar);
        resultsBar.setProgress(result);

        // quit button
        Button quitBtn = (Button)findViewById(R.id.quitBtn);
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // start over
        Button tryAgainBtn = (Button)findViewById(R.id.tryAgainBtn);
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            ArrayList<Question> questions = (ArrayList<Question>)getIntent().getExtras().get(MainActivity.QUESTIONS_KEY);
            @Override
            public void onClick(View view) {
                Intent startOver = new Intent(StatsActivity.this, TriviaActivity.class);
                startOver.putExtra(MainActivity.QUESTIONS_KEY, questions);
                startActivity(startOver);
            }
        });
    }

}
