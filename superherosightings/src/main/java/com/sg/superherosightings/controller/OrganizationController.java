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
import com.sg.superherosightings.models.Organization;
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
public class OrganizationController {
 
    
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
    
    
    @GetMapping("orgs")
    public String displayOrganizations(Model model) {
        List<Organization> orgs = oDao.getAllOrganization();
        List<Location> locations = lDao.getAllLocation();
        model.addAttribute("locations", locations);
        model.addAttribute("orgs", orgs);
        return "orgs";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        oDao.deleteOrganizationById(id);
        
        return "redirect:/orgs";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        List<Location> locations = lDao.getAllLocation();
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = oDao.getOrganizationById(id);
        
        model.addAttribute("org", org);
        model.addAttribute("locations", locations);
        return "editOrganization";
    }
    
    @GetMapping("organizationDetail")
    public String sightingDetail(Integer id, Model model) {
        Organization org = oDao.getOrganizationById(id);
        List<Superhero> heroes = heroDao.getHeroesForOrganization(org).stream()
                                                .distinct()
                                                .collect(Collectors.toList());
        
        model.addAttribute("org", org);
        model.addAttribute("heroes", heroes);
        return "organizationDetail";
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        
        Location location = new Location();
        int locationId = Integer.valueOf(request.getParameter("location"));
        
        location = lDao.getLocationById(locationId);
        
        Organization org = new Organization();
        org.setName(name);
        org.setLocation(location);
        org.setPhone(request.getParameter("phone"));
        org.setDescription(description);
        
        oDao.addOrganization(org);
        
        return "redirect:/orgs";
    }
    
    @PostMapping("editOrganization")
    public String performEditOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = oDao.getOrganizationById(id);
        
        Location location = new Location();
        int locationId = Integer.valueOf(request.getParameter("location"));
        
        location = lDao.getLocationById(locationId);
        
        org.setName(request.getParameter("name"));
        org.setLocation(location);
        org.setPhone(request.getParameter("phone"));
        org.setDescription(request.getParameter("description"));
        
        oDao.updateOrganization(org);
        
        return "redirect:/orgs";
    }
    
}
