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
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Superhero;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class SightingController {
 
    
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
    
    
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sDao.getAllSighting();
        List<Location> locations = lDao.getAllLocation();
        List<Superhero> heroes = heroDao.getAllSuperhero();
        model.addAttribute("heroes", heroes);
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        return "sightings";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sDao.deleteSightingById(id);
        
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        List<Location> locations = lDao.getAllLocation();
        List<Superhero> heroes = heroDao.getAllSuperhero();
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sDao.getSightingById(id);
        
        model.addAttribute("heroes", heroes);
        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locations);
        return "editSighting";
    }
    
    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sDao.getSightingById(id);
        List<Sighting> sightings = sDao.getAllSightingForDate(sighting.getTime()).stream()
                                                .distinct()
                                                .collect(Collectors.toList());
        
        model.addAttribute("sighting", sighting);
        model.addAttribute("sightings", sightings);
        return "sightingDetail";
    }
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String time = request.getParameter("time");
        String power = request.getParameter("hero");
        String description = request.getParameter("location");
        
        Sighting sighting = new Sighting();
        
        sighting.setHero(heroDao.getSuperheroById(Integer.parseInt(request.getParameter("hero"))));
        sighting.setLocation(lDao.getLocationById(Integer.parseInt(request.getParameter("location"))));
        
        String str = request.getParameter("time");
        sighting.setTime(LocalDate.parse(str));
        
        sDao.addSighting(sighting);
        
        return "redirect:/sightings";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sDao.getSightingById(id);
        
        String time = request.getParameter("time");
        String power = request.getParameter("hero");
        String description = request.getParameter("location");
        
        
        sighting.setHero(heroDao.getSuperheroById(Integer.parseInt(request.getParameter("hero"))));
        sighting.setLocation(lDao.getLocationById(Integer.parseInt(request.getParameter("location"))));
        
        String str = request.getParameter("time");
        sighting.setTime(LocalDate.parse(str));
        
        sDao.updateSighting(sighting);
        
        return "redirect:/sightings";
    }
    
}
