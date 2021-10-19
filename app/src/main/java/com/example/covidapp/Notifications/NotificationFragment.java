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


        //Getting bookings from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("BookedTimes");
        ArrayList<AvailableTimesListUserClass> availableTimesListUserClasses =new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
                //if user not logged in
                if ( firebaseAuth1.getCurrentUser() == null){
                    Navigation.findNavController(view).navigate(R.id.nav_login);
                    return;
                }
                //If logged in
                else {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        //getting all appointments booked by the user
                        AvailableTimesListUserClass availableTimesListUserClass = dataSnapshot1.getValue(AvailableTimesListUserClass.class);
                        if (availableTimesListUserClass.getBookedBy().equals(firebaseAuth1.getUid())) {
                            Log.d("FoundOne", availableTimesListUserClass.getId());
                            availableTimesListUserClasses.add(availableTimesListUserClass);
                            Calendar calendar = Calendar.getInstance();
//                            System.out.println(availableTimesListUserClass.getTimestamp() + 1209600000);
//                            System.out.println(calendar.getTimeInMillis());
                            long twoWeeksLater = availableTimesListUserClass.getTimestamp() + 1209600000;
                            if(twoWeeksLater <= calendar.getTimeInMillis()) {
                                binding.notificationCard.setVisibility(View.VISIBLE);
                                binding.notificationTextview.setVisibility(View.INVISIBLE);

//                                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//                                SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
                                calendar.setTimeInMillis(twoWeeksLater);
                                Date date = calendar.getTime();

//                                CardView new_card = new CardView(getActivity());
//                                new_card.setId(i+1);
//                                LinearLayout linear_layout1 = new LinearLayout(getActivity());
//                                linear_layout1.setOrientation(LinearLayout.VERTICAL);
//                                TextView date_time = new TextView(getActivity());

                                //date_time.setText(String.valueOf(date1.getDay())+"/"+String.valueOf(date1.getMonth())+"-"+date1.getYear());
//                                date_time.setText(sdf.format(date1) +" "+sdfclock.format(date1));
//                                date_time.setTextSize(20);
//                                date_time.setBackgroundColor(0xFF6200EE);
//                                date_time.setTextColor(Color.WHITE);
//                                linear_layout1.addView(date_time);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                                SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
                                binding.notificationDate.setText(sdf.format(date) +" "+sdfclock.format(date));
                            }
//                            else {
//                                binding.notificationCard.setVisibility(View.INVISIBLE);
//                                binding.notificationTextview.setVisibility(View.VISIBLE);
//                            }
                        }
                    }
//
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }
}


//        if(true) {
//            binding.notificationCard.setVisibility(View.INVISIBLE);
//            binding.notificationTextview.setVisibility(View.VISIBLE);
//        }
//        else {
//            binding.notificationCard.setVisibility(View.VISIBLE);
//            binding.notificationTextview.setVisibility(View.INVISIBLE);
//        }
//
//
//        //Getting bookings from firebase
//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
//        DatabaseReference ref = database.getReference("BookedTimes");
//        ArrayList<AvailableTimesListUserClass> availableTimesListUserClasses =new ArrayList<>();
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
//                //if user not logged in
//                if ( firebaseAuth1.getCurrentUser() == null){
//                    Navigation.findNavController(view).navigate(R.id.nav_login);
//                    return;
//                }
//                //If logged in
//                else {
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                        //getting all appointments booked by the user
//                        AvailableTimesListUserClass availableTimesListUserClass = dataSnapshot1.getValue(AvailableTimesListUserClass.class);
//                        if (availableTimesListUserClass.getBookedBy().equals(firebaseAuth1.getUid())) {
//                            Log.d("FoundOne", availableTimesListUserClass.getId());
//                            availableTimesListUserClasses.add(availableTimesListUserClass);
//                            Calendar calendar = Calendar.getInstance();
//                            if(availableTimesListUserClass.getTimestamp() + 1209600000 >= calendar.getTimeInMillis()) {
//                                binding.notificationCard.setVisibility(View.INVISIBLE);
//                                binding.notificationTextview.setVisibility(View.VISIBLE);
//                            }
//                            else {
//                                binding.notificationCard.setVisibility(View.INVISIBLE);
//                                binding.notificationTextview.setVisibility(View.VISIBLE);
//                            }
//                        }
//                        }
////
//                    }
//                }
//
//                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
//                    DatabaseReference reference = database.getReference("BookedTimes");
//                    reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                        @Override
//                        public void onComplete(@NotNull Task<DataSnapshot> task) {
//                            FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
//                            //if user not logged in
//                            if ( firebaseAuth1.getCurrentUser() == null){
//                                Navigation.findNavController(view).navigate(R.id.nav_login);
//                                return;
//                            }
//                            //If logged in
//                            else {
//                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                                    //getting all appointments booked by the user
//                                    AvailableTimesListUserClass availableTimesListUserClass = dataSnapshot1.getValue(AvailableTimesListUserClass.class);
//                                    if (availableTimesListUserClass.getBookedBy().equals(firebaseAuth1.getUid())) {
//
//                                    }
////
//                                }
//                            }
//                        }
//                    });
