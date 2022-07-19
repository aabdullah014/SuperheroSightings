/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.superherosightings.data;

import com.sg.superherosightings.models.Power;
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
public class PowerDaoDBImpl implements PowerDao {

    @Autowired
    private final JdbcTemplate jdbc;

    public PowerDaoDBImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    @Override
    @Transactional
    public Power addPower(Power power) {
        
        final String sql = "INSERT INTO power(name, description) VALUES(?, ?);";

        jdbc.update(sql,
            power.getName(),
            power.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setPowerId(newId);

        return power;
        
    }

    @Override
    public Power getPowerById(int id) {
        try {
        
            final String sql = "SELECT * "
                    + "FROM power WHERE powerId = ?;";

            return jdbc.queryForObject(sql, new PowerMapper(), id);
            
        } catch(DataAccessException e){
        
            return null;
            
        }
        
    }

    @Override
    public List<Power> getAllPower() {
        final String sql = "SELECT * FROM power;";
        return jdbc.query(sql, new PowerMapper());
    }

    @Override
    public void updatePower(Power power) {
        final String sql = "UPDATE power SET name = ?, description = ? WHERE powerId = ?;";
        jdbc.update(sql,
                power.getName(),
                power.getDescription(),
                power.getPowerId());
    }

    @Override
    @Transactional
    public void deletePowerById(int id) {
        
        final String DELETE_ORG_HERO = "DELETE ho.* FROM heroOrg ho "
                + "JOIN hero h ON ho.heroId = h.heroId WHERE h.powerId = ?;";
        jdbc.update(DELETE_ORG_HERO, id);
        
        final String DELETE_HERO = "DELETE FROM hero WHERE powerId = ?;";
        jdbc.update(DELETE_HERO, id);
        
        final String sql = "DELETE FROM power WHERE powerId = ?;";
        jdbc.update(sql, id);
    }
    
    public static final class PowerMapper implements RowMapper<Power> {
        // make it easier to get round objects from sql
        @Override
        public Power mapRow(ResultSet rs, int index) throws SQLException {
            Power power = new Power();
            power.setPowerId(rs.getInt("powerId"));
            power.setName(rs.getString("name"));
            power.setDescription(rs.getString("description"));
            return power;
        }
    }
    
}
