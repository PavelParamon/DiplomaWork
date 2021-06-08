package ru.DomofonMaster.DB;

import java.sql.*;

public class DBcontrol {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/dbDomofons";
    private static final String DATABASE_USERNAME = "postgres";
    private static final String DATABASE_PASSWORD = "1234";

    private Connection connection;
    private Statement statement;
    private ResultSet rs;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public DBcontrol(String querry, String type) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        this.statement = connection.createStatement();
        if(!type.equals("insert") && !type.equals("delete"))
            this.rs = statement.executeQuery(querry);
        else statement.execute(querry);
    }
}
