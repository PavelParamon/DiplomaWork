package ru.DomofonMaster.Boundary;

import ru.DomofonMaster.Control.HouseEntityManager;
import ru.DomofonMaster.Entities.HouseEntity;

import java.sql.SQLException;
import java.util.List;

public class HouseService {
    public List<HouseEntity> GetHouses(int street_id) throws SQLException, ClassNotFoundException {
        HouseEntityManager entityManager = new HouseEntityManager();
        return entityManager.List(String.format("SELECT * from \"House\" where street_id ='%s'", street_id));
    }
}
