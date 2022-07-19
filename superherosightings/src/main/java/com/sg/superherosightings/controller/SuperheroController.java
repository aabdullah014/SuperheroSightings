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
import com.sg.superherosightings.models.Power;
import com.sg.superherosightings.models.Superhero;
import java.util.ArrayList;
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
public class SuperheroController {
 
    
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
    
    
    @GetMapping("heroes")
    public String displaySuperheros(Model model) {
        List<Superhero> heroes = heroDao.getAllSuperhero();
        List<Organization> orgs = oDao.getAllOrganization();
        List<Power> powers = pDao.getAllPower();
        model.addAttribute("heroes", heroes);
        model.addAttribute("orgs", orgs);
        model.addAttribute("powers", powers);
        return "heroes";
    }
    
    @GetMapping("deleteHero")
    public String deleteSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDao.deleteSuperheroById(id);
        
        return "redirect:/heroes";
    }
    
    @GetMapping("editHero")
    public String editHeroes(HttpServletRequest request, Model model) {
        List<Organization> orgs = oDao.getAllOrganization();
        List<Power> powers = pDao.getAllPower();
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero hero = heroDao.getSuperheroById(id);
        
        model.addAttribute("hero", hero);
        model.addAttribute("orgs", orgs);
        model.addAttribute("powers", powers);
        return "editHeroes";
    }
    
    @GetMapping("heroDetail")
    public String heroDetail(Integer id, Model model) {
        Superhero hero = heroDao.getSuperheroById(id);
        List<Location> locations = lDao.getLocationsForSuperhero(hero).stream()
                                                .distinct()
                                                .collect(Collectors.toList());
        
        model.addAttribute("hero", hero);
        model.addAttribute("locations", locations);
        return "heroDetail";
    }
    
    @PostMapping("addSuperhero")
    public String addSuperhero(HttpServletRequest request) {
        String name = request.getParameter("name");
        String power = request.getParameter("power");
        String description = request.getParameter("description");
        String[] orgs = request.getParameterValues("orgId");
        
        Superhero hero = new Superhero();
        
        List<Organization> addedOrgs = new ArrayList<>();
        for(String org: orgs){
            addedOrgs.add(oDao.getOrganizationById(Integer.parseInt(org)));
        }
        
        hero.setOrgs(addedOrgs);
        
        hero.setName(name);
        hero.setPower(pDao.getPowerById(Integer.valueOf(request.getParameter("power"))));
        hero.setDescription(request.getParameter("description"));
        
        heroDao.addSuperhero(hero);
        
        return "redirect:/heroes";
    }
    
    @PostMapping("editHero")
    public String performEditSuperhero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero hero = heroDao.getSuperheroById(id);
        
        String name = request.getParameter("name");
        String power = request.getParameter("power");
        String description = request.getParameter("description");
        String[] orgs = request.getParameterValues("orgId");
        
        List<Organization> addedOrgs = new ArrayList<>();
        for(String org: orgs){
            addedOrgs.add(oDao.getOrganizationById(Integer.parseInt(org)));
        }
        
        hero.setName(request.getParameter("name"));
        hero.setPower(pDao.getPowerById(Integer.valueOf(request.getParameter("power"))));
        hero.setOrgs(addedOrgs);
        hero.setDescription(request.getParameter("description"));
        
        heroDao.updateSuperhero(hero);
        
        return "redirect:/heroes";
    }
    
}
