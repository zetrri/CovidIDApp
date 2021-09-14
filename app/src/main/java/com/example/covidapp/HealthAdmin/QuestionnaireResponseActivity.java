package com.example.covidapp.HealthAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covidapp.R;

public class QuestionnaireResponseActivity extends AppCompatActivity {
    private  booking [] bookings = new booking[4]; //TODO Ta bort och implementera med databas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_response);

        //TODO Ta bort och implementera med databas
        bookings [0] = new booking("0", "0", "0", "0", "Modena", "Doctor spooky", "birch pollen", "199809291111", "Karl Johan", "Kokain");
        bookings [1] = new booking("0", "0", "0", "0", "Modena", "Potatoe", "Potato", "193312031234", "Peter niklas", "Paracetamol");
        bookings [2] = new booking("0", "0", "0", "0", "Modena", "Im scared", "Spooky stuff", "193312039999", "Martin Lund", "Diaree medesin");
        bookings [3] = new booking("0", "0", "0", "0", "Modena", "eeeeeeeeee", "Kaffe", "193312039999", "REEEEEEEE", "Sand");

        LinearLayout container = findViewById(R.id.QuestionnaireContainer);

        //Dynamisk inladdning av kort
        for(int i = 0; i < bookings.length; i++){
            CardView new_card = new CardView(this);
            new_card.setRadius(4);

            LinearLayout linear_layout1 = new LinearLayout(this);
            linear_layout1.setOrientation(LinearLayout.VERTICAL);

            //Skapar personnr och namn TextView
            TextView namn_pers_text = new TextView(this);
            namn_pers_text.setText( (String) (bookings[i].namn + " \n" + bookings[i].personNr));
            namn_pers_text.setTextSize(20);
            namn_pers_text.setBackgroundColor(0xFF6200EE);
            namn_pers_text.setTextColor(Color.WHITE);
            namn_pers_text.setPadding(8,8,8,8);
            linear_layout1.addView(namn_pers_text);

            //Skapar Allergi TextView
            TextView allergi_text = new TextView(this);
            allergi_text.setTextSize(15);
            allergi_text.setText( (String) ("Allergi:\n" + bookings[i].allergy));
            allergi_text.setPadding(0,0,0,16);
            linear_layout1.addView(allergi_text);

            //Skapar Mediciner TextView
            TextView meds_text = new TextView(this);
            meds_text.setTextSize(15);
            meds_text.setText( (String) ("Mediciner:\n" + bookings[i].meds));
            meds_text.setPadding(0,0,0,16);
            linear_layout1.addView(meds_text);

            //Skapar Meddelande TextView
            TextView msg_text = new TextView(this);
            msg_text.setTextSize(15);
            msg_text.setText( (String) ("Meddelande:\n" + bookings[i].message));
            linear_layout1.addView(msg_text);

            //Skapar En horisontel linear kontainer där knapparna är i
            LinearLayout linear_layout2 = new LinearLayout(this);
            linear_layout2.setOrientation(LinearLayout.HORIZONTAL);
            linear_layout2.setPadding(0,0,0,16);
            linear_layout2.setGravity(Gravity.RIGHT);

            Button buttonConfirm = new Button(this);
            buttonConfirm.setText("Godkänd");
            linear_layout2.addView(buttonConfirm);

            //Lyssna på alla Godkänd knappar och tar bort det kortet
            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    container.removeView(new_card);
                }
            });


            Button buttonAdditional = new Button(this);
            buttonAdditional.setText("Ytterligare Verifiering");
            linear_layout2.addView(buttonAdditional);

            linear_layout1.addView(linear_layout2);

            new_card.addView(linear_layout1);
            container.addView(new_card);
        }

    }
}