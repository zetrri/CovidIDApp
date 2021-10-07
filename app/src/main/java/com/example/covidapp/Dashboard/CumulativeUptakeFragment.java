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

public class CumulativeUptakeFragment extends Fragment {
    private FragmentCumulativeUptakeBinding binding;
    private String region;
    private LineChart chart;

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
                region = region_spinner.getItemAtPosition(position).toString();
                createGraph();
                container.removeAllViews();
                container.addView(chart);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void createGraph(){
        chart = new LineChart(getActivity());

        chart.getAxisLeft().setAxisMinimum(0f);

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            float val = (float) (i*i+50);
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

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

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

        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.invalidate(); // refresh
    }
}