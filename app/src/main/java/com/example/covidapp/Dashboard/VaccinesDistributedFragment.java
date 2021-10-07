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

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentVaccinesDistributedBinding;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VaccinesDistributedFragment extends Fragment {
    private FragmentVaccinesDistributedBinding binding;
    private HorizontalBarChart graph;

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

        CardView container = binding.container;
        createGraph();
        container.addView(graph);

        //TODO kanske att sverige blir för stort jämfört med resten - lägg till en total mängd för sverige i text isåfall

    }

    public void createGraph(){
        graph = new HorizontalBarChart(getContext());

        //TODO hämta labels från filen
        String[] regions = new String[] {"Sverige", "Stockholm", "Uppsala", "Södermanland", "Östergötland", "Jönköping",
                "Kronoberg", "Kalmar", "Gotland", "Blekinge", "Skåne", "Halland", "Västra Götaland", "Värmland", "Örebro",
                "Västmanland", "Dalarna", "Gävleborg", "Västernorrland", "Jämtland", "Västerbotten", "Norrbotten"};
        graph.getXAxis().setValueFormatter(new IndexAxisValueFormatter(regions));
        graph.getXAxis().setLabelCount(22); //TODO antal labels från filen
        graph.getXAxis().setDrawGridLines(false);
        graph.getAxisLeft().setDrawGridLines(false);
        graph.getAxisLeft().setAxisMinimum(0f);

        //TODO hitta rätt region

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, new float[]{56839/*TODO moderna*/, /*TODO pfizer*/221369-56839}));
        entries.add(new BarEntry(1f, new float[]{799852, 1068050-799852}));
        entries.add(new BarEntry(2f, new float[]{870090, 1035606-870090}));
        entries.add(new BarEntry(3f, new float[]{988614, 1076674-988614}));
        entries.add(new BarEntry(4f, new float[]{1102940, 1153460-1102940}));
        entries.add(new BarEntry(5f, new float[]{983995, 1013470-983995}));
        entries.add(new BarEntry(6f, new float[]{928244, 952056-928244}));
        entries.add(new BarEntry(7f, new float[]{404101, 418524-404101}));
        entries.add(new BarEntry(8f, new float[]{85894, 90665-85894}));
        entries.add(new BarEntry(9f, new float[]{988614, 1076674-988614}));
        entries.add(new BarEntry(10f, new float[]{56839, 221369-56839}));
        entries.add(new BarEntry(11f, new float[]{799852, 1068050-799852}));
        entries.add(new BarEntry(12f, new float[]{870090, 1035606-870090}));
        entries.add(new BarEntry(13f, new float[]{988614, 1076674-988614}));
        entries.add(new BarEntry(14f, new float[]{1102940, 1153460-1102940}));
        entries.add(new BarEntry(15f, new float[]{983995, 1013470-983995}));
        entries.add(new BarEntry(16f, new float[]{928244, 952056-928244}));
        entries.add(new BarEntry(17f, new float[]{404101, 418524-404101}));
        entries.add(new BarEntry(18f, new float[]{85894, 90665-85894}));
        entries.add(new BarEntry(19f, new float[]{988614, 1076674-988614}));
        entries.add(new BarEntry(20f, new float[]{56839, 221369-56839}));
        entries.add(new BarEntry(21f, new float[]{799852, 1068050-799852}));
        BarDataSet set = new BarDataSet(entries, "      Antal levererade doser");
        set.setColors(getColors());
        set.setStackLabels(new String[]{"Modena", "Pfizer/BioNTech"});

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