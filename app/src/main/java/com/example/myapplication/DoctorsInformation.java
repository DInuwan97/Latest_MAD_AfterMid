package com.example.myapplication;

public class DoctorsInformation {

    public static final String DATA_RECIEVE = "data_recieve";
    private int Id;
    private String Username;
    private String Email;

    private String Mobile;
    private String Nic;
    private String Hospital;
    private String Specialization;
    private byte[] image;

    public DoctorsInformation(){

    }

    public DoctorsInformation(int id, String username, String email, String mobile, String nic, String hospital, String specialization) {
        Id = id;
        Username = username;
        Email = email;
        Mobile = mobile;
        Nic = nic;
        Hospital = hospital;
    }

    public int getId() {
        return Id;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }


    public String getMobile() {
        return Mobile;
    }

    public String getNic() {
        return Nic;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public String getHospital() {
        return Hospital;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
