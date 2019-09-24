package com.example.myapplication;

public class SystemUsers {

    private int Id;
    private String Username;
    private String Email;
    private String Designation;



    public SystemUsers(int id, String username, String email, String designation) {
        this.Id = id;
        this.Username = username;
        this.Email = email;
        this.Designation = designation;
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

    public String getDesignation() {
        return Designation;
    }


}
