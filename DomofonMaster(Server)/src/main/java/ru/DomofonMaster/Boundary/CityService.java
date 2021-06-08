package ru.DomofonMaster.Boundary;

import ru.DomofonMaster.Control.CityEntityManager;
import ru.DomofonMaster.Entities.CityEntity;

import java.sql.SQLException;
import java.util.List;

public class CityService {
    public List<CityEntity> GetCity() throws SQLException, ClassNotFoundException {
        CityEntityManager entityManager = new CityEntityManager();
        return entityManager.List("SELECT * from \"City\"");
    }
}
