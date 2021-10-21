package com.example.covidapp.HealthAdmin;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.Booking.Booking;
import com.example.covidapp.ClinicsClass;
import com.example.covidapp.R;
import com.example.covidapp.UserReg.RegClass;
import com.example.covidapp.databinding.FragmentAdminAppointmentsBinding;
import com.example.covidapp.databinding.FragmentAdminMenuBinding;
import com.example.covidapp.databinding.FragmentBookingBinding;
import com.example.covidapp.databinding.FragmentMyPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

public class AdminAppointmentsFragment extends Fragment {
    private Booking booking = new Booking("", "", "", "", "", "", "", "", "", "", "");
    private FragmentAdminAppointmentsBinding binding;

    public AdminAppointmentsFragment() {
        // Required empty public constructor
    }

    public static AdminAppointmentsFragment newInstance(String param1, String param2) { AdminAppointmentsFragment fragment = new AdminAppointmentsFragment();
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
        binding = FragmentAdminAppointmentsBinding.inflate(inflater, container, false);
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

        //hardcoded
        vaccinelist.add("Alla");
        vaccinelist.add("Pfizer");
        vaccinelist.add("Moderna");

        //Removes keyboard if up
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        LinearLayout container = binding.containerbookings;


            //Getting bookings from firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference ref = database.getReference("BookedTimes");
            ArrayList<AvailableTimesListUserClass> all_bookedTimes = new ArrayList<>();
            ArrayList<AvailableTimesListUserClass> chosen_clinic_bookedTimes = new ArrayList<>();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
                    //if user not logged in
                    if (firebaseAuth1.getCurrentUser() == null) {
                        return;
                    }

                    //If logged in
                    else {
                        //counts children under "Booked times"(Not used)
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            //adding all booking information into class
                            AvailableTimesListUserClass availableTimesListUserClass = dataSnapshot1.getValue(AvailableTimesListUserClass.class);
                            if (!availableTimesListUserClass.getApproved().equals(false)) {
                                Log.d("FoundOne", availableTimesListUserClass.getId());
                                all_bookedTimes.add(availableTimesListUserClass);
                            }
                        }

                        //Binding
                        Spinner county_dropdown = binding.dropdownCounty;
                        Spinner city_dropdown = binding.dropdownCity;
                        Spinner clinic_dropdown = binding.dropdownClinic;

                        ArrayList<ClinicsClass> all_clinicsClass = new ArrayList<>();

                        //Creating the dropdown menu's from database
                        DatabaseReference clinicsRef = database.getReference("Kliniker");
                        clinicsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                for(DataSnapshot dataSnapshot1: task.getResult().getChildren()){
                                    all_clinicsClass.add(dataSnapshot1.getValue(ClinicsClass.class));
                                }

                                //counties dropdown
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

                                        //cities dropdown
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

                                                //clinics dropdown
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

                                                        //vaccine dropdown
                                                        Spinner vaccine_dropdown = binding.dropdownVaccine;
                                                        ArrayAdapter<String> vaccinelistadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, vaccinelist);
                                                        vaccine_dropdown.setAdapter(vaccinelistadapter);
                                                        vaccine_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                                                                String vaccine_selected = (String) adapterView.getItemAtPosition(pos);

                                                                //vilken klinik och vaccin som är valt
                                                                container.removeAllViews();
                                                                chosen_clinic_bookedTimes.clear();
                                                                for(AvailableTimesListUserClass temp : all_bookedTimes){
                                                                    if(temp.clinic.equals(clinic_selected)){
                                                                        if(vaccine_selected.equals("Pfizer")){
                                                                            if(temp.vaccine.equals("Pfizer"))
                                                                                chosen_clinic_bookedTimes.add(temp);
                                                                        }
                                                                        else if(vaccine_selected.equals("Moderna")){
                                                                            if(temp.vaccine.equals("Moderna"))
                                                                                chosen_clinic_bookedTimes.add(temp);
                                                                        }
                                                                        else
                                                                            chosen_clinic_bookedTimes.add(temp);
                                                                    }
                                                                }

