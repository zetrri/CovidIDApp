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
    Fragment fragment_covid_cases;
    Fragment current_fragment;
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
            // Kollar om excel och JSON objecten blivit sparade, om dom inte blivit det så kommer den kasta ett error och få laddar
            // Vi ner dom i catch()
            excelDownloader = (ExcelDownloader) b.getSerializable("excelDownloader");
            excelBundle = new Bundle();
            excelBundle.putSerializable("jsonDownloader", excelDownloader);

            jsonDownloader = (JSONDownloader) b.getSerializable("jsonDownloader");
            jsonBundle = new Bundle();
            jsonBundle.putSerializable("jsonDownloader", jsonDownloader);
        }
        catch (Exception e){
            excelDownloader = new ExcelDownloader();
            jsonDownloader = new JSONDownloader();

            //Alla broadcast receivers lyssnar på när de filerna är färdiga
            BroadcastReceiver excelDownloaderReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    excelBundle = new Bundle();
                    excelBundle.putSerializable("excelDownloader", excelDownloader);

                    //Om någon har klickat på en annan knapp än "sjukdomsfall" och har loading screenen uppe så laddar den upp den sidan.
                    if(current_fragment != fragment_covid_cases){
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        ft.replace(R.id.cardView, current_fragment).commit();
                    }
                }
            };
            BroadcastReceiver jsonDownloaderRegionReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    jsonCounter++;
                    if(jsonCounter == 2){
                        jsonBundle = new Bundle();
                        jsonBundle.putSerializable("jsonDownloader", jsonDownloader);

                        fragment_covid_cases.setArguments(jsonBundle);

                        //När JSON filen är klar så byter den automatiskt fragment till covid_cases
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        ft.replace(R.id.cardView, fragment_covid_cases).commit();
                        current_fragment = fragment_covid_cases;
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
                        fragment_covid_cases.setArguments(jsonBundle);

                        //När JSON filen är klar så byter den automatiskt fragment till covid_cases
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                        ft.replace(R.id.cardView, fragment_covid_cases).commit();
                        current_fragment = fragment_covid_cases;
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

    //När man går ur dashboard så sparas excel och json objecten undan så man inte behöver ladda ner igen, är dom inte klara sparas de ej undan
    @Override
    public void onPause() {
        try {
            getActivity().getIntent().putExtra("excelDownloader", ((ExcelDownloader) excelBundle.getSerializable("excelDownloader")));
            getActivity().getIntent().putExtra("jsonDownloader", ((JSONDownloader) jsonBundle.getSerializable("jsonDownloader")));
        }catch (Exception e){
            excelDownloader.stopDownloads();
            Log.i("onPause()", "File not finished downloading");
        }
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

        fragment_covid_cases = new CovidCasesFragment();
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
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                if(jsonBundle == null){
                    removeFragment(ft, fragment_covid_cases);
                    return;
                }
                fragment_covid_cases.setArguments(jsonBundle);

                ft.replace(R.id.cardView, fragment_covid_cases).commit();
                current_fragment = fragment_covid_cases;
            }
        });
        vaccinated_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                if(excelBundle == null){
                    removeFragment(ft, fragment_vaccines_administered);
                    return;
                }
                fragment_vaccines_administered.setArguments(excelBundle);

                ft.replace(R.id.cardView, fragment_vaccines_administered).commit();
                current_fragment = fragment_vaccines_administered;
            }
        });
        distributed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                if(excelBundle == null){
                    removeFragment(ft, fragment_vaccines_distributed);
                    return;
                }
                fragment_vaccines_distributed.setArguments(excelBundle);

                ft.replace(R.id.cardView, fragment_vaccines_distributed).commit();
                current_fragment = fragment_vaccines_distributed;
            }
        });
        graph_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                if(excelBundle == null){
                    removeFragment(ft, fragment_cumulative_uptake);
                    return;
                }
                fragment_cumulative_uptake.setArguments(excelBundle);

                ft.replace(R.id.cardView, fragment_cumulative_uptake).commit();
                current_fragment = fragment_cumulative_uptake;
            }
        });
    }

    public void removeFragment(FragmentTransaction ft, Fragment this_fragment){
        try {
            ft.remove(current_fragment).commit();
            current_fragment = this_fragment;
        }catch (Exception e){
            Log.i("Nothing", "Serious");
        }
    }

}