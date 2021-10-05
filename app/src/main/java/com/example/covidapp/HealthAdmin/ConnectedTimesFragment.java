package com.example.covidapp.HealthAdmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentConnectedTimesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConnectedTimesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConnectedTimesFragment extends Fragment {
    private FragmentConnectedTimesBinding binding;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConnectedTimesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConnectedTimesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConnectedTimesFragment newInstance(String param1, String param2) {
        ConnectedTimesFragment fragment = new ConnectedTimesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    Button buttonConnectedTimes;
    ListView listViewConnectedTimes;

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
        // Inflate the layout for this fragment

        binding = FragmentConnectedTimesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonConnectedTimes = binding.buttoninConnectedTimes;
        listViewConnectedTimes = binding.listviewInConnectedTimes;
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        ArrayList<AvailableTimesListUserClass> availableTimesListUserClasseslist = new ArrayList<>();
//
//        database.getReference().child("users")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            AvailableTimesListUserClass availableTimesListUserClass = snapshot.getValue(AvailableTimesListUserClass.class);
//                            System.out.println(availableTimesListUserClass.city.toString());
//                            availableTimesListUserClasseslist.add(availableTimesListUserClass);
//
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//        ArrayAdapter<AvailableTimesListUserClass> availableTimesListUserClassArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1,availableTimesListUserClasseslist);
//        listViewConnectedTimes.se

    }
}