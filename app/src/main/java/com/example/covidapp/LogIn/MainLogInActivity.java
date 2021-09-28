package com.example.covidapp.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.Dashboard.MainDashboard;
import com.example.covidapp.HealthAdmin.AdminMenu;
import com.example.covidapp.MainActivity;
import com.example.covidapp.MyPage.MainMyPage;
import com.example.covidapp.R;
import com.example.covidapp.UserReg.MainUserRegActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainLogInActivity extends AppCompatActivity
{
    private EditText eEmail;
    private EditText ePassword;
    private Button eLogin;
    private TextView eAttemptsInfo;
    private Button eForgot;
    private Button eSignup;

    //used to show progress while logging in
    private android.app.ProgressDialog ProgressDialog;

    private FirebaseAuth firebaseAuth;

    //Dummmy admin login
    private final String Username = "Admin@gmail.com";
    private final String Password = "Admin";
    //Dummy user login
    private final String Username1 = "User@gmail.com";
    private final String Password1 = "User";

    //used for checking input email and pass
    boolean isValid = false;
    boolean isValid1 = false;

    private int counter = 5;
    CountDownTimer cTimer = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);
        //binding variable to xml layout
        eEmail = findViewById(R.id.etEmail);
        ePassword = findViewById(R.id.etPassword);
        eLogin = findViewById(R.id.btnLogin);
        eAttemptsInfo = findViewById(R.id.AttemptsInfo);
        eForgot = findViewById(R.id.btnForgot);
        eSignup = findViewById(R.id.btnSignup);

        //progress dialog(shows a loading wheel)
        ProgressDialog = new ProgressDialog(this);


        //check if user is already logged in.
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    //do this if the user is logged in already.
                    Toast.makeText(getBaseContext(), "You are logged in!!", Toast.LENGTH_SHORT).show();
                    Log.i("Error", "User already logged in!"); //logging
                    finish();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(authStateListener);

        //listeners
        //user reg
        eSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainUserRegActivity.class);
                startActivity(intent);
            }
        });

        eForgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PasswordActivity.class);
                startActivity(intent);
            }
        });

        //login
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
                    //Methods to check if email and pass matches our dummy admin & user accounts
                    //isValid = validate(inputEmail, inputPassword);
                    //isValid1 = validate1(inputEmail, inputPassword);
                    validate(inputEmail, inputPassword);
                    /*else if (isValid1) //right credentials for User Login
                    {
                        Log.i("Success", "User Login Successful!"); //logging

                        Toast.makeText(getBaseContext(), "Login Successful!", Toast.LENGTH_SHORT).show(); //print

                        //add code to go to new activity
                        Intent intent = new Intent(getBaseContext(), MainMyPage.class);
                        startActivity(intent);
                    }*/
                    }
                }
        });


    }
    private void CDTimer()
    {
        //creating timer with 5 seconds for testing(should be set to a minute+ otherwise)
        cTimer = new CountDownTimer(5000, 1000) 
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

    private boolean validate(String name, String password) //Admin method for checking if user & password matches
    {
        ProgressDialog.setMessage("Verification in progress . . . ");
        ProgressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //direct to activity if login successful
                if (task.isSuccessful())
                {
                    Log.i("Success", "User Login Successful!"); //logging
                    ProgressDialog.dismiss();;
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                //wrong credentials
                else
                {
                    Toast.makeText(getBaseContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                    counter--;
                    eAttemptsInfo.setText("Attempts remaining: " + counter); // displays amount of remaining attempts
                    ProgressDialog.dismiss();
                    if(counter == 0)
                    {
                        CDTimer();
                        eLogin.setEnabled(false);
                    }
                }
            }
        });

        if(name.equals(Username) && password.equals(Password))
        {
            return true;
        }
        return false;
    }

    private boolean validate1(String name, String password) //User method for checking if user & password matches
    {
        if(name.equals(Username1) && password.equals(Password1))
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

