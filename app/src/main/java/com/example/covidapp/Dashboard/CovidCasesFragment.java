package com.example.covidapp.Dashboard;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentCovidCasesBinding;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CovidCasesFragment extends Fragment {
    private FragmentCovidCasesBinding binding;
    private HorizontalBarChart casesAgegroupGraph;
    private HorizontalBarChart casesRegionGraph;
    private HorizontalBarChart deathsAgegroupGraph;
    private HorizontalBarChart deathsRegionGraph;
    private String first_set_buttons = "cases";
    private String second_set_buttons = "agegroup";
    private String cases_sum;
    private String deaths_sum;


    public CovidCasesFragment() {
        // Required empty public constructor
    }

    public static CovidCasesFragment newInstance(String param1, String param2) {
        CovidCasesFragment fragment = new CovidCasesFragment();
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
        binding = FragmentCovidCasesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CardView container = binding.container;
        TextView cases = binding.casesButton;
        TextView deaths = binding.deathsButton;
        TextView agegroup = binding.agegroupButton;
        TextView region = binding.regionButton;
        TextView total_sweden = binding.totalSweden;

        JSONDownloader jsonDownloader = (JSONDownloader) getArguments().getSerializable("jsonDownloader");
        String [][] ageArray = jsonDownloader.getAgeDoubleArray();
        String [][] regionArray = jsonDownloader.getRegionDoubleArray();

        createCasesAgegroupGraph(ageArray);
        createCasesRegionGraph(regionArray);
        createDeathsAgegroupGraph(ageArray);
        createDeathsRegionGraph(regionArray);

        container.addView(casesAgegroupGraph);

        cases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_set_buttons = "cases";
                deaths.setBackgroundColor(Color.rgb(207, 207, 207));
                cases.setBackgroundColor(Color.rgb(128, 128, 128));
                container.removeAllViews();
                if(second_set_buttons.equals("agegroup"))
                    container.addView(casesAgegroupGraph);
                else
                    container.addView(casesRegionGraph);

            }
        });
        deaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_set_buttons = "deaths";
                cases.setBackgroundColor(Color.rgb(207, 207, 207));
                deaths.setBackgroundColor(Color.rgb(128, 128, 128));
                container.removeAllViews();
                if(second_set_buttons.equals("agegroup"))
                    container.addView(deathsAgegroupGraph);
                else
                    container.addView(deathsRegionGraph);

            }
        });
        agegroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                second_set_buttons = "agegroup";
                region.setBackgroundColor(Color.rgb(207, 207, 207));
                agegroup.setBackgroundColor(Color.rgb(128, 128, 128));
                container.removeAllViews();
                if(first_set_buttons.equals("cases"))
                    container.addView(casesAgegroupGraph);
                else
                    container.addView(deathsAgegroupGraph);
            }
        });
        region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                second_set_buttons = "region";
                agegroup.setBackgroundColor(Color.rgb(207, 207, 207));
                region.setBackgroundColor(Color.rgb(128, 128, 128));
                container.removeAllViews();
                if(first_set_buttons.equals("cases")){
                    total_sweden.setText(cases_sum);
                    container.addView(casesRegionGraph);
                }
                else{
                    total_sweden.setText(deaths_sum);
                    container.addView(deathsRegionGraph);
                }
            }
        });
    }

    public void createCasesAgegroupGraph(String[][] ageArray){
        casesAgegroupGraph = new HorizontalBarChart(getContext());
        XAxis xAxis = casesAgegroupGraph.getXAxis();
        YAxis yAxis = casesAgegroupGraph.getAxisLeft();

        ArrayList<String> yLabels = new ArrayList<>();
        List<BarEntry> entries = new ArrayList<>();

        int [] max_arr = new int[ageArray.length];
        for(int i=0; i<ageArray.length; i++){
            yLabels.add(ageArray[i][0]);
            entries.add(new BarEntry(i, Float.parseFloat(ageArray[i][1])));
            max_arr[i] = Integer.parseInt(ageArray[i][1]);
        }


        xAxis.setValueFormatter(new IndexAxisValueFormatter(yLabels));
        xAxis.setLabelCount(ageArray.length);
        xAxis.setDrawGridLines(false);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(arrayMax(max_arr)+(arrayMax(max_arr)/5));
        yAxis.setDrawGridLines(false);

        BarDataSet set = new BarDataSet(entries, "Antal fall per åldersgrupp");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width

        set.setValueTextSize(10);
        set.setColors(getColors());

        casesAgegroupGraph.setData(data);
        casesAgegroupGraph.getDescription().setEnabled(false);
        casesAgegroupGraph.setFitBars(true); // make the x-axis fit exactly all bars
        casesAgegroupGraph.invalidate(); // refresh
    }

    public void createCasesRegionGraph(String[][] regionArray){
        casesRegionGraph = new HorizontalBarChart(getContext());
        XAxis xAxis = casesRegionGraph.getXAxis();
        YAxis yAxis = casesRegionGraph.getAxisLeft();

        ArrayList<String> yLabels = new ArrayList<>();
        List<BarEntry> entries = new ArrayList<>();

        int [] int_arr = new int[regionArray.length];
        yLabels.add((regionArray[0][0]));
        entries.add(new BarEntry(0, Float.parseFloat(regionArray[0][1])));
        for(int i=2; i<regionArray.length; i++){
            yLabels.add(regionArray[i][0]); // add lables
            entries.add(new BarEntry(i-1, Float.parseFloat(regionArray[i][1]))); // add values
            int_arr[i] = Integer.parseInt(regionArray[i][1]);
        }

        cases_sum = "Totalt Sverige: " + arraySum(int_arr);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(yLabels));
        xAxis.setLabelCount(regionArray.length);
        xAxis.setDrawGridLines(false);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(arrayMax(int_arr)+(arrayMax(int_arr)/5));
        yAxis.setDrawGridLines(false);

        BarDataSet set = new BarDataSet(entries, "Antal avlidna per åldersgrupp");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width

        set.setValueTextSize(10);
        set.setColors(getColors());

        casesRegionGraph.setData(data);
        casesRegionGraph.getDescription().setEnabled(false);
        casesRegionGraph.setFitBars(true); // make the x-axis fit exactly all bars
        casesRegionGraph.invalidate(); // refresh
    }

    public void createDeathsAgegroupGraph(String[][] ageArray){
        deathsAgegroupGraph = new HorizontalBarChart(getContext());
        XAxis xAxis = deathsAgegroupGraph.getXAxis();
        YAxis yAxis = deathsAgegroupGraph.getAxisLeft();

        ArrayList<String> yLabels = new ArrayList<>();
        List<BarEntry> entries = new ArrayList<>();

        int [] max_arr = new int[ageArray.length];
        for(int i=0; i<ageArray.length; i++){
            yLabels.add(ageArray[i][0]); // add lables
            entries.add(new BarEntry(i, Float.parseFloat(ageArray[i][2]))); // add values
            max_arr[i] = Integer.parseInt(ageArray[i][2]);
        }

        xAxis.setValueFormatter(new IndexAxisValueFormatter(yLabels));
        xAxis.setLabelCount(ageArray.length);
        xAxis.setDrawGridLines(false);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(arrayMax(max_arr)+(arrayMax(max_arr)/5));
        yAxis.setDrawGridLines(false);

        BarDataSet set = new BarDataSet(entries, "Antal avlidna per åldersgrupp");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width

        set.setValueTextSize(10);
        set.setColors(getColors());

        deathsAgegroupGraph.setData(data);
        deathsAgegroupGraph.getDescription().setEnabled(false);
        deathsAgegroupGraph.setFitBars(true); // make the x-axis fit exactly all bars
        deathsAgegroupGraph.invalidate(); // refresh
    }

    public void createDeathsRegionGraph(String[][] regionArray){
        deathsRegionGraph = new HorizontalBarChart(getContext());

        //TODO hämta in labels från filen
        String[] regions = new String[] {"Sverige", "Stockholm", "Uppsala", "Södermanland", "Östergötland", "Jönköping",
                "Kronoberg", "Kalmar", "Gotland", "Blekinge", "Skåne", "Halland", "Västra Götaland", "Värmland", "Örebro",
                "Västmanland", "Dalarna", "Gävleborg", "Västernorrland", "Jämtland", "Västerbotten", "Norrbotten"};
        deathsRegionGraph.getXAxis().setValueFormatter(new IndexAxisValueFormatter(regions));
        deathsRegionGraph.getXAxis().setLabelCount(22); //TODO antal från filen
        deathsRegionGraph.getXAxis().setDrawGridLines(false);
        deathsRegionGraph.getAxisLeft().setDrawGridLines(false);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        entries.add(new BarEntry(4f, 50f));
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));
        entries.add(new BarEntry(7f, 70f));
        entries.add(new BarEntry(8f, 60f));
        entries.add(new BarEntry(9f, 60f));
        entries.add(new BarEntry(10f, 30f));
        entries.add(new BarEntry(11f, 80f));
        entries.add(new BarEntry(12f, 60f));
        entries.add(new BarEntry(13f, 50f));
        entries.add(new BarEntry(14f, 50f));
        entries.add(new BarEntry(15f, 70f));
        entries.add(new BarEntry(16f, 60f));
        entries.add(new BarEntry(17f, 70f));
        entries.add(new BarEntry(18f, 60f));
        entries.add(new BarEntry(19f, 60f));
        entries.add(new BarEntry(20f, 30f));
        entries.add(new BarEntry(21f, 80f));
        BarDataSet set = new BarDataSet(entries, "Antal avlidna per region");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width

        set.setColors(getColors());

        deathsRegionGraph.setData(data);
        deathsRegionGraph.getDescription().setEnabled(false);
        deathsRegionGraph.setFitBars(true); // make the x-axis fit exactly all bars
        deathsRegionGraph.invalidate(); // refresh
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