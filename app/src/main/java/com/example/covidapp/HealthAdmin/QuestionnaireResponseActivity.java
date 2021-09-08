package com.example.covidapp.HealthAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covidapp.R;

public class QuestionnaireResponseActivity extends AppCompatActivity {
    private  booking [] bookings = new booking[2]; //TODO Ta bort och implementera med databas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_response);
        bookings [0] = new booking("0", "0", "0", "0", "Modena", "Doctor spooky", "birch pollen", "199809291111");
        bookings [1] = new booking("0", "0", "0", "0", "Modena", "Potatoe", "Potato", "193312031234");

        LinearLayout container = findViewById(R.id.QuestionnaireContainer);

        for(int i = 0; i < bookings.length; i++){
            CardView new_card = new CardView(this);
            TextView new_text = new TextView(this);
            new_text.setText(bookings[1].personNr);
            new_card.addView(new_text);
            container.addView(new_card);
        }

    }
}