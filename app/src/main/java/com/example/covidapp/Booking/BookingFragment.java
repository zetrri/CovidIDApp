package com.example.covidapp.Booking;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userref = database.getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


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
                EditText text_allergies = (EditText) binding.editTextAllergies;
                EditText text_medicines = (EditText) binding.editTextMedicine;
                EditText text_comments = (EditText) binding.editTextComments;

                //set adapters
                vaccine_dropdown.setAdapter(vaccinelistadapter);
                county_dropdown.setAdapter(countieslistadapter);
                city_dropdown.setAdapter(citieslistadapter);
                clinic_dropdown.setAdapter(clinicslistadapter);

                SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
                Activity activity = getActivity();
                Kalender.setFirstDayOfWeek(2);

                // Todo: HÄR HAR VI ÄNDRAT SABINA NEDANFÖR
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
                // Todo: HÄR HAR VI ÄNDRAT SABINA OVANFÖR

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

                        //Set appointments only for today
                        String currdate = sdf.format(date2.getTimeInMillis());
                        radioGroup.removeAllViews();
                        for (int i=0; i<availableTimesListUserClassArrayList.size(); i++){
                            if (currdate.equals(sdf.format(availableTimesListUserClassArrayList.get(i).getTimestamp()))){
                                RadioButton newRadioButton = new RadioButton(activity);
                                newRadioButton.setText(sdfclock.format(availableTimesListUserClassArrayList.get(i).getTimestamp()));
                                newRadioButton.setId(View.generateViewId());
                                radioGroup.addView(newRadioButton);
                            }


                        }
                    }
                });



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

                        Log.d("Selected",booking.city);

                        //Combine all info and put in alert window
                        String information = "Datum: " + booking.date + "\nTid: " + booking.time + "\nVaccin: " + booking.vaccine + "\nKlinik: " + booking.clinic + " " + booking.city;
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Din bokning")
                                .setMessage(information+"\n\nVill du bekräfta?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Navigation.findNavController(view).navigate(R.id.action_nav_booking_to_nav_my_page);
                                        //save booking to firebase
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
        // Todo: HÄR HAR VI ÄNDRAT SABINA OVANFÖR

    }
}