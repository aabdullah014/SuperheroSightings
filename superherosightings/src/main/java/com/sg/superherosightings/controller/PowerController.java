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
import com.sg.superherosightings.models.Power;
import java.util.List;
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
public class PowerController {
 
    
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
    
    
    @GetMapping("powers")
    public String displayPowers(Model model) {
        List<Power> powers = pDao.getAllPower();
        model.addAttribute("powers", powers);
        return "powers";
    }
    
    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        pDao.deletePowerById(id);
        
        return "redirect:/powers";
    }
    
    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = pDao.getPowerById(id);
        
        model.addAttribute("power", power);
        return "editPower";
    }
    
    @PostMapping("addPower")
    public String addPower(HttpServletRequest request) {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String description = request.getParameter("description");
        
        Power power = new Power();
        power.setName(name);
        power.setDescription(description);
        
        pDao.addPower(power);
        
        return "redirect:/powers";
    }
    
    @PostMapping("editPower")
    public String performEditPower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = pDao.getPowerById(id);
        
        power.setName(request.getParameter("name"));
        power.setDescription(request.getParameter("description"));
        
        pDao.updatePower(power);
        
        return "redirect:/powers";
    }
    
}
