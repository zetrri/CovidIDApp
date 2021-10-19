package com.example.covidapp.MyPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.Booking.Booking;
import com.example.covidapp.HealthAdmin.AvailableTimesListUserClass;
import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentMyPageBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageFragment extends Fragment {

    private FragmentMyPageBinding binding;

    Booking[] Bookings = new Booking[2];
    MyPageFragment this_obj = this;

    private FirebaseAuth firebaseAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
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
        binding = FragmentMyPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Removes keyboard if up
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        //Removes keyboard if up

        //Bookings[0] = new Booking("8/2-2021", "15:30", "Gripen", "Karlstad", "Värmland", "Modena", "birch pollen", "", "Doctor spooky", "199809291111", "Karl Johan");
        //Bookings[1] = new Booking("14/7-2021", "11:00", "Nolhaga", "Alingsås", "Västra Götaland", "Modena", "Potatoe", "Potato","", "193312031234", "Peter niklas");


        //Top menu listeners
        LinearLayout passport = binding.passport;
        LinearLayout bookappointment = binding.bookappointment;
        LinearLayout notifications = binding.notifications;
        passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getBaseContext(), PassportMainActivity.class);
//                startActivity(intent);
                Navigation.findNavController(view).navigate(R.id.action_nav_my_page_to_nav_passport);
            }
        });
        bookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity().getBaseContext(), BookingMainActivity.class);
//                startActivity(intent);
                Navigation.findNavController(view).navigate(R.id.action_nav_my_page_to_nav_booking);
            }
        });

        //Logout button
        firebaseAuth = FirebaseAuth.getInstance();
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_my_page_to_nav_notifications);
            }
        });

        LinearLayout container = binding.containerbookings;

        //Getting bookings from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("BookedTimes");
        ArrayList<AvailableTimesListUserClass> availableTimesListUserClasses =new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
                //if user not logged in
                if ( firebaseAuth1.getCurrentUser() == null){
                    Navigation.findNavController(view).navigate(R.id.nav_login);
                    return;
                }
                //If logged in
                else{
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                        //getting all appointments booked by the user
                        AvailableTimesListUserClass availableTimesListUserClass = dataSnapshot1.getValue(AvailableTimesListUserClass.class);
                        if (availableTimesListUserClass.getBookedBy().equals(firebaseAuth1.getUid())){
                            Log.d("FoundOne",availableTimesListUserClass.getId());
                            availableTimesListUserClasses.add(availableTimesListUserClass);
                        }
                    }
                    //Calendar and formatting helpers
                    Calendar date = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    SimpleDateFormat sdfclock = new SimpleDateFormat("kk:mm");
                    for(int i = 0; i < availableTimesListUserClasses.size(); i++){

                        //Making a card for each appointment
                        date.setTimeInMillis(availableTimesListUserClasses.get(i).getTimestamp());
                        Date date1 = date.getTime();

                        CardView new_card = new CardView(getActivity());
                        new_card.setId(i+1);
                        LinearLayout linear_layout1 = new LinearLayout(getActivity());
                        linear_layout1.setOrientation(LinearLayout.VERTICAL);
                        TextView date_time = new TextView(getActivity());

                        //date_time.setText(String.valueOf(date1.getDay())+"/"+String.valueOf(date1.getMonth())+"-"+date1.getYear());
                        date_time.setText(sdf.format(date1) +" "+sdfclock.format(date1));
                        date_time.setTextSize(20);
                        date_time.setBackgroundColor(0xFF6200EE);
                        date_time.setTextColor(Color.WHITE);
                        linear_layout1.addView(date_time);

                        TextView clinic_text = new TextView(getActivity());
                        clinic_text.setTextSize(15);
                        clinic_text.setText( (String) ("Mottagning: " + availableTimesListUserClasses.get(i).getClinic() + " " + availableTimesListUserClasses.get(i).getCity()));
                        linear_layout1.addView(clinic_text);

                        TextView vaccin_text = new TextView(getActivity());
                        vaccin_text.setTextSize(15);
                        vaccin_text.setText( (String) ("Vaccin: " + availableTimesListUserClasses.get(i).getVaccine())); //ska vara vaccine
                        linear_layout1.addView(vaccin_text);

                        TextView test_text = new TextView(getActivity());
                        test_text.setTextSize(15);
                        test_text.setText(availableTimesListUserClasses.get(i).getId());
                        test_text.setVisibility(TextView.GONE);
                        linear_layout1.addView(test_text);

                        TextView test_text2 = new TextView(getActivity());
                        test_text2.setTextSize(15);
                        test_text2.setText(String.valueOf(i));
                        test_text2.setVisibility(TextView.GONE);
                        linear_layout1.addView(test_text2);


                        LinearLayout linear_layout2 = new LinearLayout(getActivity());
                        linear_layout2.setOrientation(LinearLayout.HORIZONTAL);
                        linear_layout2.setPadding(0,0,0,16);
                        linear_layout2.setGravity(Gravity.RIGHT);

                        Button buttonAvboka = new Button(getActivity());
                        buttonAvboka.setText("Avboka");
                        linear_layout2.addView(buttonAvboka);
                        buttonAvboka.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Avboka")
                                        .setMessage("Datum: "+date_time.getText()+ "\n" + clinic_text.getText() + "\n\nÄr du säker på att du vill avboka denna tid?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                new_card.removeAllViews();
                                                gettimeback(availableTimesListUserClasses.get(Integer.parseInt(test_text2.getText().toString())));
                                                deleteCard(test_text.getText().toString());

                                                // TODO remove information from database
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .show();

                            }
                        });
                        Button buttonOmboka = new Button(getActivity());
                        buttonOmboka.setText("Omboka");
                        linear_layout2.addView(buttonOmboka);
                        buttonOmboka.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Omboka")
                                        .setMessage("Datum: "+date_time.getText()+ "\n" + clinic_text.getText() + "\n\nÄr du säker på att du vill omboka denna tid?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                new_card.removeAllViews();
                                                gettimeback(availableTimesListUserClasses.get(Integer.parseInt(test_text2.getText().toString())));
                                                deleteCard(test_text.getText().toString());
                                                View view2 = getView();
                                                Navigation.findNavController(view2).navigate(R.id.action_nav_my_page_to_nav_booking);

                                                // TODO remove information from database
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null)
                                        .show();
                            }
                        });

                        linear_layout1.addView(linear_layout2);
                        new_card.addView(linear_layout1);
                        container.addView(new_card);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
    private void deleteCard(String id)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("BookedTimes");
        ref.child(id).removeValue();
    }
    private void gettimeback(AvailableTimesListUserClass item)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://covidid-14222-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref = database.getReference("AvailableTimes");
        item.setAvailable(true);
        item.setBookedBy("");
        item.setAllergies("");
        item.setComments("");
        item.setVaccine("");
        item.setMedication("");
        item.setApproved(false);
        ref.child(item.getId()).setValue(item);
    }

    private void createCard(){}
}