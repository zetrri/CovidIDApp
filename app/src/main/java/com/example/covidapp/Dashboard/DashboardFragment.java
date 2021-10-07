package com.example.covidapp.Dashboard;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.covidapp.MainActivity;
import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    DashboardFragment this_obj = this;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("mode", true);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Fragment fragment_covid_cases = new CovidCasesFragment();
        Fragment fragment_vaccines_administered = new VaccinesAdministeredFragment();
        Fragment fragment_vaccines_distributed = new VaccinesDistributedFragment();
        Fragment fragment_cumulative_uptake = new CumulativeUptakeFragment();

        CardView cardview = binding.cardView;

        LinearLayout cases_button = binding.sickButton;
        LinearLayout vaccinated_button = binding.vaccineButton;
        LinearLayout distributed_button = binding.truckButton;
        LinearLayout graph_button = binding.graphButton;
        LinearLayout update_button = binding.updateButton;

        cases_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.cardView, fragment_covid_cases).commit();
            }
        });
        vaccinated_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.cardView, fragment_vaccines_administered).commit();
            }
        });
        distributed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.cardView, fragment_vaccines_distributed).commit();
            }
        });
        graph_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.cardView, fragment_cumulative_uptake).commit();
            }
        });
    }

}