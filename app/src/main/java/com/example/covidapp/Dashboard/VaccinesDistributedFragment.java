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
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentVaccinesDistributedBinding;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.StackedValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class VaccinesDistributedFragment extends Fragment {
    private FragmentVaccinesDistributedBinding binding;
    private HorizontalBarChart graph;
    float total_pfizer = 0;
    float total_moderna = 0;
    DecimalFormat formatter = new DecimalFormat();

    public VaccinesDistributedFragment() {
        // Required empty public constructor
    }

    public static VaccinesDistributedFragment newInstance(String param1, String param2) {
        VaccinesDistributedFragment fragment = new VaccinesDistributedFragment();
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
        binding = FragmentVaccinesDistributedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExcelDownloader excelDownloader = (ExcelDownloader) getArguments().getSerializable("excelDownloader");
        String [][] distributedArray = excelDownloader.getDosesDistributedArray();

        TextView total_sweden = binding.totalSweden;
        CardView container = binding.container;

        createGraph(distributedArray);
        total_sweden.setText("Totalt Sverige Pfizer: " + formatter.format(total_pfizer) + " doser\nTotalt Sverige Moderna: " + formatter.format(total_moderna) + " doser");
        container.addView(graph);

    }

    public void createGraph(String[][] distributedArray){
        graph = new HorizontalBarChart(getContext());
        XAxis xAxis = graph.getXAxis();
        YAxis leftAxis = graph.getAxisLeft();

        // Hämtar Labels
        ArrayList <String> regions = new ArrayList<>();
        for(int i=5; i<distributedArray.length-4; i++){
            regions.add(distributedArray[i][0]);
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(regions));
        xAxis.setLabelCount(regions.size());
        xAxis.setDrawGridLines(false);
        xAxis.setGranularityEnabled(true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        //Räknar ut totalt för sverige antal pfizer och moderna doser
        for(int i=0; i<distributedArray[0].length; i++){
            if(distributedArray[4][i]!=null){
                if(distributedArray[3][i].contains("Pfizer"))
                    total_pfizer = total_pfizer + Float.parseFloat(distributedArray[4][i]);
                else if (distributedArray[3][i].equals("Moderna"))
                    total_moderna = total_moderna + Float.parseFloat(distributedArray[4][i]);
            }
        }

        // hämtar värden för alla län
        List<BarEntry> entries = new ArrayList<>();
        for(int i=5; i<distributedArray.length-4; i++){
            float sum_pfizer = 0;
            float sum_moderna = 0;
            for(int j=0; j<distributedArray[i].length; j++){
                if(distributedArray[i][j]!=null){
                    if(distributedArray[3][j].contains("Pfizer"))
                        sum_pfizer = sum_pfizer + Float.parseFloat(distributedArray[i][j]);
                    else if (distributedArray[3][j].equals("Moderna"))
                        sum_moderna = sum_moderna + Float.parseFloat(distributedArray[i][j]);
                }
                entries.add(new BarEntry(i-5, new float[]{sum_moderna, sum_pfizer}));
            }
        }

        BarDataSet set = new BarDataSet(entries, "      Antal levererade doser");
        set.setColors(getColors());
        set.setStackLabels(new String[]{"Modena", "Pfizer/BioNTech"});

        BarData data = new BarData(set);
        data.setDrawValues(false);
        //data.setValueFormatter(new StackedValueFormatter(false, " ", 0));
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
    private int arrayMax(int[] arr){
        int max = 0;
        for(int i=0; i<arr.length; i++){
            if (arr[i] > max)
                max = arr[i];
        }
        return max;
    }

    private int arraySum (int [] arr){
        int sum=0;
        for(int i=0; i<arr.length; i++)
            sum = sum + arr[i];
        return sum;
    }
}