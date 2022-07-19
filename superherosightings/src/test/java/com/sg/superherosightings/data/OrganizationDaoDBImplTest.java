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
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
public class OrganizationDaoDBImplTest {
    
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
    
    public OrganizationDaoDBImplTest() {
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
    public void testAddAndGetOrg() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Des");
        location.setAddress("Test Address");
        location.setCoordinates("Test coordinates");
        location = lDao.addLocation(location);
        
        Organization org = new Organization();
        org.setName("test");
        org.setDescription("test des");
        org.setPhone("test num");
        org.setLocation(location);
        oDao.addOrganization(org);
        
        Organization fromDao = oDao.getOrganizationById(org.getOrgId());
        
        assertEquals("Should be equal", org, fromDao);
    }

    @org.junit.jupiter.api.Test
    public void testGetAllOrg() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Des");
        location.setAddress("Test Address");
        location.setCoordinates("Test coordinates");
        location = lDao.addLocation(location);
        
        Organization org = new Organization();
        org.setName("test");
        org.setDescription("test des");
        org.setPhone("test num");
        org.setLocation(location);
        oDao.addOrganization(org);
        
        Location location2 = new Location();
        location2.setName("Test Name");
        location2.setDescription("Test Des");
        location2.setAddress("Test Address");
        location2.setCoordinates("Test coordinates");
        location2 = lDao.addLocation(location2);
        
        Organization org2 = new Organization();
        org2.setName("test2");
        org2.setDescription("test2 des");
        org2.setPhone("test2 num");
        org2.setLocation(location2);
        oDao.addOrganization(org2);
        
        List<Organization> orgs = oDao.getAllOrganization();
        
        assertEquals("size should be 2", 2, orgs.size());
    }

    @org.junit.jupiter.api.Test
    public void testUpdateOrg() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Des");
        location.setAddress("Test Address");
        location.setCoordinates("Test coordinates");
        location = lDao.addLocation(location);
        
        Organization org = new Organization();
        org.setName("test");
        org.setDescription("test des");
        org.setPhone("test num");
        org.setLocation(location);
        oDao.addOrganization(org);
        
        Organization fromDao = oDao.getOrganizationById(org.getOrgId());
        assertEquals(org, fromDao);
        
        org.setName("New Test Name");
        oDao.updateOrganization(org);
        
        assertNotEquals("Should be different", org, fromDao);
        
        fromDao = oDao.getOrganizationById(org.getOrgId());
        
        assertEquals("Should be the same", org, fromDao);
    }

    @org.junit.jupiter.api.Test
    public void testDeleteOrgById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Des");
        location.setAddress("Test Address");
        location.setCoordinates("Test coordinates");
        location = lDao.addLocation(location);
        
        Organization org = new Organization();
        org.setName("test");
        org.setDescription("test des");
        org.setPhone("test num");
        org.setLocation(location);
        oDao.addOrganization(org);
        
        Organization fromDao = oDao.getOrganizationById(org.getOrgId());
        assertEquals("Should be the same", org, fromDao);
        
        oDao.deleteOrganizationById(org.getOrgId());
        
        fromDao = oDao.getOrganizationById(org.getOrgId());
        assertNull("Should be null", fromDao);
    }
    
}
