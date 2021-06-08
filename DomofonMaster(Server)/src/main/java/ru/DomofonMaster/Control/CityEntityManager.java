package ru.DomofonMaster.Control;

import ru.DomofonMaster.Entities.CityEntity;

import java.sql.*;



public class CityEntityManager extends EntityManager<CityEntity> {
    @Override
    protected CityEntity GetEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id_city");
        String name_city = rs.getString("name_city");

        CityEntity CE = new CityEntity();
        CE.setId_city(id);
        CE.setName_city(name_city);

        return CE;
    }
}
