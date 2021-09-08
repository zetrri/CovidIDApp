package com.example.covidapp.faq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.covidapp.R;

public class Faq_main extends AppCompatActivity {
    TextView TextVaccin, TextResor, TextRisken, TextCovid, TextMunskydd, TextGoraSjalv, TextMaBattre, TextBarn;
    Button ButtonVaccin, ButtonResor, ButtonRisken, ButtonCovid, ButtonMunskydd, ButtonGoraSjalv, ButtonMaBattre, ButtonBarn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_main);
        TextVaccin = findViewById(R.id.TextVaccin);
        ButtonVaccin = findViewById(R.id.ButtonVaccin);

        TextResor = findViewById(R.id.TextResor);
        ButtonResor = findViewById(R.id.ButtonResor);

        TextRisken = findViewById(R.id.TextRisken);
        ButtonRisken = findViewById(R.id.ButtonRisken);

        TextCovid = findViewById(R.id.TextCovid);
        ButtonCovid = findViewById(R.id.ButtonCovid);

        TextMunskydd = findViewById(R.id.TextMunskydd);
        ButtonMunskydd = findViewById(R.id.ButtonMunskydd);

        TextGoraSjalv = findViewById(R.id.TextGöraSjälv);
        ButtonGoraSjalv = findViewById(R.id.ButtonGöraSjälv);

        TextMaBattre = findViewById(R.id.TextMåBättre);
        ButtonMaBattre = findViewById(R.id.ButtonMåBättre);

        TextBarn = findViewById(R.id.TextBarn);
        ButtonBarn = findViewById(R.id.ButtonBarn);


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
