package com.example.app;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Mikveh {
    private String Religious_Council;
    private String City;
    private String neighborhood;
    private String Mikve_Address;
    private String Opening_Hours_Summer;
    private String Phone;
    private String Owner_ID;
    private String Opening_Hours_Winter;
    private String Opening_Hours_Holiday_Eve_Shabat_Eve;
    private String Opening_Hours_Saturday_Night_Good_Day;
    private String Accessibility;
    private String Schedule_Appointment;
    private String Notes;
    private String zip;
    private Geocoder geocoder;
    private String totalRate;

    public Mikveh() {
        Religious_Council = "";
        City = "";
        neighborhood = "";
        Mikve_Address = "";
        Phone = "";
        Opening_Hours_Summer = "";
        Opening_Hours_Winter = "";
        Opening_Hours_Holiday_Eve_Shabat_Eve = "";
        Opening_Hours_Saturday_Night_Good_Day = "";
        Accessibility = "";
        Schedule_Appointment = "";
        Notes = "";
        Owner_ID = "";
        totalRate = "";
    }

    public Mikveh(String religious_Council, String city, String neighborhood, String mikve_Address, String phone, String opening_Hours_Summer, String opening_Hours_Winter, String opening_Hours_Holiday_Eve_Shabat_Eve, String opening_Hours_Saturday_Night_Good_Day, String accessibility, String schedule_Appointment, String notes, String owner_ID, String total_rate) {
        Religious_Council = religious_Council;
        City = city;
        this.neighborhood = neighborhood;
        Mikve_Address = mikve_Address;
        Phone = phone;
        Opening_Hours_Summer = opening_Hours_Summer;
        Opening_Hours_Winter = opening_Hours_Winter;
        Opening_Hours_Holiday_Eve_Shabat_Eve = opening_Hours_Holiday_Eve_Shabat_Eve;
        Opening_Hours_Saturday_Night_Good_Day = opening_Hours_Saturday_Night_Good_Day;
        Accessibility = accessibility;
        Schedule_Appointment = schedule_Appointment;
        Notes = notes;
        Owner_ID = owner_ID;
        totalRate = total_rate;
    }

    public String getReligious_Council() {
        return Religious_Council;
    }

    public void setReligious_Council(String religious_Council) {
        Religious_Council = religious_Council;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getOwner_ID() { return Owner_ID; }

    public void setOwner_ID(String owner_ID) { Owner_ID = owner_ID;}

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getMikve_Address() {
        return Mikve_Address;
    }

    public void setMikve_Address(String mikve_Address) {
        Mikve_Address = mikve_Address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getOpening_Hours_Summer() {
        return Opening_Hours_Summer;
    }

    public void setOpening_Hours_Summer(String opening_Hours_Summer) {
        Opening_Hours_Summer = opening_Hours_Summer;
    }

    public String getOpening_Hours_Winter() {
        return Opening_Hours_Winter;
    }

    public void setOpening_Hours_Winter(String opening_Hours_Winter) {
        Opening_Hours_Winter = opening_Hours_Winter;
    }

    public String getOpening_Hours_Holiday_Eve_Shabat_Eve() {
        return Opening_Hours_Holiday_Eve_Shabat_Eve;
    }

    public void setOpening_Hours_Holiday_Eve_Shabat_Eve(String opening_Hours_Holiday_Eve_Shabat_Eve) {
        Opening_Hours_Holiday_Eve_Shabat_Eve = opening_Hours_Holiday_Eve_Shabat_Eve;
    }

    public String getOpening_Hours_Saturday_Night_Good_Day() {
        return Opening_Hours_Saturday_Night_Good_Day;
    }

    public void setOpening_Hours_Saturday_Night_Good_Day(String opening_Hours_Saturday_Night_Good_Day) {
        Opening_Hours_Saturday_Night_Good_Day = opening_Hours_Saturday_Night_Good_Day;
    }

    public String getAccessibility() {
        return Accessibility;
    }

    public void setAccessibility(String accessibility) {
        Accessibility = accessibility;
    }

    public String getSchedule_Appointment() {
        return Schedule_Appointment;
    }

    public void setSchedule_Appointment(String schedule_Appointment) {
        Schedule_Appointment = schedule_Appointment;
    }

    public String getNotes() {
        return Notes;
    }

    public String getTotalRate() { return totalRate; }
    public void setTotalRate(String total_Rate) { totalRate = total_Rate; }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public void geoUtils(Context ctx) {
        geocoder = new Geocoder(ctx, Locale.getDefault());
    }

    public String getCurrentZipCode(double lat, double lng) throws IOException {
        List<Address> addressesAtLocation = geocoder.getFromLocation(lat, lng, 1);
        return addressesAtLocation.get(0).getPostalCode();
    }
}
