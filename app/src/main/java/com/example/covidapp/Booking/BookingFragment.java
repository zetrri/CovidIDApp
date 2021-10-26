package com.example.covidapp.Booking;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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


import com.example.covidapp.ClinicsClass;
import com.example.covidapp.HealthAdmin.AvailableTimesListUserClass;
import com.example.covidapp.R;
import com.example.covidapp.UserReg.RegClass;
import com.example.covidapp.databinding.FragmentBookingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.PrimitiveIterator;
import java.util.TimeZone;
import java.util.UUID;

public class BookingFragment extends Fragment {
    private Booking booking = new Booking("", "", "", "", "", "", "", "", "", "", "");
    private ArrayList<ClinicsClass> all_clinicsClass = new ArrayList<>();
    private int choosedDay,choosedMonth,choosedYear,choosedHour,choosedMinute;
    private FragmentBookingBinding binding;
    private ArrayList<AvailableTimesListUserClass> availableTimes_clinic = new ArrayList<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
    private SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");

    private DatabaseReference ref;
    private long countVaccine;
    private String countVaccineAfter;

    public BookingFragment() {
        // Required empty public constructor
    }

    public static BookingFragment newInstance(String param1, String param2) {
        BookingFragment fragment = new BookingFragment();
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
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayList<String> countieslist = new ArrayList<>();
        ArrayList<String> citieslist = new ArrayList<>();
        ArrayList<String> clinicslist = new ArrayList<>();
        ArrayList<String> vaccinelist = new ArrayList<>();
        ArrayList<AvailableTimesListUserClass> all_availableTimes = new ArrayList<>();
        ArrayList<Date> datelist = new ArrayList<>();
        vaccinelist.add("Pfizer");
        vaccinelist.add("Moderna");

        Calendar today = Calendar.getInstance();
        long todayInMillis = today.getTimeInMillis() + TimeZone.getDefault().getOffset(today.getTimeInMillis());

        //Binding
        Spinner county_dropdown = binding.dropdownCounty;
        Spinner city_dropdown = binding.dropdownCity;
        Spinner clinic_dropdown = binding.dropdownClinic;
        Spinner vaccine_dropdown = binding.dropdownVaccine;
        Button button = binding.button; //Boka knapp
        RadioGroup radioGroup = binding.radGroup1;
        CalendarView Kalender = binding.calendarView;
        TextView error = binding.errormessage;
        EditText text_allergies = (EditText) binding.editTextAllergies;
        EditText text_medicines = (EditText) binding.editTextMedicine;
        EditText text_comments = (EditText) binding.editTextComments;

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference availableTimesref = database.getReference("AvailableTimes");
        FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userref = database.getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        availableTimesref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for(DataSnapshot dataSnapshot1: task.getResult().getChildren()){
                    AvailableTimesListUserClass child = dataSnapshot1.getValue(AvailableTimesListUserClass.class);
                    if(todayInMillis > child.getTimestamp())
                        availableTimesref.child(dataSnapshot1.getKey()).removeValue();
                    else
                        all_availableTimes.add(dataSnapshot1.getValue(AvailableTimesListUserClass.class));
                }
                //Creating the dropdown menu's from database
                DatabaseReference clinicsRef = database.getReference("Kliniker");
                clinicsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        for(DataSnapshot dataSnapshot1: task.getResult().getChildren()){
                            all_clinicsClass.add(dataSnapshot1.getValue(ClinicsClass.class));
                        }

                        //add counties to dropdown
                        for(ClinicsClass clinicsClass : all_clinicsClass){
                            if(!countieslist.contains(clinicsClass.getCounty()))
                                countieslist.add(clinicsClass.getCounty());
                        }
                        ArrayAdapter<String> countieslistadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countieslist);
                        county_dropdown.setAdapter(countieslistadapter);
                        county_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String county_selected = (String) adapterView.getItemAtPosition(i);

