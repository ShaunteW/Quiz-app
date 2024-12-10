package org.forestpark.quizappschoolsw;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button trueButton, falseButton;
    Button submitButton;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        submitButton = findViewById(R.id.submit_button);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions: " + totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View v) {
        trueButton.setBackgroundColor(Color.BLACK);
        falseButton.setBackgroundColor(Color.BLACK);

        Button clickedButton = (Button) v;
        if (clickedButton.getId() == R.id.submit_button) {
            finishQuiz();
        } else {
            // True/False button clicked
            boolean userAnswer = clickedButton.getId() == R.id.true_button;
            checkAnswer(userAnswer);
        }
    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = QuestionAnswer.correctAnswer[currentQuestionIndex].equals("True");

        if (userAnswer == correctAnswer) {
            score++; // Correct answer
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < totalQuestion) {
            loadNewQuestion();
        } else {
            finishQuiz();
        }
    }

    void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        trueButton.setText("True");
        falseButton.setText("False");
    }

    void finishQuiz() {
        String passStatus = (score > totalQuestion * 0.60) ? "Passed" : "Failed";

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}
