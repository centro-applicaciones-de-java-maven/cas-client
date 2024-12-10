package org.guanzon.cas.client;

public class Clients {
    
    
    private ClientTypes type;
    
    public ClientTypes getType() {
        return type;
    }

    public Clients(ClientTypes type) {
        this.type = type;
    }
    
    public void displayInfo() {
        System.out.println("Type: " + type);
    }
}
