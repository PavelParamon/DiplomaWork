package ru.DomofonMaster.Boundary;

import ru.DomofonMaster.Control.IntercomEntityManager;
import ru.DomofonMaster.Entities.IntercomEntity;

import java.sql.SQLException;
import java.util.List;

public class IntercomService {
    public List<IntercomEntity> GetIntercom() throws SQLException, ClassNotFoundException {
        IntercomEntityManager entityManager = new IntercomEntityManager();
        return entityManager.List("SELECT * from \"Intercom\"");
    }
}
