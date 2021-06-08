package ru.DomofonMaster.Control;

import ru.DomofonMaster.DB.DBcontrol;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityManager<T> {

    public EntityManager(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public List<T> List(String q) throws SQLException, ClassNotFoundException {
        return executeQuery(q);
    }

    private List<T> executeQuery(String q) throws SQLException, ClassNotFoundException {
        DBcontrol dBcontrol = new DBcontrol(q, "select");
        List<T> list = new ArrayList<T>();

        while(dBcontrol.getRs().next()){
            list.add(GetEntity(dBcontrol.getRs()));
        }
        return list;
    }

    protected void executeQueryVoid(String q) throws SQLException, ClassNotFoundException {
        DBcontrol dBcontrol = new DBcontrol(q, "insert");
        dBcontrol.getStatement().close();
    }

    protected abstract T GetEntity(ResultSet rs) throws SQLException;
}
