/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.data.LocationDaoDBImpl.LocationMapper;
import com.sg.superherosightings.data.SuperheroDaoDBImpl.HeroMapper;
import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Organization;
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
public class OrganizationDaoDBImpl implements OrganizationDao {

    @Autowired
    private final JdbcTemplate jdbc;

    public OrganizationDaoDBImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    @Autowired
    LocationDao lDao;
    
    @Override
    @Transactional
    public Organization addOrganization(Organization org) {
        final String INSERT_ORG = "INSERT INTO org(name, description, phone, locationId) "
                + "VALUES(?,?,?, ?)";
        jdbc.update(INSERT_ORG,
            org.getName(),
            org.getDescription(),
            org.getPhone(),
            org.getLocation().getLocationId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrgId(newId);
        return org;
    }
    
    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM org WHERE orgId = ?";
            Organization org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrganizationMapper(), id);
            org.setLocation(setLocationForOrg(org));
            return org;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganization() {
        final String sql = "SELECT * FROM org;";
        List<Organization> orgs = jdbc.query(sql, new OrganizationMapper());
        
        for(Organization org: orgs) {
            org.setLocation(setLocationForOrg(org));
        }
        
        return orgs;
    }
    
    @Override
    public List<Superhero> getMembers(Organization org){
        final String sql = "SELECT h.* FROM hero h JOIN "
                + "heroOrg ho ON ho.heroId = h.heroId WHERE ho.orgId = ?;";
        List<Superhero> heros = jdbc.query(sql, new HeroMapper(), org.getOrgId());
        
        return heros;
    }

    @Override
    public void updateOrganization(Organization org) {
        final String UPDATE_ORG = "UPDATE org SET name = ?, description = ?, phone = ?, locationId = ? "
                + "WHERE orgId = ?;";
        jdbc.update(UPDATE_ORG, 
                org.getName(),
                org.getDescription(),
                org.getPhone(),
                org.getLocation().getLocationId(),
                org.getOrgId());
        
        final String DELETE_ORG_HERO = "DELETE FROM heroOrg WHERE orgId = ?";
        jdbc.update(DELETE_ORG_HERO, org.getOrgId());
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        
        final String DELETE_ORG_HERO = "DELETE FROM heroOrg WHERE orgId = ?";
        jdbc.update(DELETE_ORG_HERO, id);
        
        final String DELETE_ORG = "DELETE FROM org WHERE orgId = ?";
        jdbc.update(DELETE_ORG, id);
        
    }

    @Override
    public List<Organization> getOrgsForSuperhero(Superhero hero) {
        final String SELECT_ORGS_FOR_HEROES = "SELECT o.* FROM org o JOIN "
                + "heroOrg ho ON ho.orgId = o.orgId WHERE ho.heroId = ?";
        List<Organization> orgs = jdbc.query(SELECT_ORGS_FOR_HEROES, 
                new OrganizationMapper(), hero.getHeroId());
        return orgs;
    }
    
    private List<Superhero> getSuperherosForOrganization(int id) {
        final String SELECT_HEROES_FOR_ORG = "SELECT s.* FROM hero s "
                + "JOIN heroOrg ho ON ho.heroId = s.heroId WHERE ho.orgId = ?";
        return jdbc.query(SELECT_HEROES_FOR_ORG, new HeroMapper(), id);
    }
    
    public Location setLocationForOrg(Organization org){
        final String SELECT_LOCATION_FOR_ORG = "SELECT l.* FROM location l"
            + " JOIN org o ON o.locationId = l.locationId WHERE orgId = ?;";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_ORG, new LocationMapper(), org.getOrgId());
    }
    
    public static final class OrganizationMapper implements RowMapper<Organization> {
        // make it easier to get round objects from sql
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setOrgId(rs.getInt("orgId"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setPhone(rs.getString("phone"));
            return org;
        }
    }
    
}
