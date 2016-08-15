package com.example.happy.primeno;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int number_of_correct_question = 0;
    private int number_of_incorrect_question = 0;
    private int current_question_number = 1;
    private Question question = null;
    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        ((TextView)findViewById(R.id.question)).setText("Press Next Button to Start Quiz");
        ((TextView)findViewById(R.id.status)).setText("Number of question : 0\n" +
                "Number of Correct : 0\n" +
                "Number of Incorrect : 0");

    }
    public void checkQuestion(View v){
        if(this.question == null){
            Toast.makeText(this,"Please click on next Button",Toast.LENGTH_SHORT).show();
            return;
        }
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
    }
    public void checkAnswer(View v,boolean answer){
        if(answer == this.question.getAnswer()){
            Toast.makeText(this," Correct Answer ",Toast.LENGTH_SHORT).show();
            this.number_of_correct_question++;
        }else{
            Toast.makeText(this," Incorrect Answer ",Toast.LENGTH_SHORT).show();
            this.number_of_incorrect_question++;
        }
        ((TextView)findViewById(R.id.status)).setText("No of Question : "+(this.current_question_number-1)+" \nNo of Correct : "+this.number_of_correct_question+"\nNo of Incorrect : "+this.number_of_incorrect_question);
        this.question = null;
    }
    public void nextQuestion(View v){
        if(this.question != null){
            Toast.makeText(this,"Please submit the last question answer",Toast.LENGTH_SHORT).show();
            return;
        }
        TextView question = (TextView)findViewById(R.id.question);
        this.question = new Question();
        String str = "Question : "+this.current_question_number+"\n"+this.question.getNumber()+" is a "+this.question.getQuestion_type()+" number?";
        question.setText(str);
        this.current_question_number++;
    }

    public void resetAll(View v){
        this.current_question_number = 1;
        this.number_of_correct_question = 0;
        this.number_of_incorrect_question = 0;
        this.question = null;
        ((TextView)findViewById(R.id.question)).setText("Press Next Button to Start Quiz");
        ((TextView)findViewById(R.id.status)).setText("Number of question : 0\n" +
                "Number of Correct : 0\n" +
                "Number of Incorrect : 0");
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
}

/*    public void generateQuestionType(){
        int ran = (int)(Math.random()*100)%4;
        if(ran == 0){
            this.questionType = "even";
        }else if(ran == 1){
            this.questionType = "odd";
        }else if(ran == 2){
            this.questionType = "prime";
        }else{
            this.questionType = "not prime";
        }
    }*/
    /*public void generateNumber(){
        this.number = (int)(Math.random()*this.mul)%this.mul;
    }*/

    /*public void setAnswer(View v){
        String type = this.questionType;
        String str = "hello "+this.current_question;
        str = str + " " + type;
        int num = this.number;
        if(type.equals("odd") || type.equals("even")){
            if((num & 1) == 1){
                if(type.equals("odd")){
                    this.answer = true;
                }else{
                    this.answer = false;
                }
            }else{
                if(type.equals("even")){
                    this.answer = true;
                }else{
                    this.answer = false;
                }
            }
        }else{
            boolean prime = true;
            for(int i = 2;i <= Math.sqrt(num);i++){
                if(num%i == 0){
                    prime = false;
                    break;
                }
            }
            if(prime && type.equals("prime")){
                this.answer = true;
            }else if(!prime && type.equals("not prime")){
                this.answer = true;
            }else{
                this.answer = false;
            }
        }
        if(this.status)
            str = str + " true";
        else
            str = str + " false";
    }*/

/*
    public void getMe(View wv){
        TextView text = (TextView)findViewById(R.id.textView);
        text.setText("kaslf");
        Toast.makeText(getBaseContext(),"Hi click working",Toast.LENGTH_LONG).show();
    }*/