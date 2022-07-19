/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Superhero;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class LocationDaoDBImpl implements LocationDao {

    @Autowired
    private final JdbcTemplate jdbc;

    public LocationDaoDBImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String sql = "INSERT INTO location(name, address, coordinates, description) VALUES(?, ?, ?, ?);";

        jdbc.update(sql,
            location.getName(),
            location.getAddress(),
            location.getCoordinates(),
            location.getDescription());
            
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);

        return location;
    }

    @Override
    public Location getLocationById(int id) {
        try{
            final String sql = "SELECT * "
                    + "FROM location WHERE locationId = ?";
            return jdbc.queryForObject(sql, new LocationMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
        
    }

    @Override
    public List<Location> getAllLocation() {
        final String sql = "SELECT * FROM location;";
        return jdbc.query(sql, new LocationMapper());
    }
    
    @Override
    public List<Location> getLocationsForSuperhero(Superhero hero) {
        final String sql = "SELECT l.* FROM location l"
                + " JOIN sighting s ON l.locationId = s.locationId WHERE s.heroId = ?;";
        
        List<Location> locations = jdbc.query(sql, new LocationMapper(), hero.getHeroId());
        
        return locations;
    }

    @Override
    public void updateLocation(Location location) {
        final String sql = "UPDATE location SET name = ?, address = ?, coordinates = ?, description = ? WHERE locationId = ?";
        jdbc.update(sql,
                location.getName(),
                location.getAddress(),
                location.getCoordinates(),
                location.getDescription(),
                location.getLocationId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_ORG_HERO = "DELETE ho.* FROM heroOrg ho "
                + "JOIN org o ON ho.orgId = o.orgId WHERE o.locationId = ?";
        jdbc.update(DELETE_ORG_HERO, id);
        
        final String DELETE_ORG = "DELETE FROM org WHERE locationId = ?";
        jdbc.update(DELETE_ORG, id);
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_LOCATION = "DELETE FROM location WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION, id);
    }
 
    public static final class LocationMapper implements RowMapper<Location> {
        // make it easier to get round objects from sql
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location rd = new Location();
            rd.setLocationId(rs.getInt("locationId"));
            rd.setName(rs.getString("name"));
            rd.setAddress(rs.getString("address"));
            rd.setCoordinates(rs.getString("coordinates"));
            rd.setDescription(rs.getString("description"));
            return rd;
        }
    }
    
}
