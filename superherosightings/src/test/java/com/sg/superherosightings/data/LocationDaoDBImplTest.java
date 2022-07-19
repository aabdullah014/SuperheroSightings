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
import org.junit.BeforeClass;
import static org.junit.Assert.*;
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
public class LocationDaoDBImplTest {
    
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
    
    
    
    public LocationDaoDBImplTest() {
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
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Des");
        location.setAddress("Test Address");
        location.setCoordinates("Test coordinates");
        location = lDao.addLocation(location);
        
        Location fromDao = lDao.getLocationById(location.getLocationId());
        
        assertEquals("Should be equal", location, fromDao);
    }

    @org.junit.jupiter.api.Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Des");
        location.setAddress("Test Address");
        location.setCoordinates("Test coordinates");
        location = lDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test Name");
        location2.setDescription("Test Des");
        location2.setAddress("Test Address");
        location2.setCoordinates("Test coordinates");
        location2 = lDao.addLocation(location2);
        
        List<Location> locations = lDao.getAllLocation();
        
        assertEquals("size should be 2", 2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    @org.junit.jupiter.api.Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Des");
        location.setAddress("Test Address");
        location.setCoordinates("Test coordinates");
        location = lDao.addLocation(location);
        
        Location fromDao = lDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
        
        location.setName("New Test Name");
        lDao.updateLocation(location);
        
        assertNotEquals("Should be different", location, fromDao);
        
        fromDao = lDao.getLocationById(location.getLocationId());
        
        assertEquals("Should be the same", location, fromDao);
    }

    @org.junit.jupiter.api.Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Des");
        location.setAddress("Test Address");
        location.setCoordinates("Test coordinates");
        location = lDao.addLocation(location);
        
//        Student student = new Student();
//        student.setName("Test Student First");
//        student.setDescription("Test Student Last");
//        student = studentDao.addStudent(student);
//        List<Student> students = new ArrayList<>();
//        students.add(student);
//        
//        Course course = new Course();
//        course.setName("Test Course");
//        course.setLocation(location);
//        course.setStudents(students);
//        course = courseDao.addCourse(course);
        
        Location fromDao = lDao.getLocationById(location.getLocationId());
        assertEquals("Should be the same", location, fromDao);
        
        lDao.deleteLocationById(location.getLocationId());
        
        fromDao = lDao.getLocationById(location.getLocationId());
        assertNull("Should be null", fromDao);
    }
    
}
