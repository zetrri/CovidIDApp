package com.example.covidapp.Booking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.Dashboard.Data;
import com.example.covidapp.HealthAdmin.AvailableTimesListUserClass;
import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentBookingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {

    BookingFragment this_obj = this;
    private Booking booking = new Booking("", "", "", "", "", "", "", "", "", "", "");
    private String[] cities;
    private String[] clinics;
    private int choosedDay,choosedMonth,choosedYear,choosedHour,choosedMinute;
    private FragmentBookingBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
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

        binding = FragmentBookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;

//        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO Se till att endast tider den dagen visas, inte tider för alla dagar
        ArrayList<String> countieslist = new ArrayList<>();
        ArrayList<String> citieslist = new ArrayList<>();
        ArrayList<String> clinicslist = new ArrayList<>();
        ArrayList<String> vaccinelist = new ArrayList<>();
        ArrayList<AvailableTimesListUserClass> availableTimesListUserClassArrayList = new ArrayList<>();
        ArrayList<Date> datelist = new ArrayList<>();
        vaccinelist.add("Pfizer");
        vaccinelist.add("Moderna");
        vaccinelist.add("NovaVax");
        vaccinelist.add("AstraZenica");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference ref = database.getReference("AvailableTimes");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Getting data from database
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    AvailableTimesListUserClass availableTimesListUserClass = dataSnapshot1.getValue(AvailableTimesListUserClass.class);
                    if (!countieslist.contains(availableTimesListUserClass.getCounty())) countieslist.add(availableTimesListUserClass.getCounty());
                    if (!citieslist.contains(availableTimesListUserClass.getCity())) citieslist.add(availableTimesListUserClass.getCity());
                    if (!clinicslist.contains(availableTimesListUserClass.getClinic())) clinicslist.add(availableTimesListUserClass.getClinic());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(availableTimesListUserClass.getTimestamp());
                    datelist.add(calendar.getTime());
                    availableTimesListUserClassArrayList.add(availableTimesListUserClass);
                }
                //Arrayadapters
                ArrayAdapter<String> countieslistadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countieslist);
                ArrayAdapter<String> citieslistadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, citieslist);
                ArrayAdapter<String> clinicslistadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, clinicslist);
                ArrayAdapter<String> vaccinelistadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, vaccinelist);
                ArrayAdapter<Date> timeadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, datelist);

                //Binding
                Spinner county_dropdown = binding.dropdownCounty;
                Spinner city_dropdown = binding.dropdownCity;
                Spinner clinic_dropdown = binding.dropdownClinic;
                Spinner vaccine_dropdown = binding.dropdownVaccine;
                Button button = binding.button; //Boka knapp
                RadioGroup radioGroup = binding.radGroup1;
                CalendarView Kalender = binding.calendarView;
                TextView error = binding.errormessage;

                //set adapters
                vaccine_dropdown.setAdapter(vaccinelistadapter);
                county_dropdown.setAdapter(countieslistadapter);
                city_dropdown.setAdapter(citieslistadapter);
                clinic_dropdown.setAdapter(clinicslistadapter);

                SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
                for (int i=0; i<datelist.size(); i++){
                    RadioButton newRadioButton = new RadioButton(getActivity());
                    newRadioButton.setText(sdfclock.format(datelist.get(i)));
                    newRadioButton.setId(View.generateViewId());
                    radioGroup.addView(newRadioButton);
                }

                Kalender.setFirstDayOfWeek(2);
                Kalender.setMinDate(Kalender.getDate());
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                Kalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                        choosedYear = year; //sparar år
                        choosedDay = day; //Sparar månad
                        choosedMonth = month; //sparar dag
                        Calendar date2 = Calendar.getInstance();
                        date2.set(year,month,day);
                        Toast.makeText(getActivity().getBaseContext(),sdf.format(date2.getTimeInMillis()),Toast.LENGTH_SHORT).show();
                    }
                });


                //buton book appointment
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) binding.getRoot().findViewById(selectedId);

                        if(radioButton==null){ //försöker boka utan någon tid vald
                            error.setVisibility(View.VISIBLE);
                            Log.i("FEL", "Ingen tid vald");
                            return;
                        }

                        booking.time = (String) radioButton.getText(); //tiden som är vald
                        //Separera timma och minut
                        String[] STRstr = booking.time.split(":");
                        Log.d("Vald tid",STRstr[0].toString()+ STRstr[1].toString());
                        //Skapar en "date" variabel
                        Calendar date = Calendar.getInstance();
                        date.set(choosedYear,choosedMonth,choosedDay,Integer.parseInt(STRstr[0]),Integer.parseInt(STRstr[1]),0);
                        System.out.println(choosedDay+" "+choosedMonth+" "+choosedYear+" "+STRstr[0]+" "+STRstr[1]);
                        Log.d("hour and minute",STRstr[0]+":"+STRstr[1]);
                        EditText text_allergies = (EditText) binding.editTextAllergies;
                        EditText text_medicines = (EditText) binding.editTextMedicine;
                        EditText text_comments = (EditText) binding.editTextComments;
                        booking.allergy = text_allergies.getText().toString(); //sträng med allergier
                        booking.meds = text_medicines.getText().toString(); // sträng med mediciner
                        booking.message = text_comments.getText().toString(); // sträng med kommentarer
                        booking.clinic = clinic_dropdown.getSelectedItem().toString();
                        booking.city = city_dropdown.getSelectedItem().toString();
                        booking.county = county_dropdown.getSelectedItem().toString();
                        booking.vaccine = vaccine_dropdown.getSelectedItem().toString();
                        Log.d("Selected",booking.city);
                        String information = "Datum: " + booking.date + "\nTid: " + booking.time + "\nVaccin: " + booking.vaccine + "\nKlinik: " + booking.clinic + " " + booking.city;

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Din bokning")
                                .setMessage(information+"\n\nVill du bekräfta?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Navigation.findNavController(view).navigate(R.id.action_nav_booking_to_nav_my_page);
                                        // TODO spara informationen i en databas och gå till min sida
                                        ConnectUserWithTime(booking,date,availableTimesListUserClassArrayList);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .show();

                        //Create new class and import to database
                        //ConnectUserWithTime(booking,date);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        });



        // Kod för dom olika dropdown menyerna över län, stad och klinik
//        Spinner county_dropdown = findViewById(R.id.dropdown_county);
//        Spinner county_dropdown = binding.dropdownCounty;
//
//        String[] counties = new String[] {"Värmland", "Västra Götaland"}; // TODO kommer hämtas från databas
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, counties);
//        county_dropdown.setAdapter(adapter1);
//
//        //***************** Exempel på att rätt län har rätt städer ****************
//        // Kommer ändras när vi implementerar databaser
//        county_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                booking.county = (String) adapterView.getItemAtPosition(position); // sparar län
//                //TODO jämför med databas och hämta rätt städer
//                if(booking.county == "Västra Götaland"){
//                    cities =  new String[] {"Göteborg", "Alingsås"};
//                }else{
//                    cities = new String[] {"Karlstad"};
//                }
//                Spinner city_dropdown = binding.dropdownCity;
//                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, cities);
//                city_dropdown.setAdapter(adapter2);
//
//                city_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                        booking.city = (String) adapterView.getItemAtPosition(position); // sparar stad
//                        //TODO jämför med databas
//                        if(booking.city.equals("Karlstad")){
//                            clinics =  new String[] {"Gripen", "Kronoparken", "Rud"};
//                        }else{
//                            clinics = new String[] {"Sollebrunn", "Nolhaga"};
//                        }
//                        Spinner clinic_dropdown = binding.dropdownClinic;
//                        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, clinics);
//                        clinic_dropdown.setAdapter(adapter3);
//
//                        clinic_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                                booking.clinic = (String) adapterView.getItemAtPosition(position); // sparar klinik
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> adapterView) {
//                            }
//                        });
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//                    }
//                });
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//        //************************************************************************************
//
//        //dropdown för att välja vaccin
//        Spinner vaccine_dropdown = binding.dropdownVaccine;
//        String[] vaccines = new String[] {"Phizer", "Moderna", "NovaVax"}; //TODO hämta från databas
//        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, vaccines);
//        vaccine_dropdown.setAdapter(adapter4);
//
//        //Det valda vaccinet sparas
//        vaccine_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                booking.vaccine = vaccine_dropdown.getItemAtPosition(position).toString(); // sparar vaccinet valt
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });


