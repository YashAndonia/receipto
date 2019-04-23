package com.companyname.yash.usersidereceipto;

import java.util.HashMap;
import java.util.Map;

public class user {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;



    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    String emailAddress;



    public user(){

    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }



    public double total;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String password;
    Map <String,Boolean>receipts=new HashMap<String, Boolean>();

    //public String allReceipts;


    public String keyReader(){
        String allkeys="";


        for(Object x: this.receipts.keySet()){
            allkeys+=(String)x;
            allkeys+="\n";
        }
        return allkeys;
    }



    String settingNull="";

    public user(String emaillAdd, String name){
        this.emailAddress=emaillAdd;
        this.name=name;
        //this.password=password;
        this.receipts.put("nullTx",true);
        this.total=0.0;

        this.settingNull="";
    }
/*

    public user(String emaillAdd, String name, String password){
        this.emailAddress=emaillAdd;
        this.name=name;
        this.password=password;
        //this.receipts.put("nullTx",true);
        this.allReceipts="";
        this.total=0.0;
    }*/
}
