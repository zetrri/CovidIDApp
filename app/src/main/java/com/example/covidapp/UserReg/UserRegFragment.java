package com.example.covidapp.UserReg;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.covidapp.MainActivity;
import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentUserRegBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRegFragment extends Fragment {
    private FragmentUserRegBinding binding;
    String [] account = new String[9];

    public UserRegFragment() {
        // Required empty public constructor
    }

    public static UserRegFragment newInstance(String param1, String param2) {
        UserRegFragment fragment = new UserRegFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserRegBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* Skapar konto  */
        binding.skapaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView errorMessageText = binding.errorMessageText;
                errorMessageText.setText("");

                /* Kollar fel */
                if(!checkEmail(errorMessageText) || !checkPassword(errorMessageText) || !checkPersonNummer(errorMessageText) || !checkName(errorMessageText) ||
                        !checkMobileNumber(errorMessageText) || !checkCounty(errorMessageText) || !checkCity(errorMessageText) || !checkStreetAddress(errorMessageText) ){
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
                    //F??rnamn
                    input = binding.editTextTextForPersonName;
                    account[3] = input.getText().toString();
                    //Efternamn
                    input = binding.editTextTextLastPersonName;
                    account[4] = input.getText().toString();
                    //Mobil Nr
                    input = binding.editTextPhone;
                    account[5] = input.getText().toString();
                    //L??n
                    input = binding.editTextCounty;
                    account[6] = input.getText().toString();
                    //Stad
                    input = binding.editTextCity;
                    account[7] = input.getText().toString();
                    //Gata
                    input = binding.editTextTextPersonGata;
                    account[8] = input.getText().toString();
                    makeUser(account,view);
                }
            }
        });
    }
    //Exempel p?? hur man h??mtar uppgifter fr??n databasen
    //
    //
    private void getUser(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference ref = database.getReference("User").child(id);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RegClass regClass = dataSnapshot.getValue(RegClass.class);
                System.out.println(regClass.Firstname);
                System.out.println(regClass.getLastname());
                System.out.println(regClass.Persnr);
                System.out.println(regClass.Adress);
                System.out.println(regClass.Mail);
                System.out.println(regClass.UserID);
                System.out.println(regClass.Phonenr);
                System.out.println(regClass.admin);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    //adds the user to the database
    private void makeUser(String[] accounttoadd, View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        String username = accounttoadd[0];
        String PerNR = accounttoadd[2];
        String password = accounttoadd[1];
        String name = accounttoadd[3];
        String lastname =accounttoadd[4];
        String phonenr =accounttoadd[5];
        String County =accounttoadd[6];
        String city =accounttoadd[7];
        String street = accounttoadd[8];
        Boolean admin = false;

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(accounttoadd[0], accounttoadd[1])
                .addOnCompleteListener(getActivity(), (OnCompleteListener<AuthResult>) task -> {
                    String TAG = "Registartion";
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        long longg = 0;
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        RegClass regClass = new RegClass();
                        regClass.setAdress(County+", "+city+", "+street);
                        regClass.setMail(username);
                        regClass.setPersnr(PerNR);
                        regClass.setPhonenr(phonenr);
                        regClass.setFirstname(name);
                        regClass.setLastname(lastname);
                        regClass.setUserID(user.getUid());
                        regClass.setAdmin(admin);
                        regClass.setDosTwo(longg);
                        regClass.setDosOne(longg);
                        DatabaseReference myRef = database.getReference("User").child(user.getUid());
                        myRef.setValue(regClass);
                        Toast.makeText(getActivity().getBaseContext(), "Successfully registered!",Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.nav_login);
//                        ((MainActivity)getActivity()).onUserLogin();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getActivity().getBaseContext(),task.getException().toString(),Toast.LENGTH_LONG).show();

                    }

                });
    }
    /* Kollar fel i person nummer
     *  Pre: TextView d??r error ska visas
     *  Post: True om den ??r i r??tt format, False om den ??r i fel format
     */
    private boolean checkPersonNummer(TextView errorMessageText){
        EditText input = binding.editTextDate;
        String input_value = input.getText().toString();

        if(input_value.length() < 12){
            Log.i("FEL", "Person nummer format");
            input.requestFocus();
            errorMessageText.setText("Fel format f??r person nummer! (12 siffror)");
            return false;
        }

        // 4, 6 = M??nad. 6, 8 = Dag. 0, 4 = ??rtal
        int year = Integer.parseInt(input_value.substring(0, 4));
        int month = Integer.parseInt(input_value.substring(4, 6));
        int day = Integer.parseInt(input_value.substring(6, 8));

        if(!input_value.matches("[0-9]+") || input_value.length() != 12 || month > 12 || month < 1 || day < 1 || day > 31 || year < 1900) {
            Log.i("FEL", "Person nummer format");
            input.requestFocus();
            errorMessageText.setText("Fel format f??r person nummer! (12 siffror)");
            return false;
        }
        else if( day > 30  && ( month == 4 || month == 6 || month == 9 || month == 11 )){
            Log.i("FEL", "Person nummer format");
            input.requestFocus();
            errorMessageText.setText("Fel format f??r person nummer! (12 siffror)");
            return false;
        }
        else if( day > 28 && month == 2){
            Log.i("FEL", "Person nummer format");
            input.requestFocus();
            errorMessageText.setText("Fel format f??r person nummer! (12 siffror)");
            return false;
        }
        return true;
    }

    /* Kollar fel i L??senordet
     *  Pre: TextView d??r error ska visas
     *  Post: True om den ??r i r??tt format, False om den ??r i fel format
     */
    private boolean checkPassword(TextView errorMessageText){
        EditText input1 = binding.editTextTextPassword1;
        EditText input2 = binding.editTextTextPassword2;
        String input_value1 = input1.getText().toString();
        String input_value2 = input2.getText().toString();

        if(input_value1.length() < 6){
            Log.i("FEL", "L??senord f??r kort");
            input1.requestFocus();
            errorMessageText.setText("L??senord ??r f??r kort, m??ste va minst 6 tecken l??ng!");
            return false;
        }
        else if(!input_value1.equals(input_value2)) {
            Log.i("FEL", "L??senord marchat ej: " + input_value1 + " != " + input_value2);
            input1.requestFocus();
            errorMessageText.setText("L??senorden matchar ej!");
            return false;
        }

        return true;
    }

    /*  Kollar fel i f??r och efter namn
     *  Pre: TextView d??r error ska visas
     *  Post: True om den ??r i r??tt format, False om den ??r i fel format
     */
    private boolean checkName(TextView errorMessageText){
        EditText input1 = binding.editTextTextForPersonName;
        String input_value1 = input1.getText().toString();
        EditText input2 = binding.editTextTextLastPersonName;
        String input_value2 = input2.getText().toString();

        if(input_value1.trim().length() < 1){
            Log.i("FEL", "F??rnamn ej i skrivit");
            input1.requestFocus();
            errorMessageText.setText("F??rnamn ej i skrivit");
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
     *  Pre: TextView d??r error ska visas
     *  Post: True om den ??r i r??tt format, False om den ??r i fel format
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
     *  Pre: TextView d??r error ska visas
     *  Post: True om den ??r i r??tt format, False om den ??r i fel format
     */
    private boolean checkMobileNumber(TextView errorMessageText){
        EditText input = binding.editTextPhone;
        String input_value = input.getText().toString();

        if(!Patterns.PHONE.matcher(input_value).matches()){
            Log.i("FEL", "Phone number fel format");
            input.requestFocus();
            errorMessageText.setText("Fel format p?? mobil nummer");
            return false;
        }
        return true;
    }

    /*  Kollar fel i gatu adressen
     *  Pre: TextView d??r error ska visas
     *  Post: True om den ??r i r??tt format, False om den ??r i fel format
     */
    private boolean checkStreetAddress(TextView errorMessageText){
        EditText input = binding.editTextTextPersonGata;
        String input_value = input.getText().toString();

        if(input_value.trim().isEmpty()){
            Log.i("FEL", "Gatu adress fel format");
            input.requestFocus();
            errorMessageText.setText("V??nligen skriv i gatu address");
            return false;
        }
        return true;
    }

    /*  Kollar fel i l??n
     *  Pre: TextView d??r error ska visas
     *  Post: True om den ??r i r??tt format, False om den ??r i fel format
     */
    private boolean checkCounty(TextView errorMessageText){
        EditText input = binding.editTextCounty;
        String input_value = input.getText().toString();

        if(input_value.trim().isEmpty()){
            Log.i("FEL", "l??n fel format");
            input.requestFocus();
            errorMessageText.setText("V??nligen skriv i l??n");
            return false;
        }
        return true;
    }

    /*  Kollar fel i stad
     *  Pre: TextView d??r error ska visas
     *  Post: True om den ??r i r??tt format, False om den ??r i fel format
     */
    private boolean checkCity(TextView errorMessageText){
        EditText input = binding.editTextCity;
        String input_value = input.getText().toString();

        if(input_value.trim().isEmpty()){
            Log.i("FEL", "stad fel format");
            input.requestFocus();
            errorMessageText.setText("V??nligen skriv i stad");
            return false;
        }
        return true;
    }

}