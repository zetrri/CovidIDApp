package com.example.covidapp.LogIn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.Dashboard.MainDashboard;
import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentLoginBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private EditText eEmail;
    private EditText ePassword;
    private Button eLogin;
    private TextView eAttemptsInfo;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //binding variable to xml layout
        eEmail = binding.etEmail;
        ePassword = binding.etPassword;
        eLogin = binding.btnLogin;
        eAttemptsInfo = binding.AttemptsInfo;

        //listener
        eLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String inputEmail = eEmail.getText().toString(); //get input from user and convert to string
                String inputPassword = ePassword.getText().toString();

                if(inputEmail.isEmpty() || inputPassword.isEmpty()) //error message for empty field
                {
                    Log.i("Error", "Empty input field from user!"); //logging
                    Toast.makeText(getActivity().getBaseContext(), "Please enter something in empty field!", Toast.LENGTH_SHORT).show();
                }
                else if (!isEmailValid(inputEmail))
                {
                    //checking for email
                    Log.i("Error", "Incorrect Email format!"); //logging
                    Toast.makeText(getActivity().getBaseContext(), "Please enter a correct email!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //Methods to check if email and pass matches our dummy admin & user accounts
                    isValid = validate(inputEmail, inputPassword);
                    isValid1 = validate1(inputEmail, inputPassword);
                    if(isValid) // right credentials for Admin Login
                    {
                        Log.i("Success", "User Login Successful!"); //logging

                        Toast.makeText(getActivity().getBaseContext(), "Login Successful!", Toast.LENGTH_SHORT).show(); //print

                        //add code to go to new activity
                        Intent intent = new Intent(getActivity().getBaseContext(), AdminMenu.class);
                        startActivity(intent);
                    }
                    else if (isValid1) //right credentials for User Login
                    {
                        Log.i("Success", "User Login Successful!"); //logging

                        Toast.makeText(getActivity().getBaseContext(), "Login Successful!", Toast.LENGTH_SHORT).show(); //print

                        //add code to go to new activity
                        Intent intent = new Intent(getActivity().getBaseContext(), MainDashboard.class);
                        startActivity(intent);
                    }
                    else //wrong credentials
                    {

                        counter--;

                        Log.i("Error", "Incorrect credentials from user!"); //logging

                        Toast.makeText(getActivity().getBaseContext(), "Incorrect credentials entered!", Toast.LENGTH_SHORT).show();

                        eAttemptsInfo.setText("Attempts remaining: " + counter); // displays amount of remaining attempts

                        if(counter == 0) //0 attempts left
                        {
                            eLogin.setEnabled(false); //hej
                            CDTimer();
                        }
                    }
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