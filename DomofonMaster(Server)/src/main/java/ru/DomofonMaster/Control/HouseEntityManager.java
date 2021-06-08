package ru.DomofonMaster.Control;

import ru.DomofonMaster.Entities.HouseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;


public class HouseEntityManager extends EntityManager<HouseEntity> {
    @Override
    protected HouseEntity GetEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id_house");
        int number = rs.getInt("number");
        String notes = rs.getString("notes");
        long street_id = rs.getLong("street_id");

        HouseEntity HE = new HouseEntity();
        HE.setId_house(id);
        HE.setNumber(number);
        HE.setNotes(notes);
        HE.setStreet_id(street_id);

        return HE;

    }
}
