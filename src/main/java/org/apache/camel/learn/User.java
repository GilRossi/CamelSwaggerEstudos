package org.apache.camel.learn;

import java.io.Serializable;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    @JacksonXmlProperty(isAttribute = true, localName = "user")
    private String username;

    private String firstName;
    private String lastName;
    private String city;

    // ===========================
    // Construtores
    // ===========================
    public User() {
        // construtor vazio necessário para JSON/XML
    }

    public User(int id, String username, String firstName, String lastName, String city) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }

    // ===========================
    // Getters e Setters
    // ===========================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // ===========================
    // toString
    // ===========================
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}