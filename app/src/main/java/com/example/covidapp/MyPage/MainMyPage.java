package com.example.covidapp.MyPage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covidapp.Booking.BookingMainActivity;
import com.example.covidapp.Booking.Booking;
import com.example.covidapp.Passport.PassportMainActivity;
import com.example.covidapp.R;

public class MainMyPage extends AppCompatActivity {
    Booking[] Bookings = new Booking[2];
    MainMyPage this_obj = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_my_page);
        Bookings[0] = new Booking("8/2-2021", "15:30", "Gripen", "Karlstad", "Värmland", "Modena", "birch pollen", "", "Doctor spooky", "199809291111", "Karl Johan");
        Bookings[1] = new Booking("14/7-2021", "11:00", "Nolhaga", "Alingsås", "Västra Götaland", "Modena", "Potatoe", "Potato","", "193312031234", "Peter niklas");
        //TODO ta från databas

        //Top menu listeners
        LinearLayout passport = findViewById(R.id.passport);
        LinearLayout bookappointment = findViewById(R.id.bookappointment);
        LinearLayout notifications = findViewById(R.id.notifications);
        passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PassportMainActivity.class);
                startActivity(intent);
            }
        });
        bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), BookingMainActivity.class);
                startActivity(intent);
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO notifications
            }
        });

        LinearLayout container = findViewById(R.id.containerbookings);

        //Skapar kort med mina bokningar
        //TODO hämta storleken från databas
        for(int i = 0; i < Bookings.length; i++){
            CardView new_card = new CardView(this);
            new_card.setId(i+1);
            LinearLayout linear_layout1 = new LinearLayout(this);
            linear_layout1.setOrientation(LinearLayout.VERTICAL);
            TextView date_time = new TextView(this);

            date_time.setText((String)(Bookings[i].date + " kl " + Bookings[i].time));
            date_time.setTextSize(20);
            date_time.setBackgroundColor(0xFF6200EE);
            date_time.setTextColor(Color.WHITE);
            linear_layout1.addView(date_time);

            TextView clinic_text = new TextView(this);
            clinic_text.setTextSize(15);
            clinic_text.setText( (String) ("Mottagning: " + Bookings[i].clinic + " " + Bookings[i].city));
            linear_layout1.addView(clinic_text);

            TextView vaccin_text = new TextView(this);
            vaccin_text.setTextSize(15);
            vaccin_text.setText( (String) ("Vaccin: " + Bookings[i].vaccine));
            linear_layout1.addView(vaccin_text);

            LinearLayout linear_layout2 = new LinearLayout(this);
            linear_layout2.setOrientation(LinearLayout.HORIZONTAL);
            linear_layout2.setPadding(0,0,0,16);
            linear_layout2.setGravity(Gravity.RIGHT);

            Button buttonAvboka = new Button(this);
            buttonAvboka.setText("Avboka");
            linear_layout2.addView(buttonAvboka);
            buttonAvboka.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(this_obj)
                            .setTitle("Avboka")
                            .setMessage("Datum: "+date_time.getText()+ "\n" + clinic_text.getText() + "\n\nÄr du säker på att du vill avboka denna tid?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new_card.removeAllViews();
                                    // TODO remove information from database
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();

                }
            });

            Button buttonOmboka = new Button(this);
            buttonOmboka.setText("Omboka");
            linear_layout2.addView(buttonOmboka);
            buttonOmboka.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(this_obj)
                            .setTitle("Omboka")
                            .setMessage("Datum: "+date_time.getText()+ "\n" + clinic_text.getText() + "\n\nÄr du säker på att du vill omboka denna tid?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new_card.removeAllViews();
                                    Intent intent = new Intent(getBaseContext(), BookingMainActivity.class);
                                    startActivity(intent);
                                    // TODO remove information from database
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                }
            });

            linear_layout1.addView(linear_layout2);
            new_card.addView(linear_layout1);
            container.addView(new_card);
        }

    }
}