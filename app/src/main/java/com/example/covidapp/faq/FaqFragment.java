package com.example.covidapp.faq;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.covidapp.R;
import com.example.covidapp.databinding.FragmentBookingBinding;
import com.example.covidapp.databinding.FragmentFaqBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FaqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaqFragment extends Fragment {

    TextView TextVaccin, TextResor, TextRisken, TextCovid, TextMunskydd, TextGoraSjalv, TextMaBattre, TextBarn;
    Button ButtonVaccin, ButtonResor, ButtonRisken, ButtonCovid, ButtonMunskydd, ButtonGoraSjalv, ButtonMaBattre, ButtonBarn ;

    private FragmentFaqBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FaqFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FaqFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FaqFragment newInstance(String param1, String param2) {
        FaqFragment fragment = new FaqFragment();
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

        binding = FragmentFaqBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextVaccin = binding.TextVaccin;
        ButtonVaccin = binding.ButtonVaccin;

        TextResor = binding.TextResor;
        ButtonResor = binding.ButtonResor;

        TextRisken = binding.TextRisken;
        ButtonRisken = binding.ButtonRisken;

        TextCovid = binding.TextCovid;
        ButtonCovid = binding.ButtonCovid;

        TextMunskydd = binding.TextMunskydd;
        ButtonMunskydd = binding.ButtonMunskydd;

        TextGoraSjalv = binding.TextGoraSjalv;
        ButtonGoraSjalv = binding.ButtonGoraSjalv;

        TextMaBattre = binding.TextMaBattre;
        ButtonMaBattre = binding.ButtonMaBattre;




        ButtonVaccin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextVaccin.getText() == ""  ){
                    TextVaccin.setText(getString(R.string.faq_vaccin_swe));
                }
                else{
                    //System.out.println("Test");
                    TextVaccin.setText(null);
                }
            }
        });

        ButtonResor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextResor.getText() == ""  ){
                    TextResor.setText(getString(R.string.faq_resor_swe));
                }
                else{
                    TextResor.setText(null);
                }
            }
        });

        ButtonRisken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextRisken.getText() == ""  ){
                    TextRisken.setText(getString(R.string.faq_risken_swe));
                }
                else{
                    //System.out.println("Test");
                    TextRisken.setText(null);
                }
            }
        });

        ButtonCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextCovid.getText() == ""  ){
                    TextCovid.setText(R.string.faq_covid_swe);
                }
                else{
                    //System.out.println("Test");
                    TextCovid.setText(null);
                }
            }
        });

        ButtonMunskydd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextMunskydd.getText() == ""  ){
                    TextMunskydd.setText(getString(R.string.faq_munskydd_swe));
                }
                else{
                    //System.out.println("Test");
                    TextMunskydd.setText(null);
                }
            }
        });

        ButtonGoraSjalv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextGoraSjalv.getText() == ""  ){
                     TextGoraSjalv.setText(R.string.faq_gorasjalv_swe);
                }
                else{
                    //System.out.println("Test");
                    TextGoraSjalv.setText(null);
                }
            }
        });

        ButtonMaBattre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextMaBattre.getText() == ""  ){
                    TextMaBattre.setText(R.string.faq_mabattre_swe);
                }
                else{
                    //System.out.println("Test");
                    TextMaBattre.setText(null);
                }
            }
        });


    }

}