package com.example.covidapp.HealthAdmin;

public class VaccineClass {
    String Pfizer;
    String Moderna;
    String NovaVax;
    String AstraZeneca;

    public VaccineClass() {
    }

    public VaccineClass(String pfizer, String moderna, String novavax, String astrazeneca) {
        Pfizer = pfizer;
        Moderna = moderna;
        NovaVax = novavax;
        AstraZeneca = astrazeneca;
    }
    public String getPfizer() {
        return Pfizer;
    }

    public void setPfizer(String pfizer) {
        Pfizer = pfizer;
    }

    public String getModerna() {
        return Moderna;
    }

    public void setModerna(String moderna) {
        Moderna = moderna;
    }

    public String getNovaVax() {
        return NovaVax;
    }

    public void setNovaVax(String novavax) {
        NovaVax = novavax;
    }

    public String getAstraZeneca() {
        return AstraZeneca;
    }

    public void setAstraZeneca(String astrazeneca) {
        AstraZeneca = astrazeneca;
    }
}

