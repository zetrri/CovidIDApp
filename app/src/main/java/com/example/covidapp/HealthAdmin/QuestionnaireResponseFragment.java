package com.example.covidapp.HealthAdmin;

import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentQuestionnaireResponseBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionnaireResponseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionnaireResponseFragment extends Fragment {

    private FragmentQuestionnaireResponseBinding binding;
    private  booking [] bookings = new booking[4]; //TODO Ta bort och implementera med databas

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuestionnaireResponseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionnaireResponseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionnaireResponseFragment newInstance(String param1, String param2) {
        QuestionnaireResponseFragment fragment = new QuestionnaireResponseFragment();
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
        binding = FragmentQuestionnaireResponseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO Ta bort och implementera med databas
        bookings [0] = new booking("0", "0", "0", "0", "Modena", "Doctor spooky", "birch pollen", "199809291111", "Karl Johan", "Kokain");
        bookings [1] = new booking("0", "0", "0", "0", "Modena", "Potatoe", "Potato", "193312031234", "Peter niklas", "Paracetamol");
        bookings [2] = new booking("0", "0", "0", "0", "Modena", "Im scared", "Spooky stuff", "193312039999", "Martin Lund", "Diaree medesin");
        bookings [3] = new booking("0", "0", "0", "0", "Modena", "eeeeeeeeee", "Kaffe", "193312039999", "REEEEEEEE", "Sand");

        LinearLayout container = binding.QuestionnaireContainer;

        //Dynamisk inladdning av kort
        for(int i = 0; i < bookings.length; i++){
            CardView new_card = new CardView(getActivity());
            new_card.setRadius(4);

            LinearLayout linear_layout1 = new LinearLayout(getActivity());
            linear_layout1.setOrientation(LinearLayout.VERTICAL);

            //Skapar personnr och namn TextView
            TextView namn_pers_text = new TextView(getActivity());
            namn_pers_text.setText( (String) (bookings[i].namn + " \n" + bookings[i].personNr));
            namn_pers_text.setTextSize(20);
            namn_pers_text.setBackgroundColor(0xFF6200EE);
            namn_pers_text.setTextColor(Color.WHITE);
            namn_pers_text.setPadding(8,8,8,8);
            linear_layout1.addView(namn_pers_text);

            //Skapar Allergi TextView
            TextView allergi_text = new TextView(getActivity());
            allergi_text.setTextSize(15);
            allergi_text.setText( (String) ("Allergi:\n" + bookings[i].allergy));
            allergi_text.setPadding(0,0,0,16);
            linear_layout1.addView(allergi_text);

            //Skapar Mediciner TextView
            TextView meds_text = new TextView(getActivity());
            meds_text.setTextSize(15);
            meds_text.setText( (String) ("Mediciner:\n" + bookings[i].meds));
            meds_text.setPadding(0,0,0,16);
            linear_layout1.addView(meds_text);

            //Skapar Meddelande TextView
            TextView msg_text = new TextView(getActivity());
            msg_text.setTextSize(15);
            msg_text.setText( (String) ("Meddelande:\n" + bookings[i].message));
            linear_layout1.addView(msg_text);

            //Skapar En horisontel linear kontainer där knapparna är i
            LinearLayout linear_layout2 = new LinearLayout(getActivity());
            linear_layout2.setOrientation(LinearLayout.HORIZONTAL);
            linear_layout2.setPadding(0,0,0,16);
            linear_layout2.setGravity(Gravity.RIGHT);

            Button buttonConfirm = new Button(getActivity());
            buttonConfirm.setText("Godkänd");
            linear_layout2.addView(buttonConfirm);

            //Lyssna på alla Godkänd knappar och tar bort det kortet
            buttonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    container.removeView(new_card);
                }
            });


            Button buttonAdditional = new Button(getActivity());
            buttonAdditional.setText("Ytterligare Verifiering");
            linear_layout2.addView(buttonAdditional);

            linear_layout1.addView(linear_layout2);

            new_card.addView(linear_layout1);
            container.addView(new_card);
        }

    }


}