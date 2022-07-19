/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.data.LocationDao;
import com.sg.superherosightings.data.OrganizationDao;
import com.sg.superherosightings.data.PowerDao;
import com.sg.superherosightings.data.SightingDao;
import com.sg.superherosightings.data.SuperheroDao;
import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Superhero;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author abdulrahman
 */

@Controller
public class LocationController {
 
    
    @Autowired
    LocationDao lDao;
    
    @Autowired
    OrganizationDao oDao;
    
    @Autowired
    PowerDao pDao;
    
    @Autowired
    SightingDao sDao;
    
    @Autowired
    SuperheroDao heroDao;
    
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = lDao.getAllLocation();
        model.addAttribute("locations", locations);
        return "locations";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        lDao.deleteLocationById(id);
        
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = lDao.getLocationById(id);
        
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) {
        Location location = lDao.getLocationById(id);
        
        List<Superhero> heroes = heroDao.getAllSuperheroForLocation(location).stream()
                                            .distinct()
                                            .collect(Collectors.toList());
        model.addAttribute("heroes", heroes);
        model.addAttribute("location", location);
        return "locationDetail";
    }
    
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        String coordinates = request.getParameter("coordinates");
        
        Location location = new Location();
        location.setName(name);
        location.setAddress(address);
        location.setDescription(description);
        location.setCoordinates(coordinates);
        
        lDao.addLocation(location);
        
        return "redirect:/locations";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = lDao.getLocationById(id);
        
        location.setName(request.getParameter("name"));
        location.setAddress(request.getParameter("address"));
        location.setDescription(request.getParameter("description"));
        location.setCoordinates(request.getParameter("coordinates"));
        
        lDao.updateLocation(location);
        
        return "redirect:/locations";
    }
    
}
