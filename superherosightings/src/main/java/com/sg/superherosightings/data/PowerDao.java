/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Power;
import java.util.List;

/**
 *
 * @author abdulrahman
 */
public interface PowerDao {
    
    //Power methods
    
    Power addPower(Power power);
    Power getPowerById(int id);
    List<Power> getAllPower();
    void updatePower(Power power);
    void deletePowerById(int id);
    
}
