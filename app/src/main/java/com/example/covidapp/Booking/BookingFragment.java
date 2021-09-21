package com.example.covidapp.Booking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

import com.example.covidapp.MyPage.MainMyPage;
import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentBookingBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        // Kod för dom olika dropdown menyerna över län, stad och klinik
//        Spinner county_dropdown = findViewById(R.id.dropdown_county);
        Spinner county_dropdown = binding.dropdownCounty;
        String[] counties = new String[] {"Värmland", "Västra Götaland"}; // TODO kommer hämtas från databas
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, counties);
        county_dropdown.setAdapter(adapter1);

        //***************** Exempel på att rätt län har rätt städer ****************
        // Kommer ändras när vi implementerar databaser
        county_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                booking.county = (String) adapterView.getItemAtPosition(position); // sparar län
                //TODO jämför med databas och hämta rätt städer
                if(booking.county == "Västra Götaland"){
                    cities =  new String[] {"Göteborg", "Alingsås"};
                }else{
                    cities = new String[] {"Karlstad"};
                }
                Spinner city_dropdown = binding.dropdownCity;
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, cities);
                city_dropdown.setAdapter(adapter2);

                city_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        booking.city = (String) adapterView.getItemAtPosition(position); // sparar stad
                        //TODO jämför med databas
                        if(booking.city.equals("Karlstad")){
                            clinics =  new String[] {"Gripen", "Kronoparken", "Rud"};
                        }else{
                            clinics = new String[] {"Sollebrunn", "Nolhaga"};
                        }
                        Spinner clinic_dropdown = binding.dropdownClinic;
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, clinics);
                        clinic_dropdown.setAdapter(adapter3);

                        clinic_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                booking.clinic = (String) adapterView.getItemAtPosition(position); // sparar klinik
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //************************************************************************************

        //dropdown för att välja vaccin
        Spinner vaccine_dropdown = binding.dropdownVaccine;
        String[] vaccines = new String[] {"Phizer", "Moderna", "NovaVax"}; //TODO hämta från databas
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, vaccines);
        vaccine_dropdown.setAdapter(adapter4);

        //lyssna på dropdownen
        vaccine_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                booking.vaccine = vaccine_dropdown.getItemAtPosition(position).toString(); // sparar vaccinet valt
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        RadioGroup radioGroup = binding.radGroup1;

        // få ut datum från kalendern
        CalendarView Kalender = binding.calendarView;
        TextView error = binding.errormessage;
        Kalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                booking.date = day + "/" + month + "-" + year; // valt datum
                String curDate = String.valueOf(day);
                List<String> freetimes = new ArrayList<>();

                // exempel på att hitta rätt tider för rätt dag, kommer ändras när databaser implementeras
                if(curDate.equals("1")) {
                    radioGroup.removeAllViews();
                    String[] dates = new String[]{"11:00", "11:15", "12:30", "13:00", "13:45", "14:15"}; //TODO hämta från databas
                    Collections.addAll(freetimes, dates);

                }
                else if(curDate.equals("2")){
                    radioGroup.removeAllViews();
                    String[] dates= new String[]{"11:30", "13:15", "14:00"}; //TODO hämta från databas
                    Collections.addAll(freetimes, dates);
                }

                //Skapa Radiobuttons
                int stringcount = freetimes.size(); // hur många tider den dagen
                for (int i=0; i<stringcount; i++){
                    RadioButton newRadioButton = new RadioButton(getActivity());
                    newRadioButton.setText(freetimes.get(i));
                    newRadioButton.setId(View.generateViewId());
                    radioGroup.addView(newRadioButton);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                error.setVisibility(View.INVISIBLE);
            }
        });


        //när man trycker på boka vaccin
        Button button = binding.button;
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
                EditText text_allergies = (EditText) binding.editTextAllergies;
                EditText text_medicines = (EditText) binding.editTextMedicine;
                EditText text_comments = (EditText) binding.editTextComments;
                booking.allergy = text_allergies.getText().toString(); //sträng med allergier
                booking.meds = text_medicines.getText().toString(); // sträng med mediciner
                booking.message = text_comments.getText().toString(); // sträng med kommentarer

                String information = "Datum: " + booking.date + "\nTid: " + booking.time + "\nVaccin: " + booking.vaccine + "\nKlinik: " + booking.clinic + " " + booking.city;

                new AlertDialog.Builder(getActivity())
                        .setTitle("Din bokning")
                        .setMessage(information+"\n\nVill du bekräfta?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getActivity().getBaseContext(), MainMyPage.class);
                                startActivity(intent);
                                // TODO spara informationen i en databas och gå till min sida
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();

            }
        });
    }

}