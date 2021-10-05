package com.example.covidapp.HealthAdmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentAdminAddAvailableTimesBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    ArrayList<Calendar> calendars;
    ArrayList<String> calendarsInString;

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
        calendars = new ArrayList<>();
        calendarsInString = new ArrayList<>();
        date = Calendar.getInstance();
        dateFormat = new SimpleDateFormat(pattern);
        ArrayAdapter<String> calendarArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, calendarsInString);
        listView.setAdapter(calendarArrayAdapter);
        timePicker.setIs24HourView(true);

        buttonAddTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth();
                year = datePicker.getYear();
                minute = timePicker.getMinute();
                hour = timePicker.getHour();

                date.set(year,month,day,hour,minute);
                calendars.add(date);
                calendarsInString.add(dateFormat.format(date.getTime()));

                listView.setAdapter(calendarArrayAdapter);

            }
        });
    }
}