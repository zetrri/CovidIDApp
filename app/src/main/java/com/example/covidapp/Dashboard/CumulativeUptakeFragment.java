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
        //TextView week_button = binding.weekButton;
        //TextView month_button = binding.monthButton;
        Spinner region_spinner = binding.spinner;

        //TODO ska egentligen hämtas från filen
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
                container.removeAllViews();
                createWeekChart(region);
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

    public void createWeekChart(String region){
        weekChart = new LineChart(getActivity());

        //TODO hitta rätt region

        weekChart.getAxisLeft().setAxisMinimum(0f);
        weekChart.getAxisLeft().setEnabled(false);
        XAxis xAxis = weekChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        int count = 52; // TODO hämta från filen -> räkna hur många olika veckor som finns i filen
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i<count; i++){
            labels.add(Integer.toString(i)); //TODO lägg till veckonummrena
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelCount(count);

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (i*i+i*i); //TODO lägg till minst 1 dos värderna
            values1.add(new Entry(i, val));
        }
        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (i*i); //TODO lägg till färdigvaccinerade värderna
            values2.add(new Entry(i, val));
        }

        List<ILineDataSet> entries = new ArrayList<>();
        LineDataSet set1 = new LineDataSet(values1, "Minst 1 dos");
        LineDataSet set2 = new LineDataSet(values2, "Färdigvaccinerad");

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