/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosightings.models;

import java.util.Objects;

/**
 *
 * @author abdulrahman
 */
public class Power {
    
    private int powerId;
    private String name;
    private String description;

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Power other = (Power) obj;
        
        if (!Objects.equals(this.powerId, other.powerId)) {
            return false;
        }
        return Objects.equals(this.description, other.description);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.powerId);
        hash = 13 * hash + Objects.hashCode(this.description);
        
        return hash;
    }

    @Override
    public String toString() {
        return "Power{"  + "powerId=" + powerId + ", name=" + name + ", description=" + description;
    }
    
}
