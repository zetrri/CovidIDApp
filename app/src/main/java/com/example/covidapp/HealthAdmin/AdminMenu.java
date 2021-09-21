package com.example.covidapp.HealthAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.covidapp.R;


public class AdminMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //menu listeners
        setContentView(R.layout.activity_main2);
        LinearLayout eCatalog = findViewById(R.id.Catalog);
        LinearLayout eDashboard = findViewById(R.id.Dashboard);
        LinearLayout eSecDose = findViewById(R.id.SecDose);
        LinearLayout eQuestionnaire = findViewById(R.id.Questionnaire);
        LinearLayout eTimeLine = findViewById(R.id.TimeLine);
        LinearLayout eHealthPass = findViewById(R.id.HealthPass);

        //Listeners, replace activity when implemented.
        /*
       eCatalog.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               Intent intent = new Intent(getBaseContext(), ReplaceWithActivity.class);
               startActivity(intent);
           }
       });
        eDashboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReplaceWithActivity..class);
                startActivity(intent);
            }
        });
        eSecDose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReplaceWithActivity..class);
                startActivity(intent);
            }
        });
        */
        eQuestionnaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), QuestionnaireResponseActivity.class);
                startActivity(intent);
            }
        });
        /*
        eTimeLine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReplaceWithActivity..class);
                startActivity(intent);
            }
        });
        eHealthPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReplaceWithActivity..class);
                startActivity(intent);
            }
        });*/
    }
}
