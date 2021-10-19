package com.example.covidapp.LogIn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.MainActivity;
import com.example.covidapp.R;
import com.example.covidapp.UserReg.RegClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.covidapp.databinding.FragmentLoginBinding;
import com.google.firebase.database.ValueEventListener;

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
    private Button eForgot;
    private Button eSignup;
    public Context context = getActivity();

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
        eForgot = binding.btnForgot;
        eSignup = binding.btnSignup;

        //progress dialog(shows a loading wheel)
        ProgressDialog = new ProgressDialog(getActivity());


        //check if user is already logged in.
        View view2 = getView();
        getUser(view2);

        eSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_login_to_nav_user_reg);
            }
        });

        eForgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_login_to_nav_password);
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
                    //isValid = validate(inputEmail, inputPassword);
                    //isValid1 = validate1(inputEmail, inputPassword);

                    Log.d("Email",inputEmail);
                    Log.d("Pass",inputPassword);
                    validate(inputEmail, inputPassword);

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
                eAttemptsInfo.setText("Återstående försök: " + counter); // displays amount of remaining attempts
            }
        };
        cTimer.start();
    }
    //Getting the current user, if logged in
    private void getUser(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String UID = currentUser.getUid();
                    DatabaseReference myRef = database.getReference("User").child(UID);
                    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            DataSnapshot snapshot= task.getResult();
                            RegClass regClass = snapshot.getValue(RegClass.class);
                            ((MainActivity)getActivity()).loggedIn();
                            if (regClass.getAdmin()==false){
                                Log.d("Admin","not an admin");
                                View view = getView();
                                Navigation.findNavController(view).navigate(R.id.nav_my_page);
                            }
                            else{
                                Log.d("Admin","is an admin");
                                View view2 = getView();
                                Navigation.findNavController(view2).navigate(R.id.nav_admin_menu);
                            }
                        }
                    });
                }

        }


    private boolean validate(String name, String password) //Admin method for checking if user & password matches
    {
        ProgressDialog.setMessage("Verification in progress . . . ");
        ProgressDialog.show();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //direct to activity if login successful
                if (task.isSuccessful())
                {
                    View view = getView();
                    Log.i("Success", "User Login Successful!"); //logging
                    ProgressDialog.dismiss();
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("1", R.integer.logged_in);
                    editor.apply();
                    getUser(view);
                    ((MainActivity)getActivity()).loggedIn();
                }
                //wrong credentials
                else
                {
                    Toast.makeText(getActivity().getBaseContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
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