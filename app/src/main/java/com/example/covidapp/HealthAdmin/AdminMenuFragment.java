package com.example.covidapp.HealthAdmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentAdminMenuBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminMenuFragment extends Fragment {

    private FragmentAdminMenuBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersRef;
    private DatabaseReference pfizerRef;
    private DatabaseReference modernaRef;

    private TextView registered_users;
    private TextView registerd_pfizers;
    private TextView registered_moderna;
    private int countUsers = 0;
    private long countPfizer = 0;
    private long countModerna = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminMenuFragment newInstance(String param1, String param2) {
        AdminMenuFragment fragment = new AdminMenuFragment();
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
        binding = FragmentAdminMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout eDashboard = binding.Dashboard;
        LinearLayout eQuestionnaire = binding.Questionnaire;
        LinearLayout eTimeLine = binding.TimeLine;
        //använd denna leon för pass
        LinearLayout ePass = binding.verificationPass;
        firebaseAuth = FirebaseAuth.getInstance();
        //making a reference to the child node "User"
        usersRef = FirebaseDatabase.getInstance().getReference().child("User");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        registered_users = binding.eUserReg;
        registerd_pfizers = binding.ePfizer;
        registered_moderna = binding.eModerna;

        //Removes keyboard if up
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //Removes keyboard if up

        //Listeners, replace activity when implemented.


        eDashboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_admin_menu_to_admin_appointments);
            }
        });


        eQuestionnaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_nav_admin_menu_to_questionnaire_response);
            }
        });

        eTimeLine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_admin_menu_to_admin_adminAgeGroup);
                //HÄR SKA TIMELINE VARA
            }
        });
        ePass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.ConnectedTimesFragment);
            }
        });

        //Total registered users
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    // count number of children for User child
                    countUsers = (int) snapshot.getChildrenCount();
                    // converting back to string and displaying
                    registered_users.setText("Total registrerande nu: " + Integer.toString(countUsers)); // displays amount of remaining attempts
                    registered_users.setTextColor(Color.parseColor("#228B22"));
                }
                else{
                    registered_users.setText("0 Registered Users.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        pfizerRef = FirebaseDatabase.getInstance().getReference().child("Pfizer");
        pfizerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countPfizer = (long) snapshot.getValue();
                if (countPfizer != 0){
                    registerd_pfizers.setText("Total Pfizers doser kvar: " + Long.toString(countPfizer)); // displays amount of remaining attempts
                    registerd_pfizers.setTextColor(Color.parseColor("#228B22"));
                }
                else{
                    registerd_pfizers.setText("0 Pfizers doses available.");
                    registerd_pfizers.setTextColor(Color.parseColor("#ba160c"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        modernaRef = FirebaseDatabase.getInstance().getReference().child("Moderna");
        modernaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countModerna = (long) snapshot.getValue();
                if (countModerna != 0){

                    countModerna = (long) snapshot.getValue();
                    registered_moderna.setText("Total Moderna doser kvar: " + Long.toString(countModerna)); // displays amount of remaining attempts
                    registered_moderna.setTextColor(Color.parseColor("#228B22"));
                }
                else{
                    registered_moderna.setText("0 Moderna doser kvar!");
                    registered_moderna.setTextColor(Color.parseColor("#ba160c"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

}