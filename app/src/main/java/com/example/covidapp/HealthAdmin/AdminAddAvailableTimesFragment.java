package com.example.covidapp.HealthAdmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.covidapp.Dashboard.Data;
import com.example.covidapp.R;
import com.example.covidapp.UserReg.RegClass;
import com.example.covidapp.databinding.FragmentAdminAddAvailableTimesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminAddAvailableTimesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminAddAvailableTimesFragment extends Fragment {

    private FragmentAdminAddAvailableTimesBinding binding;

    Button buttonAddTimes;
    TimePicker timePicker;
    DatePicker datePicker;
    ListView listView;
    int day,month,year,minute,hour;
    Calendar date;
    DateFormat dateFormat;
    String pattern = "MM/dd/yyyy HH:mm";

    ArrayList<String> calendarsInString;
    Spinner spinnerCities, spinnerCounties, spinnerClinics;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminAddAvailableTimesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminAddAvailableTimesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminAddAvailableTimesFragment newInstance(String param1, String param2) {
        AdminAddAvailableTimesFragment fragment = new AdminAddAvailableTimesFragment();
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
        binding = FragmentAdminAddAvailableTimesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonAddTimes = binding.buttonAddTime;
        timePicker = binding.datePickerAdminAddTimes;
        datePicker = binding.calendarViewChooseDate;
        listView = binding.listviewAdminAddedTimes;
        //calendars = new ArrayList<>();
        calendarsInString = new ArrayList<>();
        date = Calendar.getInstance();
        dateFormat = new SimpleDateFormat(pattern);
        ArrayList<AvailableTimesListUserClass> timesfromDatabase = new ArrayList<>();
        ArrayAdapter<AvailableTimesListUserClass> timesfromDatabaseadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, timesfromDatabase);
        //listView.setAdapter(timesfromDatabaseadapter);
        timePicker.setIs24HourView(true);


        ArrayList<String> Counties = new ArrayList<>();
        Counties.add("Värmland län");
        Counties.add("Örebro län");
        Counties.add("Västergötland län");
        ArrayList<String> Cities = new ArrayList<>();
        Cities.add("Örebro");
        Cities.add("Karlstad");
        Cities.add("Götebord");
        ArrayList<String> Clinics = new ArrayList<>();
        Clinics.add("Adolfsbergs vårdcentral");
        Clinics.add("Ruds vårdcentral");
        Clinics.add("Kronoparkens vårdcentral");
        ArrayAdapter<String> CountyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Counties);
        ArrayAdapter<String> CitiesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Cities);
        ArrayAdapter<String> ClinicAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Clinics);
        spinnerCities = binding.spinnerCity;
        spinnerCounties = binding.spinnerCounty;
        spinnerClinics = binding.spinnerClinic;
        spinnerCities.setAdapter(CitiesAdapter);
        spinnerCounties.setAdapter(CountyAdapter);
        spinnerClinics.setAdapter(ClinicAdapter);

        buttonAddTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String City = (String)spinnerCities.getSelectedItem();
                String County = (String)spinnerCounties.getSelectedItem();
                String Clinic = (String)spinnerClinics.getSelectedItem();
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();
                minute = timePicker.getMinute();
                hour = timePicker.getHour();

                String uniqueID = UUID.randomUUID().toString();
                Log.d("Date",String.valueOf(minute));
                date.set(year,month,day,hour,minute);
                AvailableTimesListUserClass availableTimesListUserClass = new AvailableTimesListUserClass(City,County,Clinic,date.getTimeInMillis(),uniqueID,"",true,"","","","");
//                calendars.add(date);
//                calendarsInString.add(dateFormat.format(date.getTime()));
//
//                listView.setAdapter(calendarArrayAdapter);
                SendToDatabase(availableTimesListUserClass);

            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");

        DatabaseReference ref = database.getReference("AvailableTimes");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    AvailableTimesListUserClass availableTimesListUserClass = dataSnapshot1.getValue(AvailableTimesListUserClass.class);
                    //timesfromDatabase.add(availableTimesListUserClass);
                    Calendar date1 = Calendar.getInstance();
                    date1.setTimeInMillis(availableTimesListUserClass.getTimestamp());
                    Log.d("Found",date1.toString());
                }
                //ArrayAdapter<AvailableTimesListUserClass> timesfromDatabaseadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, timesfromDatabase);
                //listView.setAdapter(timesfromDatabaseadapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    private void SendToDatabase(AvailableTimesListUserClass availableTimesListUserClass){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("AvailableTimes").child(availableTimesListUserClass.id);
        myRef.setValue(availableTimesListUserClass);
    }
}