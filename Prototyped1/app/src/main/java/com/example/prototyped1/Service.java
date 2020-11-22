package com.example.prototyped1;

public class Service {

    private String name;
    private String id;
    private double price;
    private String form;
    private String documents;

    public Service() {}
    public Service(String inName, String inID, double inPrice, String inForm, String inDocuments) {
        name = inName;
        id = inID;
        price = inPrice;
        form = inForm;
        documents = inDocuments;
    }

    public String getName() {
        return name;
    }

    public String priceToString() {
        return "$"+price;
    }

    public String getId() { return id; }

    public double getPrice() { return price; }

    public String getForm() { return form; }

    public String getDocuments() { return documents; }
}
