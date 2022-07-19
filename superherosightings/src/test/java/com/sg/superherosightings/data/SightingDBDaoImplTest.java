/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Power;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Superhero;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

/**
 *
 * @author abdulrahman
 */

@SpringBootTest
public class SightingDBDaoImplTest {
    
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
    
    
    
    public SightingDBDaoImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        List<Sighting> sightings = sDao.getAllSighting();
        for(Sighting sighting : sightings) {
            sDao.deleteSightingById(sighting.getSightingId());
        }
        
        List<Superhero> heros = heroDao.getAllSuperhero();
        for(Superhero hero : heros) {
            heroDao.deleteSuperheroById(hero.getHeroId());
        }
        
        List<Organization> orgs = oDao.getAllOrganization();
        for(Organization org : orgs) {
            oDao.deleteOrganizationById(org.getOrgId());
        }
        
        List<Location> locations = lDao.getAllLocation();
        for(Location l : locations) {
            lDao.deleteLocationById(l.getLocationId());
        }
        
        List<Power> powers = pDao.getAllPower();
        for(Power power : powers) {
            pDao.deletePowerById(power.getPowerId());
        }
    }
    
    @After
    public void tearDown() {
    }

    @org.junit.jupiter.api.Test
    public void testAddAndGetSighting() {
        Location location = new Location();
        location.setAddress("test");
        location.setCoordinates("Test");
        location.setDescription("test");
        location.setName("Test");
        lDao.addLocation(location);
        
        Power power = new Power();
        power.setName("power");
        power.setDescription("test");
        pDao.addPower(power);
        
        Organization org = new Organization();
        org.setName("test");
        org.setDescription("TEst");
        org.setLocation(location);
        org.setPhone("test");
        oDao.addOrganization(org);
        
        List<Organization> orgs = oDao.getAllOrganization();
        
        Superhero hero = new Superhero();
        hero.setName("test");
        hero.setDescription("Test");
        hero.setPower(power);
        hero.setOrgs(orgs);
        heroDao.addSuperhero(hero);
        
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setTime(LocalDate.parse("2000-01-01"));
        sighting = sDao.addSighting(sighting);
        
        Sighting fromDao = sDao.getSightingById(sighting.getSightingId());
        
        assertEquals("Should be equal", sighting.getHero(), fromDao.getHero());
        assertEquals("Should be equal", sighting.getLocation(), fromDao.getLocation());
    }

    @org.junit.jupiter.api.Test
    public void testGetAllSightings() {
        Location location = new Location();
        location.setAddress("test");
        location.setCoordinates("Test");
        location.setDescription("test");
        location.setName("Test");
        lDao.addLocation(location);
        
        Power power = new Power();
        power.setName("power");
        power.setDescription("test");
        pDao.addPower(power);
        
        Organization org = new Organization();
        org.setName("test");
        org.setDescription("TEst");
        org.setLocation(location);
        org.setPhone("test");
        oDao.addOrganization(org);
        
        List<Organization> orgs = oDao.getAllOrganization();
        
        Superhero hero = new Superhero();
        hero.setName("test");
        hero.setDescription("Test");
        hero.setPower(power);
        hero.setOrgs(orgs);
        heroDao.addSuperhero(hero);
        
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setTime(LocalDate.parse("2000-01-01"));
        sighting = sDao.addSighting(sighting);
        
        Location location2 = new Location();
        location2.setAddress("test");
        location2.setCoordinates("Test");
        location2.setDescription("test");
        location2.setName("Test");
        lDao.addLocation(location2);
        
        Power power2 = new Power();
        power2.setName("power2");
        power2.setDescription("test");
        pDao.addPower(power2);
        
        Organization org2 = new Organization();
        org2.setName("test");
        org2.setDescription("TEst");
        org2.setLocation(location2);
        org2.setPhone("test");
        oDao.addOrganization(org2);
        
        List<Organization> org2s = oDao.getAllOrganization();
        
        Superhero hero2 = new Superhero();
        hero2.setName("test");
        hero2.setDescription("Test");
        hero2.setPower(power2);
        hero2.setOrgs(org2s);
        heroDao.addSuperhero(hero2);
        
        Sighting sighting2 = new Sighting();
        sighting2.setHero(hero2);
        sighting2.setLocation(location2);
        sighting2.setTime(LocalDate.parse("2000-01-01"));
        sighting2 = sDao.addSighting(sighting2);
        
        List<Sighting> sightings = sDao.getAllSighting();
        
        assertEquals("size should be 2", 2, sightings.size());
    }

    @org.junit.jupiter.api.Test
    public void testUpdateSighting() {
        Location location = new Location();
        location.setAddress("test");
        location.setCoordinates("Test");
        location.setDescription("test");
        location.setName("Test");
        lDao.addLocation(location);
        
        Power power = new Power();
        power.setName("power");
        power.setDescription("test");
        pDao.addPower(power);
        
        Organization org = new Organization();
        org.setName("test");
        org.setDescription("TEst");
        org.setLocation(location);
        org.setPhone("test");
        oDao.addOrganization(org);
        
        List<Organization> orgs = oDao.getAllOrganization();
        
        Superhero hero = new Superhero();
        hero.setName("test");
        hero.setDescription("Test");
        hero.setPower(power);
        hero.setOrgs(orgs);
        heroDao.addSuperhero(hero);
        
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setTime(LocalDate.parse("2000-01-01"));
        sighting = sDao.addSighting(sighting);
        
        Sighting fromDao = sDao.getSightingById(sighting.getSightingId());
        assertEquals("Should be equal", sighting.getHero(), fromDao.getHero());
        assertEquals("Should be equal", sighting.getLocation(), fromDao.getLocation());
        
        Location location2 = new Location();
        location2.setAddress("test2");
        location2.setCoordinates("Test2");
        location2.setDescription("test2");
        location2.setName("Test2");
        lDao.addLocation(location2);
        
        Power power2 = new Power();
        power2.setName("power");
        power2.setDescription("test2");
        pDao.addPower(power2);
        
        Organization org2 = new Organization();
        org2.setName("test2");
        org2.setDescription("TEst2");
        org2.setLocation(location);
        org2.setPhone("test2");
        oDao.addOrganization(org2);
        
        List<Organization> orgs2 = oDao.getAllOrganization();
        
        Superhero hero2 = new Superhero();
        hero2.setName("test2");
        hero2.setDescription("Test2");
        hero2.setPower(power);
        hero2.setOrgs(orgs);
        heroDao.addSuperhero(hero2);
        
        sighting.setHero(hero2);
        sDao.updateSighting(sighting);
        
        assertNotEquals("Should be different", sighting, fromDao);
        
        fromDao = sDao.getSightingById(sighting.getSightingId());
        
        assertEquals("Should be equal", sighting.getHero(), fromDao.getHero());
        assertEquals("Should be equal", sighting.getLocation(), fromDao.getLocation());
    }

    @org.junit.jupiter.api.Test
    public void testDeleteSightingById() {
        Location location = new Location();
        location.setAddress("test");
        location.setCoordinates("Test");
        location.setDescription("test");
        location.setName("Test");
        lDao.addLocation(location);
        
        Power power = new Power();
        power.setName("power");
        power.setDescription("test");
        pDao.addPower(power);
        
        Organization org = new Organization();
        org.setName("test");
        org.setDescription("TEst");
        org.setLocation(location);
        org.setPhone("test");
        oDao.addOrganization(org);
        
        List<Organization> orgs = oDao.getAllOrganization();
        
        Superhero hero = new Superhero();
        hero.setName("test");
        hero.setDescription("Test");
        hero.setPower(power);
        hero.setOrgs(orgs);
        heroDao.addSuperhero(hero);
        
        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setTime(LocalDate.parse("2000-01-01"));
        sighting = sDao.addSighting(sighting);
        
        Sighting fromDao = sDao.getSightingById(sighting.getSightingId());
        assertEquals("Should be equal", sighting.getHero(), fromDao.getHero());
        assertEquals("Should be equal", sighting.getLocation(), fromDao.getLocation());
        
        sDao.deleteSightingById(sighting.getSightingId());
        
        fromDao = sDao.getSightingById(sighting.getSightingId());
        assertNull("Should be null", fromDao);
    }
    
    
}
