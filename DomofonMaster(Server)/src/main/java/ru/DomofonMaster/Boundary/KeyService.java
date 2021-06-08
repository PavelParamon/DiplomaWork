package ru.DomofonMaster.Boundary;

import ru.DomofonMaster.Control.KeyEntityManager;
import ru.DomofonMaster.Entities.KeyEntity;

import java.sql.SQLException;
import java.util.List;

public class KeyService {
    public List<KeyEntity> GetKeysViaPorch(int porch_id) throws SQLException, ClassNotFoundException {
        KeyEntityManager entityManager = new KeyEntityManager();
        return entityManager.List(String.format("SELECT * from \"Key\" where porch_id ='%s'", porch_id));
    }

    public List<KeyEntity> GetKeysViaIntercom(int intercom_id) throws SQLException, ClassNotFoundException {
        KeyEntityManager entityManager = new KeyEntityManager();
        return entityManager.List(String.format("SELECT * from \"Key\" where intercom_id ='%s'", intercom_id));
    }

    public void AddKey(KeyEntity KE, String fk) throws SQLException, ClassNotFoundException {
        KeyEntityManager entityManager = new KeyEntityManager();
        entityManager.Add(KE, fk);
    }
}
