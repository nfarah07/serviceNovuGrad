package com.example.servicenovigrad;

public class Administrator extends Employee {
    public Administrator(String u, String e, String p) {
        super(u, e, p, "Admin");
    }

    public void createService(double cost) {
        Service toAdd = new Service(cost);
        // Then add this service to the allServices, the way we do it depends on which data organization type we use
    }
}
