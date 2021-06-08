package ru.DomofonMaster.Control;

import ru.DomofonMaster.Entities.KeyEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyEntityManager extends EntityManager<KeyEntity> {

    public void Add(KeyEntity KE, String fk) throws SQLException, ClassNotFoundException {
        String query;
        if(fk.equals("id_porch")){
            query = String.format("INSERT INTO \"Key\" (code, data_start, porch_id) VALUES (%d, '%s', %d)",
                    Long.parseLong(KE.getCode()), KE.getData_start(), KE.getPorch_id());
        }
        else{
            query = String.format("INSERT INTO \"Key\" (code, data_start, intercom_id) VALUES (%d, '%s', %d)",
                    Long.parseLong(KE.getCode()), KE.getData_start(), KE.getIntercom_id());
        }
        executeQueryVoid(query);

    }

    @Override
    protected KeyEntity GetEntity(ResultSet rs) throws SQLException {
        long id = rs.getLong("id_key");
        long code = rs.getLong("code");
        //boolean type = rs.getBoolean("type");
        String data_start = rs.getString("data_start");
        String data_end = rs.getString("data_end");
        long porch_id = rs.getLong("porch_id");
        long intercom_id = rs.getLong("intercom_id");

        KeyEntity KE = new KeyEntity();
        KE.setId_key(id);
        KE.setCode(Long.toString(code));
        //KE.setType(type);
        KE.setData_start(data_start);
        KE.setData_end(data_end);
        KE.setPorch_id(porch_id);
        KE.setIntercom_id(intercom_id);

        return KE;

    }
}
