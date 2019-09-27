package com.example.myapplication.Models;

public class Appoinments {


    private String doctorEmail;
    private String patientEmail;
    private String slotDay;
    private String slotStartTime;
    private String slotEndTime;

    public Appoinments(){

    }

    public Appoinments(String doctorEmail, String patientEmail, String slotDay, String slotStartTime, String slotEndTime){

        setDoctorEmail(doctorEmail);
        setDoctorEmail(patientEmail);
        setSlotDay(slotDay);
        setSlotStartTime(slotStartTime);
        setSlotEndTime(slotEndTime);
    }


    public String getDoctorEmail() {
        return doctorEmail;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getSlotDay() {
        return slotDay;
    }

    public void setSlotDay(String slotDay) {
        this.slotDay = slotDay;
    }

    public String getSlotStartTime() {
        return slotStartTime;
    }

    public void setSlotStartTime(String slotStartTime) {
        this.slotStartTime = slotStartTime;
    }

    public String getSlotEndTime() {
        return slotEndTime;
    }

    public void setSlotEndTime(String slotEndTime) {
        this.slotEndTime = slotEndTime;
    }



}
