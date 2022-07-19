/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.data.OrganizationDaoDBImpl.OrganizationMapper;
import com.sg.superherosightings.data.PowerDaoDBImpl.PowerMapper;
import com.sg.superherosightings.models.Location;
import com.sg.superherosightings.models.Organization;
import com.sg.superherosightings.models.Power;
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
public class SuperheroDaoDBImpl implements SuperheroDao {
    
    @Autowired
    private final JdbcTemplate jdbc;

    public SuperheroDaoDBImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    @Autowired
    PowerDao pDao;

    @Autowired
    OrganizationDaoDBImpl oDao;
    
    @Override
    @Transactional
    public Superhero addSuperhero(Superhero hero) {
        final String INSERT_HERO = "INSERT INTO hero(name, description, powerId) "
                + "VALUES(?, ?, ?)";
        
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getPower().getPowerId());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(newId);
        insertHeroOrg(hero);
        return hero;
    }
    
    private Power getPowerForHero(int id) {
        final String SELECT_POWER_FOR_HERO = "SELECT p.* FROM power p "
                + "JOIN hero h ON h.powerId = p.powerId WHERE h.heroId = ?";
        return jdbc.queryForObject(SELECT_POWER_FOR_HERO, new PowerMapper(), id);
    }
    
    private void insertHeroOrg(Superhero hero) {
        final String INSERT_HERO_ORG = "INSERT INTO "
                + "heroOrg(orgId, heroId) VALUES(?,?)";
        for(Organization org : hero.getOrgs()) {
            jdbc.update(INSERT_HERO_ORG, 
                    org.getOrgId(),
                    hero.getHeroId());
        }
    }

    @Override
    public Superhero getSuperheroById(int id) {
        try {
            
            final String SELECT_HERO_BY_ID = "SELECT * FROM hero WHERE heroId = ?;";
            Superhero hero  = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), id);
            hero.setPower(setPowerForHero(hero));
            hero.setOrgs(getOrgsForSuperhero(id));
            return hero;
                   
            
        } catch(DataAccessException e) {
            
            return null;
            
        }
    }

    public List<Organization> getOrgsForSuperhero(int id) {
        final String SELECT_ORG_FOR_HEROES = "SELECT o.* FROM org o "
                + "JOIN heroOrg ho ON ho.orgId = o.orgId WHERE ho.heroId = ?;";
        List<Organization> orgs = jdbc.query(SELECT_ORG_FOR_HEROES, new OrganizationMapper(), id);
        
        for(Organization org: orgs) {
            org.setLocation(oDao.setLocationForOrg(org));
        }
        
        return orgs;
    }
    
    @Override
    public List<Superhero> getAllSuperhero() {
        final String sql = "SELECT * FROM hero;";
        List<Superhero> heros = jdbc.query(sql, new HeroMapper());
        
        for(Superhero h: heros){
            h.setPower(setPowerForHero(h));
            h.setOrgs(getOrgsForSuperhero(h.getHeroId()));
        }
        
        return heros;
    }
    
    @Override
    public List<Superhero> getAllSuperheroForLocation(Location location){
        final String sql = "SELECT h.* FROM hero h JOIN "
                + "sighting s ON s.heroId = h.heroId WHERE s.locationId = ?";
        List<Superhero> heros = jdbc.query(sql, new HeroMapper(), location.getLocationId());
        
        for(Superhero h: heros){
            h.setPower(setPowerForHero(h));
            h.setOrgs(getOrgsForSuperhero(h.getHeroId()));
        }
        
        return heros;
    }

    @Override
    public void updateSuperhero(Superhero hero) {
        final String UPDATE_ORG = "UPDATE hero SET name = ?, description = ?, powerId = ? "
                + "WHERE heroId = ?";
        jdbc.update(UPDATE_ORG, 
                hero.getName(),
                hero.getDescription(),
                hero.getPower().getPowerId(),
                hero.getHeroId());
        
        final String DELETE_ORG_HERO = "DELETE FROM heroOrg WHERE heroId = ?";
        jdbc.update(DELETE_ORG_HERO, hero.getHeroId());
        
        final String DELETE_SIGHTING = "DELETE FROM sighting s WHERE s.heroId = ?";
        jdbc.update(DELETE_SIGHTING, hero.getHeroId());
        
        insertHeroOrg(hero);
    }

    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting s WHERE s.heroId = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_ORG_HERO = "DELETE FROM heroOrg WHERE heroId = ?";
        jdbc.update(DELETE_ORG_HERO, id);
        
        final String DELETE_HERO = "DELETE FROM hero WHERE heroId = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    public List<Superhero> getHeroesForOrganization(Organization org) {
        
        final String SELECT_HEROES__FOR_ORGS = "SELECT h.* FROM hero h JOIN "
                + "heroOrg ho ON ho.heroId = h.heroId WHERE ho.orgId = ?";
        List<Superhero> heroes = jdbc.query(SELECT_HEROES__FOR_ORGS, 
                new HeroMapper(), org.getOrgId());
        return heroes;
        
    }
    
    private Power setPowerForHero(Superhero hero){
        final String SELECT_POWER_FOR_HERO = "SELECT p.* FROM power p"
            + " JOIN hero h ON h.powerId = p.powerId WHERE heroId = ?;";
        return jdbc.queryForObject(SELECT_POWER_FOR_HERO, new PowerMapper(), hero.getHeroId());
    }
    
    public static final class HeroMapper implements RowMapper<Superhero> {
        // make it easier to get round objects from sql
        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero hero = new Superhero();
            hero.setHeroId(rs.getInt("heroId"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            return hero;
        }
    }
    
}
