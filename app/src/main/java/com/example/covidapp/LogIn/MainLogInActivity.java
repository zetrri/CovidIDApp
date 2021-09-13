package com.example.covidapp.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.MainActivity;
import com.example.covidapp.R;

public class MainLogInActivity extends AppCompatActivity {
    private EditText eEmail;
    private EditText ePassword;
    private Button eLogin;
    private TextView eAttemptsInfo;

    private final String Username = "Admin@gmail.com";
    private final String Password = "Admin";

    boolean isValid = false;
    private int counter = 5;
    CountDownTimer cTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);
        //binding variable to xml layout
        eEmail = findViewById(R.id.etEmail);
        ePassword = findViewById(R.id.etPassword);
        eLogin = findViewById(R.id.btnLogin);
        eAttemptsInfo = findViewById(R.id.AttemptsInfo);

        //listener
        eLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String inputEmail = eEmail.getText().toString(); //get input from user and convert to string
                String inputPassword = ePassword.getText().toString();

                if(inputEmail.isEmpty() || inputPassword.isEmpty()) //error message for empty field
                {
                    Log.i("Error", "Empty input field from user!"); //logging
                    Toast.makeText(getBaseContext(), "Please enter something in empty field!", Toast.LENGTH_SHORT).show();
                }
                else if (!isEmailValid(inputEmail))
                {
                    //checking for email
                    Log.i("Error", "Incorrect Email format!"); //logging
                    Toast.makeText(getBaseContext(), "Please enter a correct email!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    isValid = validate(inputEmail, inputPassword);
                    if(!isValid)
                    {
                        counter--;

                        Log.i("Error", "Incorrect credentials from user!"); //logging

                        Toast.makeText(getBaseContext(), "Incorrect credentials entered!", Toast.LENGTH_SHORT).show();

                        eAttemptsInfo.setText("Attempts remaining: " + counter); // displays amount of remaining attempts

                        if(counter == 0) //0 attempts left
                        {
                            eLogin.setEnabled(false); //hej
                            CDTimer();
                        }
                    }
                    else //right credentials
                    {
                        Log.i("Success", "Login Successful!"); //logging

                        Toast.makeText(getBaseContext(), "Login Successful!", Toast.LENGTH_SHORT).show(); //print

                        //add code to go to new activity
                        Intent intent = new Intent(getBaseContext(), MainActivity.class); //insert which activity to intent
                        startActivity(intent);
                    }
                }
            }
        });
    }
    private void CDTimer()
    {
        //creating timer with 60 seconds
        cTimer = new CountDownTimer(10000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                eAttemptsInfo.setText("There has been too many login failures from your device. Please try again in: " + millisUntilFinished / 1000 + " seconds."); // displays timer instead of attempts remaining
            }
            public void onFinish()
            {
                counter = 5; // sets remaining attempts to 5
                eLogin.setEnabled(true); //enabling login
                eAttemptsInfo.setText("Attempts remaining: " + counter); // displays amount of remaining attempts
            }
        };
        cTimer.start();
    }

    private boolean validate(String name, String password) //method for checking if user & password matches
    {
        if(name.equals(Username) && password.equals(Password))
        {
            return true;
        }
        return false;
    }
    private boolean isEmailValid(CharSequence email)
    {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    }
