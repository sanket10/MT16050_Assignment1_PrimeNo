package com.example.happy.primeno;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String tag = "MainActivity";
    private static String state_number_of_correct_question = "NumberOfCorrectQuestion";
    private static String state_number_of_incorrect_question = "NumberOfIncorrectQuestion";
    private static String state_current_question_number = "CurrentQuestionNumber";
    private static String state_question_type = "QuestionType";
    private static String state_question_number = "QuestionNumber";
    private static String state_question_answer = "QuestionAnswer";
    private static String state_question_state = "QuestionState";

    private boolean question_state = true; //question state means whether user answered the question or not
    private int number_of_correct_question = 0;
    private int number_of_incorrect_question = 0;
    private int current_question_number = 0;
    private Question question = null;
    private Button yes_button;
    private Button no_button;
    private Button next_button;
    private Button restart_button;

    @Override
    public void onResume(){
        super.onResume();
        yes_button = (Button)findViewById(R.id.yes);
        no_button = (Button)findViewById(R.id.no);
        next_button = (Button)findViewById(R.id.next);
        restart_button = (Button)findViewById(R.id.restart);
        yes_button.setBackgroundColor(Color.LTGRAY);
        no_button.setBackgroundColor(Color.LTGRAY);
        next_button.setBackgroundColor(Color.LTGRAY);
        restart_button.setBackgroundColor(Color.LTGRAY);
        yes_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                checkQuestion(view);
            }
        });
        no_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                checkQuestion(view);
            }
        });
        next_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                nextQuestion(view);
            }
        });
        restart_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                resetAll(view);
            }
        });
    }

    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag,"Enter onCreate()");
        String question_field = "Press Next Button to Start Quiz";
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        int temp_current_question_number = this.current_question_number;
        if(savedInstanceState != null && savedInstanceState.getInt(MainActivity.state_current_question_number) != 0){
            Log.d(tag,"onCreate inside savedInstanceState");
            this.question = new Question(savedInstanceState.getString(MainActivity.state_question_type),savedInstanceState.getInt(MainActivity.state_question_number),savedInstanceState.getBoolean(MainActivity.state_question_answer));
            this.current_question_number = savedInstanceState.getInt(MainActivity.state_current_question_number);
            question_field = "Question : "+(this.current_question_number)+"\n"+this.question.getNumber()+" is a "+this.question.getQuestion_type()+" number?";
            this.number_of_correct_question = savedInstanceState.getInt(MainActivity.state_number_of_correct_question);
            this.number_of_incorrect_question = savedInstanceState.getInt(MainActivity.state_number_of_incorrect_question);
            temp_current_question_number = this.current_question_number - 1;
            if(this.question_state = savedInstanceState.getBoolean(MainActivity.state_question_state)){
                temp_current_question_number++;
            }
        }

        //First time view to end user
        ((TextView)findViewById(R.id.question)).setText(question_field);
        ((TextView)findViewById(R.id.status)).setText("No of Question : "+(temp_current_question_number)+" \nNo of Correct : "+this.number_of_correct_question+"\nNo of Incorrect : "+this.number_of_incorrect_question);

        Log.d(tag,"Return onCreate()");
    }


    //Check the questions on user click
    public void checkQuestion(View v){
        //user can't click on yes/no button till he's not press next question button
        Log.d(tag,"Enter checkQuestion()");
        if(this.question == null || this.question_state){
            Toast.makeText(this,"Please click on next Button",Toast.LENGTH_SHORT).show();
            return;
        }

        //checking which botton user clicks
        switch (v.getId()){
            case R.id.yes:
                checkAnswer(v,true);
                break;
            case R.id.no:
                checkAnswer(v,false);
                break;
            default:
                Toast.makeText(this,"Please click only Yes/No",Toast.LENGTH_SHORT).show();
        }
        this.question_state = true;
        Log.d(tag,"Return checkQuestion()");
    }

    //check question after user's pressing button
    public void checkAnswer(View v,boolean answer){
        Log.d(tag,"Enter checkAnswer()");
        if(answer == this.question.getAnswer()){
            if(answer){
                //yes_button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                yes_button.setBackgroundColor(Color.GREEN);
            }else{
                //no_button.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                no_button.setBackgroundColor(Color.GREEN);
            }
            Toast.makeText(this," Correct Answer ",Toast.LENGTH_SHORT).show();
            this.number_of_correct_question++;
        }else{
            if(answer){
                yes_button.setBackgroundColor(Color.RED);
                no_button.setBackgroundColor(Color.GREEN);
            }else{
                yes_button.setBackgroundColor(Color.GREEN);
                no_button.setBackgroundColor(Color.RED);
            }
            Toast.makeText(this," Incorrect Answer ",Toast.LENGTH_SHORT).show();
            this.number_of_incorrect_question++;
        }
        ((TextView)findViewById(R.id.status)).setText("No of Question : "+(this.current_question_number)+" \nNo of Correct : "+this.number_of_correct_question+"\nNo of Incorrect : "+this.number_of_incorrect_question);
        this.question_state = true; // user can press Yes/No button only once for each question

        Log.d(tag,"Return checkAnswer()");
    }

    //Method execute after pressing next button. This method generate new question
    public void nextQuestion(View v){
        Log.d(tag,"Enter nextQuestion()");
        yes_button.setBackgroundColor(Color.LTGRAY);
        no_button.setBackgroundColor(Color.LTGRAY);
        if(this.question_state == false){
            Toast.makeText(this,"Please submit the last question answer",Toast.LENGTH_SHORT).show();
            return;
        }
        this.current_question_number++;
        TextView question = (TextView)findViewById(R.id.question);
        this.question = new Question(); //generate a new random question
        String generated_question = "Question : "+this.current_question_number+"\n"+this.question.getNumber()+" is a "+this.question.getQuestion_type()+" number?";
        question.setText(generated_question);
        this.question_state = false;
        Log.d(tag,"Return nextQuestion()");
    }

    //This method link with reset button and this method reset complete quiz
    public void resetAll(View v){
        Log.d(tag,"Enter resetAll()");
        this.current_question_number = 0;
        this.number_of_correct_question = 0;
        this.number_of_incorrect_question = 0;
        yes_button.setBackgroundColor(Color.LTGRAY);
        no_button.setBackgroundColor(Color.LTGRAY);
        this.question = null;
        this.question_state = true;
        ((TextView)findViewById(R.id.question)).setText("Press Next Button to Start Quiz");
        ((TextView)findViewById(R.id.status)).setText("Number of question : 0\n" +
                "Number of Correct : 0\n" +
                "Number of Incorrect : 0");
        Log.d(tag,"Return resetAll()");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        Log.d(tag,"Enter onSaveInstaceState()");
        if(this.current_question_number == 0){
            Log.d(tag,"Return onSaveInstanceState()");
            return;
        }

        Log.d(tag,"onSaveInstanceState on Rotation");
        outState.putInt(MainActivity.state_current_question_number,this.current_question_number);
        outState.putInt(MainActivity.state_number_of_correct_question,this.number_of_correct_question);
        outState.putInt(MainActivity.state_number_of_incorrect_question,this.number_of_incorrect_question);
        outState.putString(MainActivity.state_question_type,this.question.getQuestion_type());
        outState.putInt(MainActivity.state_question_number,this.question.getNumber());
        outState.putBoolean(MainActivity.state_question_answer,this.question.getAnswer());
        outState.putBoolean(MainActivity.state_question_state,this.question_state);
        super.onSaveInstanceState(outState);
        Log.d(tag,"Return onSaveInstanceState()");
    }
}

