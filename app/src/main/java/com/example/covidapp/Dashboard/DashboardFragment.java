package com.example.covidapp.Dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.covidapp.MainActivity;
import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private ExcelDownloader excelDownloader = null;
    private JSONDownloader jsonDownloader = null;
    private Bundle excelBundle = null;
    private Bundle jsonBundle = null;
    private  int jsonCounter = 0;
    private Bundle administeredBundle = null;
    private Bundle distributedBundle = null;
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
        Bundle b = getActivity().getIntent().getExtras();
        try {
            Log.i("onResume()",((ExcelDownloader) b.getSerializable("excelDownloader")).toString());
            excelDownloader = (ExcelDownloader) b.getSerializable("excelDownloader");
            excelBundle = new Bundle();
            excelBundle.putSerializable("jsonDownloader", excelDownloader);
            Log.i("excelDownloader", excelDownloader.getCumulativeUptakeArray()[0][0]);
            jsonDownloader = (JSONDownloader) b.getSerializable("jsonDownloader");
            jsonBundle = new Bundle();
            jsonBundle.putSerializable("jsonDownloader", jsonDownloader);
        }
        catch (Exception e){
            excelDownloader = new ExcelDownloader();
            jsonDownloader = new JSONDownloader();

            BroadcastReceiver excelDownloaderReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    excelBundle = new Bundle();
                    excelBundle.putSerializable("excelDownloader", excelDownloader);
                }
            };
            BroadcastReceiver jsonDownloaderRegionReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    jsonCounter++;
                    if(jsonCounter == 2){
                        jsonBundle = new Bundle();
                        jsonBundle.putSerializable("jsonDownloader", jsonDownloader);
                    }
                }
            };
            BroadcastReceiver jsonDownloaderAgeReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    jsonCounter++;
                    if(jsonCounter == 2){
                        jsonBundle = new Bundle();
                        jsonBundle.putSerializable("jsonDownloader", jsonDownloader);
                    }
                }
            };

            getActivity().registerReceiver(excelDownloaderReceiver, new IntentFilter("EXCEL_DOWNLOAD_COMPLETE"));
            getActivity().registerReceiver(jsonDownloaderRegionReceiver, new IntentFilter("JSON_DOWNLOAD_COMPLETE_REGION"));
            getActivity().registerReceiver(jsonDownloaderAgeReceiver, new IntentFilter("JSON_DOWNLOAD_COMPLETE_AGE"));

            excelDownloader.startDownload(getActivity());
            jsonDownloader.startDownload(getActivity());
        }
    }

    @Override
    public void onPause() {
        getActivity().getIntent().putExtra("excelDownloader",((ExcelDownloader) excelBundle.getSerializable("excelDownloader")));
        getActivity().getIntent().putExtra("jsonDownloader", ((JSONDownloader) excelBundle.getSerializable("jsonDownloader")));
        super.onPause();
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

        cases_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(excelBundle == null){
                    return; //TODO FIXA SNURR GREJ "Laddar ner filer, vänligen vänta"
                }
                fragment_covid_cases.setArguments(jsonBundle);

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.cardView, fragment_covid_cases).commit();
            }
        });
        vaccinated_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(jsonBundle == null){
                    return; //TODO FIXA SNURR GREJ "Laddar ner filer, vänligen vänta"
                }
                fragment_vaccines_administered.setArguments(excelBundle);

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.cardView, fragment_vaccines_administered).commit();
            }
        });
        distributed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(jsonBundle == null){
                    return; //TODO FIXA SNURR GREJ "Laddar ner filer, vänligen vänta"
                }
                fragment_vaccines_distributed.setArguments(excelBundle);

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.cardView, fragment_vaccines_distributed).commit();
            }
        });
        graph_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(jsonBundle == null){
                    return; //TODO FIXA SNURR GREJ "Laddar ner filer, vänligen vänta"
                }
                fragment_cumulative_uptake.setArguments(excelBundle);

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.cardView, fragment_cumulative_uptake).commit();
            }
        });
    }

}