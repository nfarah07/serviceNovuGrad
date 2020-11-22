package com.example.prototyped1;

public class Service {

    private String name;
    private String id;
    private double price;
    // private Map < infoName , infoType >

    public Service() {}
    public Service(String inName, String inID, double inPrice) {
        name = inName;
        id = inID;
        price = inPrice;
    }

    public String getName() {
        return name;
    }

    public String priceToString() {
        return "$"+price;
    }

    public String getId() { return id; }

    public double getPrice() { return price; }


}
