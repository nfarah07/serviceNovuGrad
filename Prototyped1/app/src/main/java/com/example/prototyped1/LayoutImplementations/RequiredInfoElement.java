package com.example.prototyped1.LayoutImplementations;


// Information required for a particular Service
// Ex. phone number, address, type of licence, health card number, ...
// store info name (ex. passport) and type (ex. image)

public class RequiredInfoElement {

    String infoName;
    String infoType;

    public RequiredInfoElement(String info, String type) {
        infoName = info;
        infoType = type;
    }

    public String getInfoName() {return infoName;}

    public String getInfoType() {return infoType;}

}
