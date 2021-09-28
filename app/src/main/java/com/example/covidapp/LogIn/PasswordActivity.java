package com.example.covidapp.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covidapp.MainActivity;
import com.example.covidapp.R;
import com.example.covidapp.UserReg.MainUserRegActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        passwordEmail = findViewById(R.id.PasswordEmail);
        resetPassword = findViewById(R.id.btnPasswordReset);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //converting the text to string, removes whitespace etc.
                String useremail = passwordEmail.getText().toString().trim();
                //if empty
                if(useremail.isEmpty())
                {
                    Toast.makeText(getBaseContext(), "Please enter something in empty field!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //communicate with firebase passwordemail reset
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //the email is sent,
                            if(task.isSuccessful()){
                                Toast.makeText(getBaseContext(), "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(getBaseContext(), MainLogInActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getBaseContext(), "Error sending password reset email!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}