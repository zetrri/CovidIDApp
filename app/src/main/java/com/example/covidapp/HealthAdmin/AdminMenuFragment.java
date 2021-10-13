package com.example.covidapp.HealthAdmin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentAdminMenuBinding;
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
    private TextView registered_users;
    private int countUsers = 0;
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
//        LinearLayout eCatalog = binding.Catalog;
        LinearLayout eDashboard = binding.Dashboard;
//        LinearLayout eSecDose = binding.SecDose;
        LinearLayout eQuestionnaire = binding.Questionnaire;
//        LinearLayout eTimeLine = binding.TimeLine;
//        LinearLayout eHealthPass = binding.HealthPass;
        firebaseAuth = FirebaseAuth.getInstance();
        //making a reference to the child node "User"
        usersRef = FirebaseDatabase.getInstance().getReference().child("User");

        registered_users = binding.eUserReg;

        //Removes keyboard if up
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //Removes keyboard if up

        //Listeners, replace activity when implemented.

       /*eCatalog.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               Intent intent = new Intent(getBaseContext(), ReplaceWithActivity.class);
               startActivity(intent);
           }
       });*/
        eDashboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_admin_menu_to_admin_appointments);
            }
        });
        /*eSecDose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReplaceWithActivity..class);
                startActivity(intent);
            }
        });*/

        eQuestionnaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_nav_admin_menu_to_questionnaire_response);
            }
        });
        /*
        eTimeLine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReplaceWithActivity..class);
                startActivity(intent);
            }
        });
        eHealthPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ReplaceWithActivity..class);
                startActivity(intent);
            }
        });*/

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

    }


}