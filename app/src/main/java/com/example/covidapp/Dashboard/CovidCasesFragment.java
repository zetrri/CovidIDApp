package com.example.covidapp.Dashboard;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentCovidCasesBinding;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CovidCasesFragment() {
        // Required empty public constructor
    }

    public static CovidCasesFragment newInstance(String param1, String param2) {
        CovidCasesFragment fragment = new CovidCasesFragment();
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

        createCasesAgegroupGraph();
        createCasesRegionGraph();
        createDeathsAgegroupGraph();
        createDeathsRegionGraph();
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
                if(first_set_buttons.equals("cases"))
                    container.addView(casesRegionGraph);
                else
                    container.addView(deathsRegionGraph);

            }
        });


    }

    public void createCasesAgegroupGraph(){
        casesAgegroupGraph = new HorizontalBarChart(getContext());

        final ArrayList<String> yLables = new ArrayList<>();
        yLables.add("9-10");
        yLables.add("11-20");
        yLables.add("21-30");
        casesAgegroupGraph.getAxisRight().setValueFormatter(new IndexAxisValueFormatter(yLables));
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));
        BarDataSet set = new BarDataSet(entries, "Antal fall per åldersgrupp");

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width




        casesAgegroupGraph.setData(data);
        casesAgegroupGraph.setFitBars(true); // make the x-axis fit exactly all bars
        casesAgegroupGraph.invalidate(); // refresh
    }

    public void createCasesRegionGraph(){
        casesRegionGraph = new HorizontalBarChart(getContext());
    }

    public void createDeathsAgegroupGraph(){
        deathsAgegroupGraph = new HorizontalBarChart(getContext());
    }

    public void createDeathsRegionGraph(){
        deathsRegionGraph = new HorizontalBarChart(getContext());
    }
}