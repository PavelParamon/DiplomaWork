package ru.DomofonMaster.Control;

import ru.DomofonMaster.Entities.PorchEntity;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PorchEntityManager extends EntityManager<PorchEntity> {
    @Override
    protected PorchEntity GetEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id_porch");
        int number = rs.getInt("number");
        long house_id = rs.getLong("house_id");

        PorchEntity PE = new PorchEntity();
        PE.setId_porch(id);
        PE.setNumber(number);
        PE.setHouse_id(house_id);

        return PE;

    }
}
