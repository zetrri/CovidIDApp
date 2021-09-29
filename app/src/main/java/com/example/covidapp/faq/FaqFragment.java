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

        TextBarn = binding.TextBarn;
        ButtonBarn = binding.ButtonBarn;


        ButtonVaccin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextVaccin.getText() != "Vaccination är det effektivaste sättet att undvika att bli allvarligt sjuk eller dö i covid-19. Vaccination hjälper också till att minska smittspridningen. Därför är det viktigt att så många som möjligt väljer att vaccineras."  ){
                    TextVaccin.setText("Vaccination är det effektivaste sättet att undvika att bli allvarligt sjuk eller dö i covid-19. Vaccination hjälper också till att minska smittspridningen. Därför är det viktigt att så många som möjligt väljer att vaccineras.");
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
                if(TextResor.getText() != "För resor inom EU krävs särskilda covidbevis. De är utformade på samma sätt inom hela EU och gäller för alla som är medborgare eller bosatta i ett EU-land. Flera länder utanför EU kräver ett intyg som visar att du inte har covid-19, för att du ska kunna resa in i landet. Det är inte säkert att den offentliga vården kan hjälpa till med intyg, och du kan behöva stå för kostnaden själv. "  ){
                    TextResor.setText("För resor inom EU krävs särskilda covidbevis. De är utformade på samma sätt inom hela EU och gäller för alla som är medborgare eller bosatta i ett EU-land. Flera länder utanför EU kräver ett intyg som visar att du inte har covid-19, för att du ska kunna resa in i landet. Det är inte säkert att den offentliga vården kan hjälpa till med intyg, och du kan behöva stå för kostnaden själv. ");
                }
                else{
                    //System.out.println("Test");
                    TextResor.setText(null);
                }
            }
        });

        ButtonRisken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextRisken.getText() != "Du behöver fortsätta att följa de allmänna råd och regler som finns för att minska spridningen av covid-19 och för att undvika att själv bli sjuk. Undvik trängsel, håll avstånd och tvätta händerna. Det är också viktigt att du stannar hemma om det behövs. Här kan du läsa mer."  ){
                    TextRisken.setText("Du behöver fortsätta att följa de allmänna råd och regler som finns för att minska spridningen av covid-19 och för att undvika att själv bli sjuk. Undvik trängsel, håll avstånd och tvätta händerna. Det är också viktigt att du stannar hemma om det behövs. Här kan du läsa mer.");
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
                if(TextCovid.getText() != "Covid-19 är en infektionssjukdom som orsakas av ett virus. Det är vanligt att bli sjuk som vid förkylning eller influensa. Du kan också få andra symtom. Lämna prov om du har symtom. Stanna hemma så länge som behövs. Oftast går covid-19 över av sig själv, men ibland behövs vård på sjukhus."  ){
                    TextCovid.setText("Covid-19 är en infektionssjukdom som orsakas av ett virus. Det är vanligt att bli sjuk som vid förkylning eller influensa. Du kan också få andra symtom. Lämna prov om du har symtom. Stanna hemma så länge som behövs. Oftast går covid-19 över av sig själv, men ibland behövs vård på sjukhus.");
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
                if(TextMunskydd.getText() != "Två saker är viktiga när du använder munskydd. Använd CE-märkta munskydd. Använd munskydden på rätt sätt."  ){
                    TextMunskydd.setText("Två saker är viktiga när du använder munskydd. Använd CE-märkta munskydd. Använd munskydden på rätt sätt.");
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
                if(TextGoraSjalv.getText() != "Här är råd till dig som har covid-19 men som kan vara hemma utan att behöva vård. Det är viktigt att du kontaktar vården om dina symtom förvärras."  ){
                    TextGoraSjalv.setText("Här är råd till dig som har covid-19 men som kan vara hemma utan att behöva vård. Det är viktigt att du kontaktar vården om dina symtom förvärras.");
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
                if(TextMaBattre.getText() != "Det är vanligt att behöva återhämta sig efter en infektion. Det gäller även covid-19. Hur du behöver återhämta dig beror på hur du mår. Här får du råd om vad du kan göra själv och när du kan behöva söka vård. Att fortsätta ha symtom efter covid-19 kan kallas att ha postcovid."){
                    TextMaBattre.setText("Det är vanligt att behöva återhämta sig efter en infektion. Det gäller även covid-19. Hur du behöver återhämta dig beror på hur du mår. Här får du råd om vad du kan göra själv och när du kan behöva söka vård. Att fortsätta ha symtom efter covid-19 kan kallas att ha postcovid.");
                }
                else{
                    //System.out.println("Test");
                    TextMaBattre.setText(null);
                }
            }
        });

        ButtonBarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(TextBarn.getText() != "Här kan du titta på en film om coronaviruset och om vad du kan göra för att inte sprida smitta eller bli smittad. Du får tips och råd om vad du kan göra om du är orolig och info om hur det går till att lämna prov för covid-19."){
                    TextBarn.setText("Här kan du titta på en film om coronaviruset och om vad du kan göra för att inte sprida smitta eller bli smittad. Du får tips och råd om vad du kan göra om du är orolig och info om hur det går till att lämna prov för covid-19.");
                }
                else{
                    //System.out.println("Test");
                    TextBarn.setText(null);
                }
            }
        });
    }

}