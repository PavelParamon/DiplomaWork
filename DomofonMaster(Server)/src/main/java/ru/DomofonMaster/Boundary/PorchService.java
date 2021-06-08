package ru.DomofonMaster.Boundary;

import ru.DomofonMaster.Control.PorchEntityManager;
import ru.DomofonMaster.Entities.PorchEntity;

import java.sql.SQLException;
import java.util.List;

public class PorchService {
    public List<PorchEntity> GetPorches(int street_id) throws SQLException, ClassNotFoundException {
        PorchEntityManager entityManager = new PorchEntityManager();
        return entityManager.List(String.format("SELECT * from \"Porch\" where house_id ='%s'", street_id));
    }
}
