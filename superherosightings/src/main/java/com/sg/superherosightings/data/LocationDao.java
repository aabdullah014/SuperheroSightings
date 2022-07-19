/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Superhero;
import java.util.List;

/**
 *
 * @author abdulrahman
 */
public interface LocationDao {
    
    //Location methods
    
    Location addLocation(Location location);
    Location getLocationById(int id);
    List<Location> getAllLocation();
    void updateLocation(Location location);
    void deleteLocationById(int id);
    List<Location> getLocationsForSuperhero(Superhero hero);
    
}
