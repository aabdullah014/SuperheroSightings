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
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
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
public class SuperheroDaoDBImplTest {
    
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
    
    public SuperheroDaoDBImplTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        List<Location> locations = lDao.getAllLocation();
        for(Location l : locations) {
            lDao.deleteLocationById(l.getLocationId());
        }
        
        List<Organization> orgs = oDao.getAllOrganization();
        for(Organization org : orgs) {
            oDao.deleteOrganizationById(org.getOrgId());
        }
        
        List<Power> powers = pDao.getAllPower();
        for(Power power : powers) {
            pDao.deletePowerById(power.getPowerId());
        }
        
        List<Sighting> sightings = sDao.getAllSighting();
        for(Sighting sighting : sightings) {
            sDao.deleteSightingById(sighting.getSightingId());
        }
        
        List<Superhero> heros = heroDao.getAllSuperhero();
        for(Superhero hero : heros) {
            heroDao.deleteSuperheroById(hero.getHeroId());
        }
        
    }
    
    @After
    public void tearDown() {
    }

    @org.junit.jupiter.api.Test
    public void testAddAndGetHero() {
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
        
        Superhero fromDao = heroDao.getSuperheroById(hero.getHeroId());
        
        assertEquals("Should be equal", hero, fromDao);
    }
    
    @org.junit.jupiter.api.Test
    public void testGetAllHeroes() {
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
        
        List<Superhero> heros = heroDao.getAllSuperhero();
        
        assertEquals("size should be 2", 2, heros.size());
    }

    @org.junit.jupiter.api.Test
    public void testUpdateHero() {
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
        
        Superhero fromDao = heroDao.getSuperheroById(hero.getHeroId());
        assertEquals("Should be equal", hero, fromDao);
        
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
        
        hero.setName("Update");
        heroDao.updateSuperhero(hero);
        
        assertNotEquals("Should be different", hero, fromDao);
        
        fromDao = heroDao.getSuperheroById(hero.getHeroId());
        
        assertEquals("Should be equal", hero, fromDao);
    }

    @org.junit.jupiter.api.Test
    public void testDeleteHeroById() {
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
        
        Superhero fromDao = heroDao.getSuperheroById(hero.getHeroId());
        
        assertEquals("Should be equal", hero, fromDao);
        
        heroDao.deleteSuperheroById(hero.getHeroId());
        
        fromDao = heroDao.getSuperheroById(hero.getHeroId());
        assertNull("Should be null", fromDao);
    }
    
    
}
