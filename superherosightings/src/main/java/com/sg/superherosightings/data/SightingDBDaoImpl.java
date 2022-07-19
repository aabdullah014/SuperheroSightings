/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.data.LocationDaoDBImpl.LocationMapper;
import com.sg.superherosightings.data.SuperheroDaoDBImpl.HeroMapper;
import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Sighting;
import com.sg.superherosightings.models.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author abdulrahman
 */

@Repository
public class SightingDBDaoImpl implements SightingDao {
    
    @Autowired
    private final JdbcTemplate jdbc;

    public SightingDBDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    @Autowired
    SuperheroDao hDao;
    
    @Autowired
    LocationDao lDao;
    

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        
        final String sql = "INSERT INTO sighting(heroId, locationId, time) VALUES(?, ?, ?);";

        jdbc.update(sql,
            sighting.getHero().getHeroId(),
            sighting.getLocation().getLocationId(),
            sighting.getTime().toString());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(newId);

        return sighting;
        
    }

    @Override
    public Sighting getSightingById(int id) {
        
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocation(setLocationForSighting(sighting));
            sighting.setHero(setSuperheroForSighting(sighting));
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSighting() {
        final String sql = "SELECT * FROM sighting;";
        List<Sighting> sightings = jdbc.query(sql, new SightingMapper());
        
        for(Sighting sighting: sightings){
            sighting.setLocation(setLocationForSighting(sighting));
            sighting.setHero(setSuperheroForSighting(sighting));
        }
        
        return sightings;
    }
    
    @Override
    public List<Sighting> getAllSightingForDate(LocalDate date){
        final String sql = "SELECT * FROM sighting WHERE time = ?;";
        
        List<Sighting> sightings = jdbc.query(sql, new SightingMapper(), date);
        
        for(Sighting sighting: sightings){
            sighting.setLocation(setLocationForSighting(sighting));
            sighting.setHero(setSuperheroForSighting(sighting));
        }
        
        return sightings;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String sql = "UPDATE sighting SET heroId = ?, locationId = ?, time = ? WHERE sightingId = ?";
        jdbc.update(sql,
                sighting.getHero().getHeroId(),
                sighting.getLocation().getLocationId(),
                sighting.getTime(),
                sighting.getSightingId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String sql = "DELETE FROM sighting WHERE sightingId = ?;";
        jdbc.update(sql, id);
    }
    
    private Location setLocationForSighting(Sighting sighting){
        final String SELECT_LOCATION_FOR_ORG = "SELECT l.* FROM location l"
            + " JOIN sighting s ON s.locationId = l.locationId WHERE sightingId = ?;";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_ORG, new LocationMapper(), sighting.getSightingId());
    }
    
    private Superhero setSuperheroForSighting(Sighting sighting){
        final String SELECT_LOCATION_FOR_ORG = "SELECT h.* FROM hero h"
            + " JOIN sighting s ON s.heroId = h.heroId WHERE sightingId = ?;";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_ORG, new HeroMapper(), sighting.getSightingId());
    }
    
    public final class SightingMapper implements RowMapper<Sighting> {
        // make it easier to get round objects from sql
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            
            sighting.setSightingId(rs.getInt("sightingId"));
            
            sighting.setHero(hDao.getSuperheroById(rs.getInt("heroId")));
            
            sighting.setLocation(lDao.getLocationById(rs.getInt("locationId")));
            
            String str = rs.getString("time");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDate date = LocalDate.parse(str, formatter);
            sighting.setTime(date);
            
            return sighting;
        }
    }
    
    
}
