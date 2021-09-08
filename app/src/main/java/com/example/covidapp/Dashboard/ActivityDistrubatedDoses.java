package com.example.covidapp.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.covidapp.R;

import java.util.ArrayList;

public class ActivityDistrubatedDoses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distrubated_doses);
        ListView listView;
        ArrayList<Data> datalist = new ArrayList<>();
        ArrayList<String> conties;
        ArrayList<String> months;
        ArrayAdapter<Data> arrayAdapter;
        EditText etSearch;
        listView =  findViewById(R.id.listview);
        etSearch = findViewById(R.id.edittext);


        conties = createdummydatacounties();
        months = createdummydatamonths();




        ArrayList<Weekly> weeklist = new ArrayList<>();
        ArrayList<Monthly> Monthlist = new ArrayList<>();


        for (int i=0;i<months.size();i++){
            Monthly monthly = new Monthly(months.get(i),600);
            Monthlist.add(monthly);
        }
        //System.out.println("Amoint in months"+Integer.toString(months.size()));
        for (int i=1;i<=52;i++){
            Weekly weekly = new Weekly(i,150);
            weeklist.add(weekly);
        }
        //ListView listview_week = findViewById(R.id.listview_Week);

        for(int i=0;i<conties.size();i++){
            Data data = new Data(conties.get(i),i,1,weeklist,Monthlist);
            datalist.add(data);
        }


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, datalist);
        listView.setAdapter(arrayAdapter);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),datalist.get(i).getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), DisplayCountyNumbersActivity.class);

                intent.putExtra("name", datalist.get(i).getName());
                intent.putExtra("id",Integer.toString(datalist.get(i).getId()));
                intent.putExtra("number",Integer.toString(datalist.get(i).getNumber()));
                intent.putExtra("list",datalist.get(i));


                startActivity(intent);
            }
        });

    }
    private ArrayList<String> createdummydatamonths() {
        ArrayList<String> months= new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        return months;
    }

    private ArrayList<String> createdummydatacounties(){
        ArrayList<String> conties = new ArrayList<>();
        conties.add("Blekinge län");
        conties.add("Dalarnas län");
        conties.add("Gotlands län");
        conties.add("Gävleborgs län");
        conties.add("Hallands län");
        conties.add("Jämtlands län");
        conties.add("Jönköpings län");
        conties.add("Kalmar län");
        conties.add("Kronobergs län");
        conties.add("Norrbottens län");
        conties.add("Skåne län");
        conties.add("Stockholms län");

        conties.add("Södermanlands län");
        conties.add("Uppsala län");
        conties.add("Värmlands län");
        conties.add("Västerbottens län");
        conties.add("Västernorrlands län");
        conties.add("Västmanlands län");
        conties.add("Västra Götalands län ");
        conties.add("Örebro län");
        conties.add("Östergötlands län");
        return conties;
    }
}