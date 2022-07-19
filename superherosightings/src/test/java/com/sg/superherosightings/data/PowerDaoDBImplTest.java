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
public class PowerDaoDBImplTest {
    
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
    
    public PowerDaoDBImplTest() {
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
    public void testAddAndGetPower() {
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Des");
        power = pDao.addPower(power);
        
        Power fromDao = pDao.getPowerById(power.getPowerId());
        
        assertEquals("Should be equal", power, fromDao);
    }

    @org.junit.jupiter.api.Test
    public void testGetAllPowers() {
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Des");
        power = pDao.addPower(power);
        
        Power power2 = new Power();
        power2.setName("Test2 Name");
        power2.setDescription("Test2 Des");
        power2 = pDao.addPower(power2);
        
        List<Power> powers = pDao.getAllPower();
        
        assertEquals("size should be 2", 2, powers.size());
        assertTrue(powers.contains(power));
        assertTrue(powers.contains(power2));
    }

    @org.junit.jupiter.api.Test
    public void testUpdatePower() {
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Des");
        power = pDao.addPower(power);
        
        Power fromDao = pDao.getPowerById(power.getPowerId());
        assertEquals("Should be same", power, fromDao);
        
        power.setName("New Test Name");
        pDao.updatePower(power);
        
        assertNotEquals("Should be different", fromDao.getName(), power.getName());
        
        fromDao = pDao.getPowerById(power.getPowerId());
        
        assertEquals("Should be the same", power, fromDao);
    }

    @org.junit.jupiter.api.Test
    public void testDeletePowerById() {
        Power power = new Power();
        power.setName("Test Name");
        power.setDescription("Test Des");
        power = pDao.addPower(power);
        
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
        
        Power fromDao = pDao.getPowerById(power.getPowerId());
        assertEquals("Should be the same", power, fromDao);
        
        pDao.deletePowerById(power.getPowerId());
        
        fromDao = pDao.getPowerById(power.getPowerId());
        assertNull("Should be null", fromDao);
    }
    
}
