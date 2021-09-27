package com.example.covidapp.UserReg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentUserRegBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRegFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRegFragment extends Fragment {

    private FragmentUserRegBinding binding;
    String [] account = new String[9];


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserRegFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserRegFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserRegFragment newInstance(String param1, String param2) {
        UserRegFragment fragment = new UserRegFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentUserRegBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner countySpinner = binding.countySpinner;
        Spinner stadSpinner = binding.stadSpinner;



        /* Temp Förbereder  */
        String[] counties = new String[] {"Värmland län", "Örebro län", "Västra Götaland"};

        String[] staderVarmland = new String[] {"Karlstad", "Arvika", "Kil"};

        countySpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, counties));
        stadSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, staderVarmland));

        /* Skapar konto  */
        binding.skapaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView errorMessageText = binding.errorMessageText;
                errorMessageText.setText("");

                /* Kollar fel */
                if(!checkPersonNummer(errorMessageText) || !checkPassword(errorMessageText) || !checkName(errorMessageText) || !checkEmail(errorMessageText) ||
                        !checkMobileNumber(errorMessageText) || !checkStreetAddress(errorMessageText) ){
                    return;
                }
                else{
                    //TEMP SET ACCOUNT
                    //Email
                    EditText input = binding.editTextTextEmailAddress;
                    account[0] = input.getText().toString();
                    //Password
                    input = binding.editTextTextPassword1;
                    account[1] = input.getText().toString();
                    //Person nr
                    input = binding.editTextDate;
                    account[2] = input.getText().toString();
                    //Förnamn
                    input = binding.editTextTextForPersonName;
                    account[3] = input.getText().toString();
                    //Efternamn
                    input = binding.editTextTextLastPersonName;
                    account[4] = input.getText().toString();
                    //Mobil Nr
                    input = binding.editTextPhone;
                    account[5] = input.getText().toString();
                    //Län
                    Spinner spinner = binding.countySpinner;
                    account[6] = spinner.getSelectedItem().toString();
                    //Stad
                    spinner = binding.stadSpinner;
                    account[7] = spinner.getSelectedItem().toString();
                    //Gata
                    input = binding.editTextTextPersonGata;
                    account[8] = input.getText().toString();
                }
            }
        });
    }
    /* Kollar fel i person nummer
     *  Pre: TextView där error ska visas
     *  Post: True om den är i rätt format, False om den är i fel format
     */
    private boolean checkPersonNummer(TextView errorMessageText){
        EditText input = binding.editTextDate;
        String input_value = input.getText().toString();

        // 4, 6 = Månad. 6, 8 = Dag. 0, 4 = Årtal
        int year = Integer.parseInt(input_value.substring(0, 4));
        int month = Integer.parseInt(input_value.substring(4, 6));
        int day = Integer.parseInt(input_value.substring(6, 8));


        if(!input_value.matches("[0-9]+") || input_value.length() != 12 || month > 12 || month < 1 || day < 1 || day > 31 || year < 1900) {
            Log.i("FEL", "Person nummer format");
            input.requestFocus();
            errorMessageText.setText("Fel format för person nummer! (12 siffror)");
            return false;
        }
        else if( day > 30  && ( month == 4 || month == 6 || month == 9 || month == 11 )){
            Log.i("FEL", "Person nummer format");
            input.requestFocus();
            errorMessageText.setText("Fel format för person nummer! (12 siffror)");
            return false;
        }
        else if( day > 28 && month == 2){
            Log.i("FEL", "Person nummer format");
            input.requestFocus();
            errorMessageText.setText("Fel format för person nummer! (12 siffror)");
            return false;
        }
        return true;
    }

    /* Kollar fel i Lösenordet
     *  Pre: TextView där error ska visas
     *  Post: True om den är i rätt format, False om den är i fel format
     */
    private boolean checkPassword(TextView errorMessageText){
        EditText input1 = binding.editTextTextPassword1;
        EditText input2 = binding.editTextTextPassword2;
        String input_value1 = input1.getText().toString();
        String input_value2 = input2.getText().toString();

        if(input_value1.length() < 6){
            Log.i("FEL", "Lösenord för kort");
            input1.requestFocus();
            errorMessageText.setText("Lösenord är för kort, måste va minst 6 tecken lång!");
            return false;
        }
        else if(!input_value1.equals(input_value2)) {
            Log.i("FEL", "Lösenord marchat ej: " + input_value1 + " != " + input_value2);
            input1.requestFocus();
            errorMessageText.setText("Lösenorden matchar ej!");
            return false;
        }

        return true;
    }

    /*  Kollar fel i för och efter namn
     *  Pre: TextView där error ska visas
     *  Post: True om den är i rätt format, False om den är i fel format
     */
    private boolean checkName(TextView errorMessageText){
        EditText input1 = binding.editTextTextForPersonName;
        String input_value1 = input1.getText().toString();
        EditText input2 = binding.editTextTextLastPersonName;
        String input_value2 = input2.getText().toString();

        if(input_value1.trim().length() < 1){
            Log.i("FEL", "Förnamn ej i skrivit");
            input1.requestFocus();
            errorMessageText.setText("Förnamn ej i skrivit");
            return false;
        }
        else if(input_value2.trim().length() < 1){
            Log.i("FEL", "Efternamn ej i skrivit");
            input2.requestFocus();
            errorMessageText.setText("Efternamn ej i skrivit");
            return false;
        }

        return true;
    }

    /*  Kollar fel i eport addressen
     *  Pre: TextView där error ska visas
     *  Post: True om den är i rätt format, False om den är i fel format
     */
    private boolean checkEmail(TextView errorMessageText){
        EditText input = binding.editTextTextEmailAddress;
        String input_value = input.getText().toString();

        if(input_value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(input_value).matches()){
            Log.i("FEL", "Email fel format");
            input.requestFocus();
            errorMessageText.setText("E-post address i fel format!");
            return false;
        }

        return true;
    }

    /*  Kollar fel i mobil numret
     *  Pre: TextView där error ska visas
     *  Post: True om den är i rätt format, False om den är i fel format
     */
    private boolean checkMobileNumber(TextView errorMessageText){
        EditText input = binding.editTextPhone;
        String input_value = input.getText().toString();

        if(!Patterns.PHONE.matcher(input_value).matches()){
            Log.i("FEL", "Phone number fel format");
            input.requestFocus();
            errorMessageText.setText("Fel format på mobil nummer");
            return false;
        }
        return true;
    }

    /*  Kollar fel i gatu adressen
     *  Pre: TextView där error ska visas
     *  Post: True om den är i rätt format, False om den är i fel format
     */
    private boolean checkStreetAddress(TextView errorMessageText){
        EditText input = binding.editTextTextPersonGata;
        String input_value = input.getText().toString();

        if(input_value.trim().isEmpty()){
            Log.i("FEL", "Gatu adress fel format");
            input.requestFocus();
            errorMessageText.setText("Vänligen skriv i gatu address");
            return false;
        }
        return true;
    }

}