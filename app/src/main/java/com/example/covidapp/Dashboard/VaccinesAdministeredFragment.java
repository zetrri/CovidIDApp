package com.example.covidapp.Dashboard;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentVaccinesAdministeredBinding;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VaccinesAdministeredFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaccinesAdministeredFragment extends Fragment {
    private FragmentVaccinesAdministeredBinding binding;
    private HorizontalBarChart graph;

    public VaccinesAdministeredFragment() {
        // Required empty public constructor
    }

    public static VaccinesAdministeredFragment newInstance(String param1, String param2) {
        VaccinesAdministeredFragment fragment = new VaccinesAdministeredFragment();
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
        binding = FragmentVaccinesAdministeredBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView container = binding.container;

        //TODO ska egentligen hämtas från filen
        Spinner region_spinner = binding.spinner;
        String[] regions = new String[] {"Sverige", "Stockholm", "Uppsala", "Södermanland", "Östergötland", "Jönköping",
                "Kronoberg", "Kalmar", "Gotland", "Blekinge", "Skåne", "Halland", "Västra Götaland", "Värmland", "Örebro",
                "Västmanland", "Dalarna", "Gävleborg", "Västernorrland", "Jämtland", "Västerbotten", "Norrbotten"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, regions);
        region_spinner.setAdapter(adapter);

        //lyssna på spinnern
        region_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String region = region_spinner.getItemAtPosition(position).toString();
                createGraph(region);
                container.removeAllViews();
                container.addView(graph);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void createGraph(String region){
        graph = new HorizontalBarChart(getContext());

        //TODO hämta labels från filen
        ArrayList<String> yLabels = new ArrayList<>(Arrays.asList("16-17 år", "18-29 år", "30-39 år", "40-49 år", "50-59 år", "60-69 år", "70-79 år", "80-89 år", "90 år +"));
        graph.getXAxis().setValueFormatter(new IndexAxisValueFormatter(yLabels));
        graph.getXAxis().setLabelCount(9); //TODO antal från filen
        graph.getXAxis().setDrawGridLines(false);
        graph.getAxisLeft().setAxisMinimum(0f);
        graph.getAxisLeft().setDrawGridLines(false);

        //TODO hitta rätt region
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, new float[]{56839/*TODO färdigvaccinerad*/, 221369-56839/*TODO minst 1 dos - färdigvaccinerade*/}));
        entries.add(new BarEntry(1f, new float[]{799852, 1068050-799852}));
        entries.add(new BarEntry(2f, new float[]{870090, 1035606-870090}));
        entries.add(new BarEntry(3f, new float[]{988614, 1076674-988614}));
        entries.add(new BarEntry(4f, new float[]{1102940, 1153460-1102940}));
        entries.add(new BarEntry(5f, new float[]{983995, 1013470-983995}));
        entries.add(new BarEntry(6f, new float[]{928244, 952056-928244}));
        entries.add(new BarEntry(7f, new float[]{404101, 418524-404101}));
        entries.add(new BarEntry(8f, new float[]{85894, 90665-85894}));
        BarDataSet set = new BarDataSet(entries, "      Antal vaccinerade i " + region);
        set.setColors(getColors());
        set.setStackLabels(new String[]{"Färdigvaccinerad", "Minst 1 dos"});

        BarData data = new BarData(set);
        data.setDrawValues(false);
        data.setBarWidth(0.8f); // set custom bar width

        graph.setData(data);
        graph.getDescription().setEnabled(false);
        graph.setFitBars(true); // make the x-axis fit exactly all bars
        graph.invalidate(); // refresh
    }
    private int[] getColors() {
        int [] color = {Color.rgb(187, 134, 252), Color.rgb(140, 234, 255)};

        return color;
    }
}