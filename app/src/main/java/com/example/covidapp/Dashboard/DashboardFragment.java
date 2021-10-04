package com.example.covidapp.Dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentDashboardBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle bundle = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        bundle.putBoolean("mode", true);
        fragment.setArguments(bundle);
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
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button,button_deathsandcases,buttonAgeProduct,buttonCumelative;
        button_deathsandcases = binding.buttonDeathsandCases;
        button = binding.buttonDisDoses;
        buttonCumelative = binding.buttonCumelative;
        buttonAgeProduct = binding.button;
        buttonCumelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity().getBaseContext(),ActivityDistrubatedDoses.class);
                Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_nav_distributed_doses);

            }
        });
        buttonAgeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getBaseContext(), ActivityDosesAgeProduct.class);
//                startActivity(intent);
                Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_nav_doses_age_product);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getBaseContext(), ActivityDistrubatedDoses.class);
//
//                intent.putExtra("mode",true);
//                startActivity(intent);
//                DistributedDosesFragment fragment = new DistributedDosesFragment();
//                Bundle bundle = new Bundle();
//                bundle.putBoolean("mode", true);
//                fragment.setArguments(bundle);
                Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_nav_distributed_doses);
            }
        });
        button_deathsandcases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getBaseContext(),ActivityDeathsAndCases.class);
//                startActivity(intent);
                Navigation.findNavController(view).navigate(R.id.action_nav_dashboard_to_nav_deaths_and_cases);
            }
        });
    }

}