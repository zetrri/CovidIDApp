package com.example.covidapp.Notifications;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covidapp.HealthAdmin.AvailableTimesListUserClass;
import com.example.covidapp.R;
import com.example.covidapp.UserReg.RegClass;
import com.example.covidapp.databinding.FragmentNotificationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;

    final static int TWENTYTHREE_DAYS = 1987200000;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.notificationCard.setVisibility(View.INVISIBLE);
        binding.notificationTextview.setVisibility(View.VISIBLE);

        //Checks if two weeks has passed since the first dose was administered
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot1 = task.getResult();
                RegClass regClass = dataSnapshot1.getValue(RegClass.class);
                regClass.getDosOne();
                Calendar calendar = Calendar.getInstance();
                final long dos1 = regClass.getDosOne();
                final long dos2 = regClass.getDosTwo();
                final long currentTime = calendar.getTimeInMillis();
                if (dos1 == 1 || dos2 == 1 || dos1 + TWENTYTHREE_DAYS <= currentTime && dos1 != 0 && dos2 == 0) {
                    binding.notificationCard.setVisibility(View.VISIBLE);
                    binding.notificationTextview.setVisibility(View.INVISIBLE);
                    if(dos1 == 1 || dos2 == 1) {
                        binding.message.setText("Din bokade tid har blivit nekad av en administratör. Var vänlig kontakta en administratör.");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
                        binding.notificationDate.setText(sdf.format(currentTime) + " " + sdfclock.format(currentTime));
                    }
                    else {
                        binding.message.setText("Du kan nu boka en tid för din andra dos av vaccin.");
                        calendar.setTimeInMillis(dos1 + TWENTYTHREE_DAYS);
                        Date date = calendar.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
                        binding.notificationDate.setText(sdf.format(date) + " " + sdfclock.format(date));
                    }

                }
            }
        });
    }
}