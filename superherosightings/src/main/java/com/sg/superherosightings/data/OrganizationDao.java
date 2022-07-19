/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Superhero;
import java.util.List;

/**
 *
 * @author abdulrahman
 */
public interface OrganizationDao {
    
    //Organization methods
    
    Organization addOrganization(Organization org);
    Organization getOrganizationById(int id);
    List<Organization> getAllOrganization();
    void updateOrganization(Organization org);
    void deleteOrganizationById(int id);
    List<Superhero> getMembers(Organization org);
    
    List<Organization> getOrgsForSuperhero(Superhero hero);
    
}
