package com.example.myapplication.Models;

public class TimeSlots {


    private String doctorEmail;
    private String slotDay;
    private String slotStartTime;
    private String slotEndTime;

    public TimeSlots(){

    }

    public TimeSlots(String doctorEmail, String slotDay, String slotStartTime, String slotEndTime){

        setDoctorEmail(doctorEmail);
        setSlotDay(slotDay);
        setSlotStartTime(slotStartTime);
        setSlotEndTime(slotEndTime);
    }


    public String getDoctorEmail() {
        return doctorEmail;
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
