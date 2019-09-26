package com.example.myapplication.Database;

public class DeliverClass {


    private int id ;
    private String userName ;
    private String address ;
    private String email;
    private int status ;
    private int phonenumber ;
    private String itemNames ;
    private String[] itemNamesArray ;
    private String itemsAmount ;
    private float[] itemsAmountArray ;
    private float totalprice;
    private String dateTime;
    private String AcceptDateTime;
    private String DeliveredDateTime;
    private String acceptedby;

    public String getAcceptedby() {
        return acceptedby;
    }

    public void setAcceptedby(String acceptedby) {
        this.acceptedby = acceptedby;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }



    public float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getItemNames() {
        return itemNames;
    }

    public void setItemNames(String itemNames) {
        this.itemNames = itemNames;
    }

    public String[] getItemNamesArray() {
        return itemNamesArray;
    }

    public void setItemNamesArray(String[] itemNamesArray) {
        this.itemNamesArray = itemNamesArray;
    }

    public String getItemsAmount() {
        return itemsAmount;
    }

    public void setItemsAmount(String itemsAmount) {
        this.itemsAmount = itemsAmount;
    }

    public float[] getItemsAmountArray() {
        return itemsAmountArray;
    }

    public void setItemsAmountArray(float[] itemsAmountArray) {
        this.itemsAmountArray = itemsAmountArray;
    }

    public String getAcceptDateTime() {
        return AcceptDateTime;
    }

    public void setAcceptDateTime(String acceptDateTime) {
        AcceptDateTime = acceptDateTime;
    }

    public String getDeliveredDateTime() {
        return DeliveredDateTime;
    }

    public void setDeliveredDateTime(String deliveredDateTime) {
        DeliveredDateTime = deliveredDateTime;
    }
}
