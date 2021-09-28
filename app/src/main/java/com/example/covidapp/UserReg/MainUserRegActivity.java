package com.example.covidapp.UserReg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.LogIn.MainLogInActivity;
import com.example.covidapp.MyPage.MainMyPage;
import com.example.covidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainUserRegActivity extends AppCompatActivity {
    String [] account = new String[9];
    //firebase authentication
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_reg);
        Spinner countySpinner = findViewById(R.id.countySpinner);
        Spinner stadSpinner = findViewById(R.id.stadSpinner);

        firebaseAuth = FirebaseAuth.getInstance();


        /* Temp Förbereder  */
        String[] counties = new String[] {"Värmland län", "Örebro län", "Västra Götaland"};

        String[] staderVarmland = new String[] {"Karlstad", "Arvika", "Kil"};

        countySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, counties));
        stadSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, staderVarmland));

        /* Skapar konto  */
        findViewById(R.id.skapaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView errorMessageText = findViewById(R.id.errorMessageText);
                errorMessageText.setText("");

                /* Kollar fel */
                if(!checkEmail(errorMessageText)  /*|| !checkPassword(errorMessageText) || !checkPersonNummer(errorMessageText) || !checkName(errorMessageText) ||
                        !checkMobileNumber(errorMessageText) || !checkStreetAddress(errorMessageText)*/)
                {
                    return ;
                }
                else{
                    //TEMP SET ACCOUNT
                    //Email
                    EditText input = findViewById(R.id.editTextTextEmailAddress);
                    account[0] = input.getText().toString();
                    //Password
                    input = findViewById(R.id.editTextTextPassword1);
                    account[1] = input.getText().toString();
                    //Person nr
                    input = findViewById(R.id.editTextDate);
                    account[2] = input.getText().toString();
                    //Förnamn
                    input = findViewById(R.id.editTextTextForPersonName);
                    account[3] = input.getText().toString();
                    //Efternamn
                    input = findViewById(R.id.editTextTextLastPersonName);
                    account[4] = input.getText().toString();
                    //Mobil Nr
                    input = findViewById(R.id.editTextPhone);
                    account[5] = input.getText().toString();
                    //Län
                    Spinner spinner = findViewById(R.id.countySpinner);
                    account[6] = spinner.getSelectedItem().toString();
                    //Stad
                    spinner = findViewById(R.id.stadSpinner);
                    account[7] = spinner.getSelectedItem().toString();
                    //Gata
                    input = findViewById(R.id.editTextTextPersonGata);
                    account[8] = input.getText().toString();

                    //sending to database
                    makeUser(account);
                }
            }
        });
    }

    //Exempel på hur man hämtar uppgifter från databasen
    //
    //
    private void getUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference ref = database.getReference("User").child("43254325");

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    private void makeUser(String[] accounttoadd){
        //Testklass ta bort
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

        DatabaseReference myRef = database.getReference("User").child(PerNR);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        //
        mAuth.createUserWithEmailAndPassword(accounttoadd[0], accounttoadd[1])
                .addOnCompleteListener(this, (OnCompleteListener<AuthResult>) task -> {
                    String TAG = "Registartion";
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
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

                        myRef.setValue(regClass);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getBaseContext(),task.getException().toString(),Toast.LENGTH_LONG).show();

                    }

                });
    }
    /* Kollar fel i person nummer
     *  Pre: TextView där error ska visas
     *  Post: True om den är i rätt format, False om den är i fel format
     */
    private boolean checkPersonNummer(TextView errorMessageText){
        EditText input = findViewById(R.id.editTextDate);
        String input_value = input.getText().toString();

        if(input_value.length() < 12){
            Log.i("FEL", "Person nummer format");
            input.requestFocus();
            errorMessageText.setText("Fel format för person nummer! (12 siffror)");
            return false;
        }

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
        EditText input1 = findViewById(R.id.editTextTextPassword1);
        EditText input2 = findViewById(R.id.editTextTextPassword2);
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
        EditText input1 = findViewById(R.id.editTextTextForPersonName);
        String input_value1 = input1.getText().toString();
        EditText input2 = findViewById(R.id.editTextTextLastPersonName);
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
        EditText input = findViewById(R.id.editTextTextEmailAddress);
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
        EditText input = findViewById(R.id.editTextPhone);
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
        EditText input = findViewById(R.id.editTextTextPersonGata);
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