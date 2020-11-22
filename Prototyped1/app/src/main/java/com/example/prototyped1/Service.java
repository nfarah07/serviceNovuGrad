package com.example.prototyped1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Service {

    //Store the data types once so there's no confusion or mistakes later. We'll add more as needed
    public static final List<String> SERVICE_INFO_DATA_TYPES = new ArrayList<>(
            Arrays.asList("String", "Double", "Integer", "Boolean", "Map", "Image")
    );

    private String name;
    private String id;
    private double price;
    private String form;
    private String documents;
    private Map<String,String> informationHolder; //This will hold key/val pairs for service request information, eg : "Full Name" : "String", "Age" : "int"

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

    public void addElement(Map.Entry<String, String> newEntry){
        this.informationHolder.put(newEntry.getKey(), newEntry.getValue());
    }

    public void removeElement(String key){
        this.informationHolder.remove(key);
    }

    public void clearElements(){
        this.informationHolder.clear();
    }
}
