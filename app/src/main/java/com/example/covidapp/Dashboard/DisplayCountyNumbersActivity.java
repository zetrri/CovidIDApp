package com.example.covidapp.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.covidapp.R;

import java.util.ArrayList;

public class DisplayCountyNumbersActivity extends AppCompatActivity {

    Button buttonweek;
    Button buttonMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_county_numbers);

        //get intent data and assign to the right fields
        Intent intent = getIntent();
        Data DistData = (Data) intent.getSerializableExtra("list");
        TextView textView_countyname = findViewById(R.id.textView);
        TextView textView_countyID = findViewById(R.id.textView2);
        TextView textView_CountyNumber = findViewById(R.id.textView3);
        buttonweek = findViewById(R.id.buttonweek);
        buttonMonth = findViewById(R.id.buttonmonth);

        //set textviews to the names according to the selected county
        textView_countyname.setText("County "+DistData.getName());
        textView_countyID.setText("ID "+DistData.getId());
        textView_CountyNumber.setText("number "+DistData.getNumber());

        //set inital view for listview
        //mode =0 --> Week
        //mode = 1 --> Month
        setViewForList(DistData,0);

        //Buttons to change view to month or week
        buttonweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewForList(DistData,0);

            }
        });
        buttonMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewForList(DistData,1);
            }
        });

    }

    //set Listview to either Montly or weekly
    private void setViewForList(Data data, int mode){
        ArrayAdapter<Weekly> arrayAdapter;
        ArrayList<Weekly> weeklist = data.getWeeklyDist();
        ArrayList<Monthly> monthlist = data.getMonthly();
        ListView listView = findViewById(R.id.listview_Week);
        listView.setAdapter(null);
        //set listview to week
        if (mode==0){
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, weeklist){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText("Vecka "+Integer.toString(weeklist.get(position).getWeek()));
                    text2.setText(Integer.toString(weeklist.get(position).getAmount()));
                    return view;
                }
            };

            listView.setAdapter(arrayAdapter);
        }
        //set listview to month
        else{
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, monthlist){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(monthlist.get(position).getMonth());
                    text2.setText(Integer.toString(monthlist.get(position).getAmount()));
                    return view;
                }
            };

            listView.setAdapter(arrayAdapter);

        }

    }
    }