//        RadioGroup radioGroup = binding.radGroup1;
//
//        // få ut datum från kalendern
//        CalendarView Kalender = binding.calendarView;
//        TextView error = binding.errormessage;

//        Kalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
//                choosedYear = year; //sparar år
//                choosedDay = day; //Sparar månad
//                choosedMonth = month; //sparar dag
//                Log.d("Choosedday",String.valueOf(choosedDay));
//                ArrayList<Times> timeslist = new ArrayList<>();
//                for (int j=8;j<17;j++){
//                    for (int i=0;i<60;i+=15){
//                        Times times = new Times(j,i);
//                        timeslist.add(times);
//                        //Log.d("Times",String.valueOf(times.hour)+String.valueOf(times.minute));
//                    }
//                }
//
//                booking.date = day + "/" + month + "-" + year; // valt datum
//                String curDate = String.valueOf(day);
//                List<String> freetimes = new ArrayList<>();
//
//                // exempel på att hitta rätt tider för rätt dag, kommer ändras när databaser implementeras
////                if(curDate.equals("1")) {
////                    radioGroup.removeAllViews();
////                    String[] dates = new String[]{"11:00", "11:15", "12:30", "13:00", "13:45", "14:15"}; //TODO hämta från databas
////                    Collections.addAll(freetimes, dates);
////                }
////                else if(curDate.equals("2")){
////                    radioGroup.removeAllViews();
////                    String[] dates= new String[]{"11:30", "13:15", "14:00"}; //TODO hämta från databas
////                    Collections.addAll(freetimes, dates);
//                //}
//
//                //Skapa Radiobuttons
////                int stringcount = freetimes.size(); // hur många tider den dagen
////                for (int i=0; i<stringcount; i++){
////                    RadioButton newRadioButton = new RadioButton(getActivity());
////                    newRadioButton.setText(freetimes.get(i));
////                    newRadioButton.setId(View.generateViewId());
////                    radioGroup.addView(newRadioButton);
////                }
////                int stringcount = timeslist.size(); // hur många tider den dagen
////                for (int i=0; i<stringcount; i++){
////                    RadioButton newRadioButton = new RadioButton(getActivity());
////                    newRadioButton.setText(timeslist.get(i).toString());
////
////                    newRadioButton.setId(View.generateViewId());
////                    radioGroup.addView(newRadioButton);
////                }
//            }
//        });
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                error.setVisibility(View.INVISIBLE);
//            }
//        });
//
//
//        //när man trycker på boka vaccin
//        Button button = binding.button;
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int selectedId = radioGroup.getCheckedRadioButtonId();
//                RadioButton radioButton = (RadioButton) binding.getRoot().findViewById(selectedId);
//
//                if(radioButton==null){ //försöker boka utan någon tid vald
//                    error.setVisibility(View.VISIBLE);
//                    Log.i("FEL", "Ingen tid vald");
//                    return;
//                }
//
//                booking.time = (String) radioButton.getText(); //tiden som är vald
//                //Separera timma och minut
//                String[] STRstr = booking.time.split(":");
//                Log.d("Vald tid",STRstr[0].toString()+ STRstr[1].toString());
//                //Skapar en "date" variabel
//                Calendar date = Calendar.getInstance();
//                date.set(choosedYear,choosedMonth,choosedDay,Integer.parseInt(STRstr[0]),Integer.parseInt(STRstr[1]));
//
//                EditText text_allergies = (EditText) binding.editTextAllergies;
//                EditText text_medicines = (EditText) binding.editTextMedicine;
//                EditText text_comments = (EditText) binding.editTextComments;
//                booking.allergy = text_allergies.getText().toString(); //sträng med allergier
//                booking.meds = text_medicines.getText().toString(); // sträng med mediciner
//                booking.message = text_comments.getText().toString(); // sträng med kommentarer
//
//                String information = "Datum: " + booking.date + "\nTid: " + booking.time + "\nVaccin: " + booking.vaccine + "\nKlinik: " + booking.clinic + " " + booking.city;
//
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("Din bokning")
//                        .setMessage(information+"\n\nVill du bekräfta?")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Navigation.findNavController(view).navigate(R.id.action_nav_booking_to_nav_my_page);
//                                // TODO spara informationen i en databas och gå till min sida
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, null)
//                        .show();
//
//                //Create new class and import to database
//                ConnectUserWithTime(booking,date);
//            }
//        });


    }

    private AvailableTimesListUserClass getNewAppointment(){
        AvailableTimesListUserClass availableTimesListUserClass = new AvailableTimesListUserClass();
        return availableTimesListUserClass;
    }

    private void ConnectUserWithTime(Booking booking,Calendar date,ArrayList<AvailableTimesListUserClass> listofDates){
        Calendar datetest = Calendar.getInstance();
        SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        AvailableTimesListUserClass Userclass = new AvailableTimesListUserClass();
        Userclass.setCity("None");
        for (AvailableTimesListUserClass key: listofDates){
            datetest.setTimeInMillis(key.getTimestamp());
            if ((sdf.format(datetest.getTimeInMillis())+ sdfclock.format(datetest.getTimeInMillis())).equals(sdf.format(date.getTimeInMillis())+ sdfclock.format(date.getTimeInMillis()))){
                Userclass=key;
                Log.d("TAG", key.getTimestamp().toString());
            }

            //Log.d("TAG","HEHOPP");
            //System.out.println(sdf.format(date.getTimeInMillis())+ sdfclock.format(date.getTimeInMillis()));
            //Log.d("timestampinkey", key.getTimestamp().toString() );
            //Log.d("timestampininchoosed", String.valueOf(date.getTimeInMillis()));
        }
        if (Userclass.getCity().equals("None")){
            Log.d("Error creating user","Error creating user");
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        //AvailableTimesListUserClass Userclass = getNewAppointment();
        Userclass.setAllergies(booking.allergy);
        Userclass.setMedication(booking.meds);
        Userclass.setComments(booking.message);
        Userclass.setCity(booking.city);
        Userclass.setCounty(booking.county);
        Userclass.setClinic(booking.clinic);
        //Userclass.setTimestamp(date.getTimeInMillis());
        Userclass.setVaccine(booking.vaccine);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if ( currentUser==null){
            Toast.makeText(getActivity().getBaseContext(),"User Not logged in",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            String UID = currentUser.getUid();
            Userclass.setBookedBy(UID);
            Userclass.setAvailable(false);
            //Userclass.setId(UUID.randomUUID().toString());
            DatabaseReference myRef = database.getReference("BookedTimes").child(Userclass.getId());
            myRef.setValue(Userclass);

            DatabaseReference myRef3 = database.getReference("AvailableTimes").child(Userclass.getId());
            myRef3.removeValue();

        }

    }
}