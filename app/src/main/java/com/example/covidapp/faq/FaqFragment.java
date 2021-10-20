package com.example.covidapp.faq;

import android.graphics.drawable.Drawable;
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

public class FaqFragment extends Fragment {
    TextView TextVaccin, TextResor, TextRisken, TextCovid, TextMunskydd, TextGoraSjalv, TextMaBattre, TextBarn;
    Button ButtonVaccin, ButtonResor, ButtonRisken, ButtonCovid, ButtonMunskydd, ButtonGoraSjalv, ButtonMaBattre, ButtonBarn ;
    private FragmentFaqBinding binding;

    public FaqFragment() {
        // Required empty public constructor
    }

    public static FaqFragment newInstance(String param1, String param2) {
        FaqFragment fragment = new FaqFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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


        Drawable up_arrow = getResources().getDrawable(R.drawable.up_arrow);
        Drawable down_arrow = getResources().getDrawable(R.drawable.down_arrow);

        ButtonVaccin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextVaccin.getText() == ""  ){
                    TextVaccin.setText(getString(R.string.faq_vaccin_swe));
                    ButtonVaccin.setCompoundDrawablesWithIntrinsicBounds(null, null, up_arrow, null);
                }
                else{
                    ButtonVaccin.setCompoundDrawablesWithIntrinsicBounds(null, null, down_arrow, null);
                    TextVaccin.setText(null);
                }
            }
        });

        ButtonResor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextResor.getText() == ""  ){
                    TextResor.setText(getString(R.string.faq_resor_swe));
                    ButtonResor.setCompoundDrawablesWithIntrinsicBounds(null, null, up_arrow, null);
                }
                else{
                    ButtonResor.setCompoundDrawablesWithIntrinsicBounds(null, null, down_arrow, null);
                    TextResor.setText(null);
                }
            }
        });

        ButtonRisken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextRisken.getText() == ""  ){
                    TextRisken.setText(getString(R.string.faq_risken_swe));
                    ButtonRisken.setCompoundDrawablesWithIntrinsicBounds(null, null, up_arrow, null);
                }
                else{
                    ButtonRisken.setCompoundDrawablesWithIntrinsicBounds(null, null, down_arrow, null);
                    TextRisken.setText(null);
                }
            }
        });

        ButtonCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextCovid.getText() == ""  ){
                    TextCovid.setText(R.string.faq_covid_swe);
                    ButtonCovid.setCompoundDrawablesWithIntrinsicBounds(null, null, up_arrow, null);
                }
                else{
                    ButtonCovid.setCompoundDrawablesWithIntrinsicBounds(null, null, down_arrow, null);
                    TextCovid.setText(null);
                }
            }
        });

        ButtonMunskydd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextMunskydd.getText() == ""  ){
                    TextMunskydd.setText(getString(R.string.faq_munskydd_swe));
                    ButtonMunskydd.setCompoundDrawablesWithIntrinsicBounds(null, null, up_arrow, null);
                }
                else{
                    ButtonMunskydd.setCompoundDrawablesWithIntrinsicBounds(null, null, down_arrow, null);
                    TextMunskydd.setText(null);
                }
            }
        });

        ButtonGoraSjalv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextGoraSjalv.getText() == ""  ){
                     TextGoraSjalv.setText(R.string.faq_gorasjalv_swe);
                     ButtonGoraSjalv.setCompoundDrawablesWithIntrinsicBounds(null, null, up_arrow, null);
                }
                else{
                    ButtonGoraSjalv.setCompoundDrawablesWithIntrinsicBounds(null, null, down_arrow, null);
                    TextGoraSjalv.setText(null);
                }
            }
        });

        ButtonMaBattre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextMaBattre.getText() == ""  ){
                    TextMaBattre.setText(R.string.faq_mabattre_swe);
                    ButtonMaBattre.setCompoundDrawablesWithIntrinsicBounds(null, null, up_arrow, null);
                }
                else{
                    ButtonMaBattre.setCompoundDrawablesWithIntrinsicBounds(null, null, down_arrow, null);
                    TextMaBattre.setText(null);
                }
            }
        });


    }

}