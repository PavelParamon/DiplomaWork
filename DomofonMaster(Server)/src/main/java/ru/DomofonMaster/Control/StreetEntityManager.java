package ru.DomofonMaster.Control;

import ru.DomofonMaster.Entities.StreetEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StreetEntityManager extends EntityManager<StreetEntity> {
    @Override
    protected StreetEntity GetEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id_street");
        String name_street = rs.getString("name_street");
        long city_id = rs.getLong("city_id");

        StreetEntity SE = new StreetEntity();
        SE.setId_street(id);
        SE.setName_street(name_street);
        SE.setCity_id(city_id);

        return SE;

    }
}
