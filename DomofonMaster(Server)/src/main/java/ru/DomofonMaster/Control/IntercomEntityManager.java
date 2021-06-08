package ru.DomofonMaster.Control;

import ru.DomofonMaster.Entities.IntercomEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntercomEntityManager extends EntityManager<IntercomEntity> {
    @Override
    protected IntercomEntity GetEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id_intercom");
        String manufacturer = rs.getString("manufacturer");

        IntercomEntity IE = new IntercomEntity();
        IE.setId_intercom(id);
        IE.setManufacturer(manufacturer);

        return IE;
    }
}
