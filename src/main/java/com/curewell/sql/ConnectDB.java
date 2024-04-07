package com.curewell.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
    private final String databaseName = "curewell";
    private final String databaseUser = "root";
    private final String databasePassword = "";
    private final String url = "jdbc:mysql://localhost:3307/" + databaseName + "?zeroDateTimeBehavior=convertToNull";

    public Connection conn;
    public Statement statement;

    public ConnectDB() throws SQLException, ClassNotFoundException {
        //try {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, databaseUser, databasePassword);

            statement = conn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
    public Statement getStatement() {
        return statement;
    }
}
