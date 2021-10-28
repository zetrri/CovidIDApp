package com.example.covidapp.HealthAdmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.R;
import com.example.covidapp.UserReg.RegClass;
import com.example.covidapp.databinding.FragmentConnectedTimesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.ss.formula.functions.T;

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
    Button buttonValidate;
    TextView textViewNameInput,textViewName,textViewComment;
    ImageView imageViewCheck,imageViewError;
    EditText editTextIDinput;

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
        buttonValidate = binding.buttonValidateID;
        textViewName = binding.textViewValidateName;
        textViewNameInput = binding.textViewValidateNameinput;
        editTextIDinput = binding.editTextIDtoVailidate;
        imageViewCheck = binding.imageViewCheckmark;
        imageViewError = binding.imageViewError;
        textViewComment = binding.textviewComment;

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextIDinput.getText().toString().length()>0){
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
                    DatabaseReference ref = database.getReference("User");
                    ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            for (DataSnapshot dataSnapshot1: task.getResult().getChildren()){

                                //getting all appointments booked by the user
                                RegClass regClass = dataSnapshot1.getValue(RegClass.class);
                                if (regClass.getUserID().equals(editTextIDinput.getText().toString())){
                                    Log.d("BOOKEDBY",regClass.getFirstname().toString());
                                    imageViewError.setVisibility(View.GONE);
                                    textViewName.setVisibility(View.VISIBLE);
                                    textViewNameInput.setVisibility(View.VISIBLE);
                                    textViewNameInput.setText(regClass.getFirstname() + " "+regClass.getLastname());
                                    textViewComment.setText("Detta ID är Validerat");
                                    textViewComment.setVisibility(View.VISIBLE);
                                    imageViewCheck.setVisibility(View.VISIBLE);
                                    return;

                                }

                            }
                            textViewComment.setText("Detta ID är ej Validerat");
                            textViewComment.setVisibility(View.VISIBLE);
                            textViewName.setVisibility(View.GONE);
                            textViewNameInput.setVisibility(View.GONE);
                            imageViewCheck.setVisibility(View.GONE);
                            imageViewError.setVisibility(View.VISIBLE);
                            return;


                        }
                    });
                }
                else Toast.makeText(getActivity(),"Skriv ett id att validera",Toast.LENGTH_LONG).show();
            }
        });


    }
}