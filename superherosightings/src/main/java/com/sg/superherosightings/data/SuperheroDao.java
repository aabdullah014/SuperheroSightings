/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Superhero;
import java.util.List;

/**
 *
 * @author abdulrahman
 */
public interface SuperheroDao {
    
    //Superhero methods
    
    Superhero addSuperhero(Superhero hero);
    Superhero getSuperheroById(int id);
    List<Superhero> getAllSuperhero();
    void updateSuperhero(Superhero hero);
    void deleteSuperheroById(int id);
    List<Superhero> getAllSuperheroForLocation(Location location);
    
    List<Superhero> getHeroesForOrganization(Organization org);
    
}
