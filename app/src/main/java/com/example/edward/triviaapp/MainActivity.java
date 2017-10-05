package com.example.edward.triviaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Button exitButton;
    ImageView triviaImage;
    TextView readyView;
    ArrayList<Question> questions = new ArrayList<>();
    ProgressBar progressBar;

    public static final String QUESTIONS_KEY = "QUESTIONS";
    public static final String SCORE_KEY = "SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        exitButton = (Button)findViewById(R.id.exitButton);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        triviaImage = (ImageView) findViewById(R.id.triviaImage);
        readyView = (TextView) findViewById(R.id.readyView);

        triviaImage.setEnabled(false);
        readyView.setEnabled(false);

        // get content
        if (isConnected()){
            AsyncTask asyncTask = new GetContentAsync(this).execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");
            try {
                questions = (ArrayList<Question>)asyncTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // send array of questions to triva activity
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TriviaActivity.class);
                intent.putExtra(QUESTIONS_KEY, questions);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean isConnected() {
        // get connectivity manager w/ network info
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // basic connection check
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}




