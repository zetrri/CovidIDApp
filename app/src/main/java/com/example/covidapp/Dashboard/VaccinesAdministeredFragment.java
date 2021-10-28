package com.example.covidapp.Dashboard;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentVaccinesAdministeredBinding;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.slider.LabelFormatter;

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

        ExcelDownloader excelDownloader = (ExcelDownloader) getArguments().getSerializable("excelDownloader");
        String [][] dosesArray = excelDownloader.getDosesAdministratedArray();

        CardView container = binding.container;
        Spinner region_spinner = binding.spinner;

        //Lägger till labels
        ArrayList<String> regions = new ArrayList<String>();
        for(int i=1; i<dosesArray.length; i++){
            if(!regions.contains(dosesArray[i][0]))
                regions.add(dosesArray[i][0]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, regions);
        region_spinner.setAdapter(adapter);

        //lyssna på spinnern
        region_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String region = region_spinner.getItemAtPosition(position).toString();
                createGraph(region, dosesArray);
                container.removeAllViews();
                container.addView(graph);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void createGraph(String region, String [][] dosesArray){
        graph = new HorizontalBarChart(getContext());
        XAxis xAxis = graph.getXAxis();
        YAxis leftAxis = graph.getAxisLeft();

        //get agegroup labels
        ArrayList<String> yLabels = new ArrayList<String>();
        for(int i=1; i<dosesArray.length; i++){
            if(dosesArray[i][1].equals("Totalt"))
                break;
            yLabels.add(dosesArray[i][1]);
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(yLabels));
        xAxis.setLabelCount(yLabels.size());
        xAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);

        ArrayList <Float> one_dose_arr = new ArrayList<>();
        ArrayList <Float> complete_vacc_arr = new ArrayList<>();

        //Get values from array
        for(int i=1; i<dosesArray.length; i++){
            if(dosesArray[i][0].equals(region)){
                if(dosesArray[i][4].equals("Minst 1 dos"))
                    one_dose_arr.add( Float.parseFloat(dosesArray[i][2]));
                else
                    complete_vacc_arr.add(Float.parseFloat(dosesArray[i][2]));
            }
        }
        //ad values
        List<BarEntry> entries = new ArrayList<>();
        int [] int_arr = new int[complete_vacc_arr.size()];
        for(int i=0; i<one_dose_arr.size()-1; i++){
            entries.add(new BarEntry(i, new float[]{complete_vacc_arr.get(i), one_dose_arr.get(i) - complete_vacc_arr.get(i)}));
            int_arr[i]= Math.round(one_dose_arr.get(i));
        }

        leftAxis.setAxisMaximum(arrayMax(int_arr)+arrayMax(int_arr)/5);

        BarDataSet set = new BarDataSet(entries, "      Antal vaccinerade i " + region);
        set.setColors(getColors());
        set.setStackLabels(new String[]{"Färdigvaccinerad", "Minst 1 dos"});
        set.setValueTextSize(10);

        BarData data = new BarData(set);
        data.setDrawValues(true);
        data.setBarWidth(0.8f); // set custom bar width
        data.setValueFormatter(new StackedValueFormatter(false, " ", 0));

        graph.setData(data);
        graph.getDescription().setEnabled(false);
        graph.setFitBars(true); // make the x-axis fit exactly all bars
        graph.invalidate(); // refresh
    }
    private int[] getColors() {
        int [] color = {Color.rgb(187, 134, 252), Color.rgb(140, 234, 255)};

        return color;
    }
    private int arrayMax(int[] arr){
        int max = 0;
        for(int i=0; i<arr.length; i++){
            if (arr[i] > max)
                max = arr[i];
        }
        return max;
    }
}