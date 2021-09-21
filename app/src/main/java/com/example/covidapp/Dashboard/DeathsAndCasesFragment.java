package com.example.covidapp.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentDeathsAndCasesBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeathsAndCasesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeathsAndCasesFragment extends Fragment {

    private FragmentDeathsAndCasesBinding binding;
    Spinner spinnerage, spinnerDoses, spinnerConties;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeathsAndCasesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeathsAndCasesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeathsAndCasesFragment newInstance(String param1, String param2) {
        DeathsAndCasesFragment fragment = new DeathsAndCasesFragment();
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
        binding = FragmentDeathsAndCasesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Declaration of spinners
        spinnerage = binding.spinnerAge;
        spinnerDoses = binding.SpinnerDoses;
        spinnerConties = binding.SpinnerCounty;


        //Demo items for spinners
        String[] ages = new String[]{"All","1-19","20-40","40-60"};
        String[] Counties = new String[]{"All","Örebro","Värmland","Stockholm"};
        String[] Doses = new String[]{"None","1","2"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ages);
        ArrayAdapter<String> adapterCounites = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Counties);
        ArrayAdapter<String> adapterDoses = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Doses);

        spinnerage.setAdapter(adapter);
        spinnerConties.setAdapter(adapterCounites);
        spinnerDoses.setAdapter(adapterDoses);



        //Intent intent = new Intent(getBaseContext(), );


        //spinner on select listener
        spinnerage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                //just to make sure the listener works
                Toast.makeText(getActivity().getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}