                                //add cities to dropdown
                                citieslist.clear();
                                for(ClinicsClass clinicsClass : all_clinicsClass){
                                    if(clinicsClass.getCounty().equals(county_selected) && !citieslist.contains(clinicsClass.getCity()))
                                        citieslist.add(clinicsClass.getCity());
                                }
                                ArrayAdapter<String> citieslistadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, citieslist);
                                city_dropdown.setAdapter(citieslistadapter);
                                city_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        String citiy_selected = (String) adapterView.getItemAtPosition(i);

                                        //add clinics to dropdown
                                        clinicslist.clear();
                                        for(ClinicsClass clinicsClass : all_clinicsClass){
                                            if(clinicsClass.getCity().equals(citiy_selected))
                                                clinicslist.add(clinicsClass.getClinic());
                                        }
                                        ArrayAdapter<String> clinicslistadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, clinicslist);
                                        clinic_dropdown.setAdapter(clinicslistadapter);
                                        clinic_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                String clinic_selected = (String) adapterView.getItemAtPosition(i);

                                                //add times for the clinic
                                                availableTimes_clinic.clear();
                                                for(AvailableTimesListUserClass temp : all_availableTimes){
                                                    Log.i("!!!!", clinic_selected + " " + temp.getClinic());
                                                    if(temp.getClinic().equals(clinic_selected)) {
                                                        Log.i("!!!!", "adding" + temp.getTimestamp());
                                                        availableTimes_clinic.add(temp);
                                                    }
                                                }
                                                uppdateRadioButtons(radioGroup);
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) { }
                                        });
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) { }
                                });
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) { }
                        });
                    }
                });
                //gets current amount of vaccines
                /*
                pfizerRef = FirebaseDatabase.getInstance().getReference().child("Moderna");
                pfizerRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        countPfizer = (long) snapshot.getValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

                modernaRef = FirebaseDatabase.getInstance().getReference().child("Moderna");
                modernaRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        countModerna = (long) snapshot.getValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });*/

                //vaccine dropdown
                ArrayAdapter<String> vaccinelistadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, vaccinelist);
                vaccine_dropdown.setAdapter(vaccinelistadapter);

                Kalender.setFirstDayOfWeek(2);


                final long days22=1900800000;
                userref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        DataSnapshot dataSnapshot1= task.getResult();
                        RegClass usergetearlist = dataSnapshot1.getValue(RegClass.class);
                        if (usergetearlist.getDosOne() != 0){
                            Log.d("Dos1wasnotnull","Dos1wasnotnull");
                            Kalender.setMinDate(usergetearlist.getDosOne()+days22);
                            long temp = usergetearlist.getDosOne()+days22;
                            Log.d("Dos1wasnotnull",String.valueOf(temp));
                        }
                        else Kalender.setMinDate(Kalender.getDate());
                    }
                });


                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

                Kalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                        choosedYear = year; //sparar år
                        choosedDay = day; //Sparar månad
                        choosedMonth = month; //sparar dag

                        uppdateRadioButtons(radioGroup);
                    }
                });

                Log.d("Selected",booking.city);
                //button book appointment
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

                        //get data from all textfields
                        booking.allergy = text_allergies.getText().toString(); //sträng med allergier
                        booking.meds = text_medicines.getText().toString(); // sträng med mediciner
                        booking.message = text_comments.getText().toString(); // sträng med kommentarer
                        booking.clinic = clinic_dropdown.getSelectedItem().toString();
                        booking.city = city_dropdown.getSelectedItem().toString();
                        booking.county = county_dropdown.getSelectedItem().toString();
                        booking.vaccine = vaccine_dropdown.getSelectedItem().toString();


                        ref = FirebaseDatabase.getInstance().getReference().child(booking.vaccine);

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                countVaccine = (long) snapshot.getValue();
                                Log.d("Vaccine amount", String.valueOf(countVaccine));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });
                        //Combine all info and put in alert window


                        //String information = "Datum: " + booking.date + "\nTid: " + booking.time + "\nVaccin: " + booking.vaccine + "\nKlinik: " + booking.clinic + " " + booking.city;

                        String information = "Datum: " + choosedDay + "/" + (int)(choosedMonth+1) + "\nTid: " + booking.time + "\nVaccin: " + booking.vaccine + "\nKlinik: " + booking.clinic + " " + booking.city;

                        new AlertDialog.Builder(getActivity())

                                .setTitle("Din bokning")
                                .setMessage(information+"\n\nVill du bekräfta?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(countVaccine == 0){
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle("Ett problem uppstod!")
                                                    .setMessage(booking.vaccine + ": är just nu slut" + "\nBoka annat vaccin eller va vänlig och vänta.")
                                                    .setNeutralButton("Gå tillbaka", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                        }
                                                    }).show();
                                        }
                                        else{

                                            Navigation.findNavController(view).navigate(R.id.action_nav_booking_to_nav_my_page);
                                            //save booking to firebase
                                            ConnectUserWithTime(booking,date,availableTimes_clinic);
                                            //decrease vaccine
                                            ref.setValue(countVaccine-1);
                                        }
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                })
                                .show();

                        //Create new class and import to database
                        //ConnectUserWithTime(booking,date);
                    }
                });


            }
        });


    }

    //uppdates the radio buttons with the available times
    private void uppdateRadioButtons(RadioGroup radioGroup){
        Activity activity = getActivity();
        Calendar date2 = Calendar.getInstance();
        date2.set(choosedYear,choosedMonth,choosedDay);
        Toast.makeText(getActivity().getBaseContext(),sdf.format(date2.getTimeInMillis()),Toast.LENGTH_SHORT).show();

        //Set appointments only for today
        String currdate = sdf.format(date2.getTimeInMillis());

        //Log.i("!!!!", "HELLO");
        radioGroup.removeAllViews();

        Collections.sort(availableTimes_clinic);

        for(AvailableTimesListUserClass temp : availableTimes_clinic) {

            //Log.i("!!!!", currdate + " " + temp.getTimestamp());
            if (currdate.equals(sdf.format(temp.getTimestamp()))) {
                RadioButton newRadioButton = new RadioButton(activity);
                newRadioButton.setText(sdfclock.format(temp.getTimestamp()));
                newRadioButton.setId(View.generateViewId());
                radioGroup.addView(newRadioButton);
            }
        }
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

            //Dedug  help
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
        final long bookeddate = Userclass.getTimestamp();
        if ( currentUser==null){
            Toast.makeText(getActivity().getBaseContext(),"User Not logged in",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            String UID = currentUser.getUid();
            Userclass.setBookedBy(UID);
            if(booking.allergy.equals("") && booking.meds.equals("") && booking.message.equals(""))
            {
                //Approved if nothing is entered.
                Userclass.setApproved(true);
            }
            else
            {
                Userclass.setApproved(false);
            }

            Userclass.setAvailable(false);
            //Userclass.setId(UUID.randomUUID().toString());
            DatabaseReference myRef = database.getReference("BookedTimes").child(Userclass.getId());
            myRef.setValue(Userclass);

            DatabaseReference myRef3 = database.getReference("AvailableTimes").child(Userclass.getId());
            myRef3.removeValue();

        }
        // Todo: HÄR HAR VI ÄNDRAT SABINA NEDANFÖR
        DatabaseReference myRef = database.getReference("User").child(currentUser.getUid());
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot1= task.getResult();

                //getting all appointments booked by the user
                RegClass regClass = dataSnapshot1.getValue(RegClass.class);
                if (regClass.getDosOne() == 0){
                    regClass.setDosOne(bookeddate);
                    myRef.removeValue();
                    myRef.setValue(regClass);
                }else if ( regClass.getDosTwo()==0){
                    regClass.setDosTwo(bookeddate);
                    myRef.removeValue();
                    myRef.setValue(regClass);
                }
            }
        });

    }
}