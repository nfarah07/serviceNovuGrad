package com.example.prototyped1;

import com.example.prototyped1.ClassFiles.Service;

import java.io.Serializable;
import java.util.Map;

public class ServiceRequest implements Serializable {

    //class variables
    private String id;  //ID of individual request
    private Map<String,Object> formResponses;  //to hold request info from client
    private String associatedService; //ID of Service Request service
    private String associatedBranch;  //ID of Branch sending request to
    private String associatedClient;  //ID of client making request

    //constructor
    public ServiceRequest(String id, String associatedClient, String associatedBranch, String associatedService, Map<String,Object> formResponses){
        this.id = id;
        this.associatedClient = associatedClient;
        this.associatedBranch = associatedBranch;
        this.associatedService = associatedService;
        this.formResponses = formResponses;
    }

    public String getId() {return id;}
    public String getAssociatedService() {return associatedService;}
    public String getAssociatedBranch() {return associatedBranch;}
    public String getAssociatedClient() {return associatedClient;}
}
