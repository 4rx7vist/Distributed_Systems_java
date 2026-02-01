package com.distribuidas.model;

public class UserSession {
    private static UserSession instance;
    private String username;
    private String role; // "MASTER", "SLAVE"
    private String location; // "QUITO", "GUAYAQUIL", "ALL"

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void cleanUserSession() {
        username = null;
        role = null;
        location = null;
    }

    public void setUser(String username, String role, String location) {
        this.username = username;
        this.role = role;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getLocation() {
        return location;
    }

    public boolean isMaster() {
        return "MASTER".equalsIgnoreCase(role);
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