                                                                //Calendar and formatting helpers
                                                                Calendar date = Calendar.getInstance();
                                                                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                                                                SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
                                                                for (int i = 0; i < chosen_clinic_bookedTimes.size(); i++) {

                                                                    //Making a card for each appointment
                                                                    date.setTimeInMillis(chosen_clinic_bookedTimes.get(i).getTimestamp());
                                                                    Date date1 = date.getTime();

                                                                    CardView new_card = new CardView(getActivity());
                                                                    new_card.setId(i + 1);
                                                                    LinearLayout linear_layout1 = new LinearLayout(getActivity());
                                                                    linear_layout1.setOrientation(LinearLayout.VERTICAL);
                                                                    TextView date_time = new TextView(getActivity());

                                                                    //date_time.setText(String.valueOf(date1.getDay())+"/"+String.valueOf(date1.getMonth())+"-"+date1.getYear());
                                                                    date_time.setText(sdf.format(date1) + " " + sdfclock.format(date1));
                                                                    date_time.setTextSize(20);
                                                                    date_time.setBackgroundColor(Color.parseColor("#228B22"));
                                                                    date_time.setTextColor(Color.WHITE);
                                                                    linear_layout1.addView(date_time);

                                                                    TextView clinic_text = new TextView(getActivity());
                                                                    clinic_text.setTextSize(15);
                                                                    clinic_text.setText((String) ("Mottagning: " + chosen_clinic_bookedTimes.get(i).getClinic() + " " + chosen_clinic_bookedTimes.get(i).getCity()));
                                                                    linear_layout1.addView(clinic_text);

                                                                    TextView vaccin_text = new TextView(getActivity());
                                                                    vaccin_text.setTextSize(15);
                                                                    vaccin_text.setText((String) ("Vaccin: " + chosen_clinic_bookedTimes.get(i).getVaccine())); //ska vara vaccine
                                                                    linear_layout1.addView(vaccin_text);


                                                                    TextView test_text = new TextView(getActivity());
                                                                    test_text.setTextSize(15);
                                                                    test_text.setText(chosen_clinic_bookedTimes.get(i).getId());
                                                                    test_text.setVisibility(TextView.GONE);
                                                                    linear_layout1.addView(test_text);

                                                                    TextView test_text2 = new TextView(getActivity());
                                                                    test_text2.setTextSize(15);
                                                                    test_text2.setText(String.valueOf(i));
                                                                    test_text2.setVisibility(TextView.GONE);
                                                                    linear_layout1.addView(test_text2);

                                                                    //TextViews for more information on cards
                                                                    TextView allergies_text = new TextView(getActivity());
                                                                    allergies_text.setTextSize(15);
                                                                    allergies_text.setText((String) ("Allergier: ") + chosen_clinic_bookedTimes.get(i).getAllergies());

                                                                    TextView county_text = new TextView(getActivity());
                                                                    county_text.setTextSize(15);
                                                                    county_text.setText((String) ("Län: ") + chosen_clinic_bookedTimes.get(i).getCounty());

                                                                    TextView city_text = new TextView(getActivity());
                                                                    city_text.setTextSize(15);
                                                                    city_text.setText((String) ("Stad: ") + chosen_clinic_bookedTimes.get(i).getCity());

                                                                    TextView comments_text = new TextView(getActivity());
                                                                    comments_text.setTextSize(15);
                                                                    comments_text.setText((String) ("Kommentarer: ") + chosen_clinic_bookedTimes.get(i).getComments());

                                                                    TextView medication_text = new TextView(getActivity());
                                                                    medication_text.setTextSize(15);
                                                                    medication_text.setText((String) ("Mediciner: ") + chosen_clinic_bookedTimes.get(i).getMedication());

                                                                    // These TextViews uses below completelistener
                                                                    TextView FirstName_text = new TextView(getActivity());
                                                                    FirstName_text.setTextSize(15);

                                                                    //Gets User ID first
                                                                    String UID = chosen_clinic_bookedTimes.get(i).getBookedBy();


                                                                    TextView persnr_text = new TextView(getActivity());
                                                                    persnr_text.setTextSize(15);

                                                                    TextView nummer_text = new TextView(getActivity());
                                                                    nummer_text.setTextSize(15);

                                                                    TextView LastName_text = new TextView(getActivity());
                                                                    LastName_text.setTextSize(15);

                                                                    TextView person_text = new TextView(getActivity());
                                                                    person_text.setTextSize(15);


                                                                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
                                                                    DatabaseReference ref = database.getReference("User");
                                                                    ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                                            for (DataSnapshot dataSnapshot1: task.getResult().getChildren()){

                                                                                //getting all appointments booked by the user
                                                                                RegClass regClass = dataSnapshot1.getValue(RegClass.class);
                                                                                if (regClass.getUserID().equals(UID)){
                                                                                    Log.d("BOOKEDBY",regClass.getFirstname().toString());
                                                                                    FirstName_text.setText("Namn: " + regClass.getFirstname());
                                                                                    persnr_text.setText("Personnummer: " + regClass.getPersnr());
                                                                                    nummer_text.setText("Telefon: " + regClass.getPhonenr());
                                                                                    LastName_text.setText("Efternamn: " + regClass.getLastname());
                                                                                    person_text.setText("Namn: " + regClass.getFirstname() + " " + regClass.getLastname());
                                                                                }
                                                                            }

                                                                        }
                                                                    });

                                                                    linear_layout1.addView(person_text);

                                                                    LinearLayout linear_layout2 = new LinearLayout(getActivity());
                                                                    linear_layout2.setOrientation(LinearLayout.HORIZONTAL);
                                                                    linear_layout2.setPadding(0, 0, 0, 16);
                                                                    linear_layout2.setGravity(Gravity.RIGHT);

                                                                    Button buttonInformation = new Button(getActivity());
                                                                    buttonInformation.setText("Mer info");
                                                                    linear_layout2.addView(buttonInformation);
                                                                    buttonInformation.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {
                                                                            new AlertDialog.Builder(getActivity())
                                                                                    .setTitle("Boknings information")
                                                                                    //information retrived from firebase
                                                                                    .setMessage("Datum: " + date_time.getText() + "\n"
                                                                                            + county_text.getText() + "\n"
                                                                                            + city_text.getText() + "\n"
                                                                                            + clinic_text.getText() + "\n"
                                                                                            + FirstName_text.getText() + "\n"
                                                                                            + LastName_text.getText() + "\n"
                                                                                            + persnr_text.getText() + "\n"
                                                                                            + nummer_text.getText() + "\n"
                                                                                            + medication_text.getText() + "\n"
                                                                                            + allergies_text.getText() + "\n"
                                                                                            + comments_text.getText()).setPositiveButton("Klar", null).show();

                                                                        }
                                                                    });
                                                                    linear_layout1.addView(linear_layout2);
                                                                    new_card.addView(linear_layout1);
                                                                    container.addView(new_card);
                                                                }
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
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) { }
                                });
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
         }
}