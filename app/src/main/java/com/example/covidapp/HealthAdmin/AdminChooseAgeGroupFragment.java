package com.example.covidapp.HealthAdmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentAdminChooseAgeGroupBinding;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminChooseAgeGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminChooseAgeGroupFragment extends Fragment {
    private FragmentAdminChooseAgeGroupBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminChooseAgeGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminChooseAgeGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminChooseAgeGroupFragment newInstance(String param1, String param2) {
        AdminChooseAgeGroupFragment fragment = new AdminChooseAgeGroupFragment();
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
        binding = FragmentAdminChooseAgeGroupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    EditText editTextYear,editTextMonth,editTextDay;
    String choosed;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         editTextYear = binding.editTextNumberYear;
         editTextMonth = binding.editTextNumberMonth;
         editTextDay = binding.editTextNumberDay;
         choosed = "0";
        Button buttonChange = binding.buttonChangeAgeMin;

        buttonChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                choosed = editTextYear.getText().toString()+editTextMonth.getText().toString()+editTextDay.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
                database.getReference("Minbookingage").removeValue();
                database.getReference("Minbookingage").setValue(Integer.parseInt(choosed));
            }
        });

    }

}