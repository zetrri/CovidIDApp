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

        Boolean buttonSwitchBool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity_distrubated_doses);

        Intent intentExtras = getIntent();
        buttonSwitchBool = intentExtras.getExtras().getBoolean("mode");




        //Declaration
        ListView listView;
        ArrayList<String> conties;
        ArrayList<String> months;
        ArrayAdapter<Data> arrayAdapter;
        EditText etSearch;
        listView =  findViewById(R.id.listview);
        etSearch = findViewById(R.id.edittext);

        //assign arrays to demo data
        conties = createdummydatacounties();
        months = createdummydatamonths();

        //user classes that contain weekly and monthly dose information
        ArrayList<Weekly> weeklist ;
        ArrayList<Monthly> Monthlist ;

        //filling the list with demo data
        Monthlist = createMonths(months,null,1);
        weeklist = createWeeks(null,null,1);
        final ArrayList<Data>datalist = createData(Monthlist, weeklist, conties);

        //setting up the listview to show conties
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, datalist);
        listView.setAdapter(arrayAdapter);

        //for the search to work
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
        //when clicking on item in list, that countys data get sent to the intent
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),datalist.get(i).getName(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), DisplayCountyNumbersActivity.class);

                intent.putExtra("name", datalist.get(i).getName());
                intent.putExtra("id",Integer.toString(datalist.get(i).getId()));
                intent.putExtra("number",Integer.toString(datalist.get(i).getNumber()));
                intent.putExtra("list",datalist.get(i));
                if ( buttonSwitchBool) intent.putExtra("mode",true);


                startActivity(intent);
            }
        });

    }

    //making the final dataclass for alla conties
    public ArrayList<Data> createData(ArrayList<Monthly> monthly,ArrayList<Weekly> weekly,ArrayList<String> conties){
        ArrayList<Data> listofData = new ArrayList<>();
        for(int i=0;i<conties.size();i++){
            Data data = new Data(conties.get(i),i,1,weekly,monthly);
            listofData.add(data);
        }

    return listofData;
    }
    //Reciving a list of weeks and a list of the amount on that week and create the userclass Weekly
    //mode 1 == demo mode
    public ArrayList<Weekly> createWeeks(ArrayList<Integer> weeks, ArrayList<Integer> amount, int mode){
        ArrayList<Weekly> listDone = new ArrayList<>();
        if (mode==1){
            for (int i=1;i<=52;i++){
                Weekly weekly = new Weekly(i,150);
                listDone.add(weekly);
            }
        }
        else{
            for (int i=0;i<weeks.size();i++){
                Weekly weekly = new Weekly(weeks.get(i),amount.get(i));
                listDone.add(weekly);
            }
        }


        return listDone;
    }
    //Reciving a list of months and a list of the amount on that month and create the userclass Monthly
    //mode 1 == demo mode
    public ArrayList<Monthly> createMonths(ArrayList<String> months, ArrayList<Integer> amount, int mode){
        ArrayList<Monthly> listdone = new ArrayList<>();
        if(mode==1){
            for (int i=0;i<months.size();i++){
                Monthly monthly = new Monthly(months.get(i),600);
                listdone.add(monthly);
            }
        }
        else{
            for (int i=0;i<months.size();i++){
                Monthly monthly = new Monthly(months.get(i),amount.get(i));
                listdone.add(monthly);
            }

        }
        return listdone;
    }

    //creating dummy months
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

    //creating dummy counties
    private ArrayList<String> createdummydatacounties(){
        ArrayList<String> conties = new ArrayList<>();
        conties.add("Sverige Total");
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