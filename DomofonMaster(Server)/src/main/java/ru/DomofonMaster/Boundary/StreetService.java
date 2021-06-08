package ru.DomofonMaster.Boundary;

import ru.DomofonMaster.Control.StreetEntityManager;
import ru.DomofonMaster.Entities.StreetEntity;

import java.sql.SQLException;
import java.util.List;

public class StreetService {
    public List<StreetEntity> GetStreets(int city_id) throws SQLException, ClassNotFoundException {
        StreetEntityManager entityManager = new StreetEntityManager();
        return entityManager.List(String.format("SELECT * from \"Street\" where city_id ='%s'", city_id));
    }
}
