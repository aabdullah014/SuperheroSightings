/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author abdulrahman
 */
public interface SightingDao {
    
    //Sighting methods
    
    Sighting addSighting(Sighting sighting);
    Sighting getSightingById(int id);
    List<Sighting> getAllSighting();
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    List<Sighting> getAllSightingForDate(LocalDate date);
    
}
