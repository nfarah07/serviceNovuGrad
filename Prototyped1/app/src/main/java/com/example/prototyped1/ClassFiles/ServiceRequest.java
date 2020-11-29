package com.example.prototyped1.ClassFiles;

import com.example.prototyped1.ClassFiles.Service;

import java.io.Serializable;
import java.util.Map;

public class ServiceRequest implements Serializable {

    //class variables
    private String id;  //ID of individual request
    private Map<String,Object> formResponses;  //to hold request info from client
    private String associatedService; //Name of Service Request service
    private String associatedBranch;  //ID of Branch sending request to
    private String associatedClient;  //ID of client making request
    private String requestStatus = "pending"; //Current Status of the service request that was made {pending, approved, rejected}

    //constructor
    public ServiceRequest(String id, String associatedClient, String associatedBranch, String associatedService, Map<String,Object> formResponses){
        this.id = id;
        this.associatedClient = associatedClient;
        this.associatedBranch = associatedBranch;
        this.associatedService = associatedService;
        this.formResponses = formResponses;
    }

    public ServiceRequest(String id, String associatedClient, String associatedBranch, String associatedService, Map<String,Object> formResponses, String requestStatus){
        this.id = id;
        this.associatedClient = associatedClient;
        this.associatedBranch = associatedBranch;
        this.associatedService = associatedService;
        this.formResponses = formResponses;
        this.requestStatus = requestStatus;
    }

    public ServiceRequest() {}

    public String getId() {return id;}
    public String getAssociatedService() {return associatedService;}
    public String getAssociatedBranch() {return associatedBranch;}
    public String getAssociatedClient() {return associatedClient;}
    public String getRequestStatus() {return requestStatus;}
    public void setRequestStatus(String requestStatus) {this.requestStatus = requestStatus;}
}
