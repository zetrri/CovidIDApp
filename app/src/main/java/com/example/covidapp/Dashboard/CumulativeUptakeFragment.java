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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentCumulativeUptakeBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CumulativeUptakeFragment extends Fragment {
    private FragmentCumulativeUptakeBinding binding;
    private LineChart weekChart;
    private LineChart monthChart;
    private String selection = "week";

    public CumulativeUptakeFragment() {
        // Required empty public constructor
    }

    public static CumulativeUptakeFragment newInstance(String param1, String param2) {
        CumulativeUptakeFragment fragment = new CumulativeUptakeFragment();
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
        binding = FragmentCumulativeUptakeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView container = binding.container;

        ExcelDownloader excelDownloader = (ExcelDownloader) getArguments().getSerializable("excelDownloader");
        String [][] cumulativeArray = excelDownloader.getCumulativeUptakeArray();

        //TextView week_button = binding.weekButton;
        //TextView month_button = binding.monthButton;

        //Add labels from file
        List<String> regions = new ArrayList<String>();
        for(int i=1; i<cumulativeArray.length; i++){
            if(!regions.contains(cumulativeArray[i][2]) && cumulativeArray[i][2]!=null)
                regions.add(cumulativeArray[i][2]);

        }
        Spinner region_spinner = binding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, regions);
        region_spinner.setAdapter(adapter);

        //lyssna på spinnern
        region_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String region = region_spinner.getItemAtPosition(position).toString();
                container.removeAllViews();
                createWeekChart(region, cumulativeArray);
                //createMonthChart(region);
                if(selection.equals("week"))
                    container.addView(weekChart);
                else
                    container.addView(monthChart);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
/*
        week_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "week";
                month_button.setBackgroundColor(Color.rgb(207, 207, 207));
                week_button.setBackgroundColor(Color.rgb(128, 128, 128));
                container.removeAllViews();
                container.addView(weekChart);
            }
        });
        month_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "month";
                week_button.setBackgroundColor(Color.rgb(207, 207, 207));
                month_button.setBackgroundColor(Color.rgb(128, 128, 128));
                container.removeAllViews();
                container.addView(monthChart);
            }
        });*/
    }

    public void createWeekChart(String region, String[][] cumulativeArray){
        weekChart = new LineChart(getActivity());
        XAxis xAxis = weekChart.getXAxis();
        YAxis yAxis = weekChart.getAxisLeft();

        yAxis.setAxisMinimum(0f);
        yAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //Adding the weeks from the file
        ArrayList<String> weeks = new ArrayList<>();
        for(int i=1; i<cumulativeArray.length; i++){
            if(cumulativeArray[i][0]!=null && !cumulativeArray[i][0].equals(cumulativeArray[i-1][0]))
                weeks.add(cumulativeArray[i][0]);
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weeks));
        xAxis.setLabelCount(weeks.size());
        xAxis.setGranularityEnabled(true);

        //Add values
        ArrayList<Entry> one_dose = new ArrayList<>();
        ArrayList<Entry> complete_vacc = new ArrayList<>();
        int j=0;
        int k=0;
        for(int i=0; i<cumulativeArray.length; i++){
            if(cumulativeArray[i][0]!=null && cumulativeArray[i][2].equals(region)){
                if(cumulativeArray[i][5].equals("Minst 1 dos")){
                    one_dose.add(new Entry(j, Float.parseFloat(cumulativeArray[i][4]))); j++;}
                else if (cumulativeArray[i][5].equals("Färdigvaccinerade")){
                    complete_vacc.add(new Entry(k, Float.parseFloat(cumulativeArray[i][4]))); k++; }

            }
        }

        List<ILineDataSet> entries = new ArrayList<>();
        LineDataSet set1 = new LineDataSet(one_dose, "Minst 1 dos");
        LineDataSet set2 = new LineDataSet(complete_vacc, "Färdigvaccinerad");

        weekChart.setDragEnabled(true);
        weekChart.setScaleEnabled(true);

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setDrawCircles(false);
        set1.setFillAlpha(255);
        set1.setColor(Color.rgb(187, 134, 252));
        set1.setDrawFilled(true);
        set1.setFillColor(Color.rgb(187, 134, 252));

        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setDrawCircles(false);
        set2.setColor(Color.rgb(140, 234, 255));
        set2.setFillAlpha(255);
        set2.setDrawFilled(true);
        set2.setFillColor(Color.rgb(140, 234, 255));

        LineData data = new LineData(set1,set2);
        data.setDrawValues(false);

        weekChart.setData(data);
        weekChart.getDescription().setEnabled(false);
        weekChart.invalidate(); // refresh
    }
    /*public void createMonthChart(String region){
        monthChart = new LineChart(getActivity());

        //TODO hitta rätt region

        monthChart.getAxisLeft().setAxisMinimum(0f);
        XAxis xAxis = monthChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        int count = 52; // TODO hämta från filen -> räkna hur många olika veckor/4 som finns i filen
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i<count; i++){
            labels.add(Integer.toString(i)); //TODO lägg till månaderna
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelCount(count);

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            float val = (float) (i*i+20);
            values1.add(new Entry(i, val));
        }
        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            float val = (float) (i*i);
            values2.add(new Entry(i, val));
        }

        List<ILineDataSet> entries = new ArrayList<>();
        LineDataSet set1 = new LineDataSet(values1, "Minst 1 dos");
        LineDataSet set2 = new LineDataSet(values2, "Färdigvaccinerad");

        monthChart.setDragEnabled(true);
        monthChart.setScaleEnabled(true);

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setDrawCircles(false);
        set1.setFillAlpha(255);
        set1.setColor(Color.rgb(187, 134, 252));
        set1.setDrawFilled(true);
        set1.setFillColor(Color.rgb(187, 134, 252));

        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setDrawCircles(false);
        set2.setColor(Color.rgb(140, 234, 255));
        set2.setFillAlpha(255);
        set2.setDrawFilled(true);
        set2.setFillColor(Color.rgb(140, 234, 255));

        LineData data = new LineData(set1,set2);
        data.setDrawValues(false);

        monthChart.setData(data);
        monthChart.getDescription().setEnabled(false);
        monthChart.invalidate(); // refresh
    }*/
}