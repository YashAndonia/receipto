package com.companyname.yash.shopsidereceipto;

import java.util.HashMap;
import java.util.Map;

public class receipt {
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }


    double cost;
    String customerId;
    String items;


    //CONSTRUCTORSL:
    public receipt(){

    }

    String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public receipt(double cost, String customerId, String items, String company){
        this.cost=cost;
        this.customerId=customerId;
        this.items=items;
        this.company=company;
        this.settingNull="";
        this.idVal="";
    }

    String settingNull="";
    String idVal="";

    public Map<String,Object> toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("cost",cost);
        result.put("customerId",customerId);
        result.put("items",items);
        result.put("company",company);


        return result;
    }

}
