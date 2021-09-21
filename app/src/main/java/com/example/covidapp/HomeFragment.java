package com.example.covidapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidapp.Booking.BookingMainActivity;
import com.example.covidapp.Dashboard.MainDashboard;
import com.example.covidapp.HealthAdmin.QuestionnaireResponseActivity;
import com.example.covidapp.LogIn.MainLogInActivity;
import com.example.covidapp.MyPage.MainMyPage;
import com.example.covidapp.Passport.PassportMainActivity;
import com.example.covidapp.UserReg.MainUserRegActivity;
import com.example.covidapp.faq.Faq_main;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        buttonDashboard = findViewById(R.id.buttonDashboard);
//        buttonfaq = findViewById(R.id.buttonFaq);
//        buttonUserLogin = findViewById(R.id.buttonUserLogin);
//        buttonMainscreen2= findViewById(R.id.buttonMainscreen2);
//        buttonMyPage = findViewById(R.id.buttonMyPage);
//        buttonMyAppointments = findViewById(R.id.buttonMyAppointments);
//        buttonpassport = findViewById(R.id.buttonPassport);
//        buttonuserreg = findViewById(R.id.buttonUserReg);
//        buttonquestresp = findViewById(R.id.buttonQuestRes);
//
//        buttonDashboard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), MainDashboard.class);
//                startActivity(intent);
//
//            }
//        });
//        buttonfaq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), Faq_main.class);
//                startActivity(intent);
//            }
//        });
//        buttonUserLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), MainLogInActivity.class);
//                startActivity(intent);
//            }
//        });
//        buttonMainscreen2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), MainActivity2.class);
//                startActivity(intent);
//
//            }
//
//        });
//        buttonMyPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), MainMyPage.class);
//                startActivity(intent);
//            }
//        });
//        buttonMyAppointments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), BookingMainActivity.class);
//                startActivity(intent);
//            }
//        });
//        buttonpassport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), PassportMainActivity.class);
//                startActivity(intent);
//            }
//        });
//        buttonuserreg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), MainUserRegActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        buttonquestresp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getBaseContext(), QuestionnaireResponseActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
}