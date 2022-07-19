/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosightings.controller;
import com.sg.superherosightings.data.SightingDao;
import com.sg.superherosightings.models.Sighting;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author abdulrahman
 */

@Controller
public class IndexController {
    
    @Autowired
    SightingDao sDao;
    
    
    @GetMapping("/")
    public String displayLocations(Model model) {
        List<Sighting> allSightings = sDao.getAllSighting();
        List<Sighting> sightings = allSightings.stream()
                                        .sorted((s1, s2)->s2.getTime()
                                                .compareTo(s1.getTime())).limit(10).collect(Collectors.toList());
        
        model.addAttribute("sightings", sightings);
        return "index";
    }
    
}
