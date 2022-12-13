package com.entrepidea.database.supports;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0DataSource {

    private ComboPooledDataSource comboPooledDataSource;

    private C3P0DataSource() {
        try {
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
            comboPooledDataSource
                    .setJdbcUrl("jdbc:mysql://localhost:3306/entrepidea");
            comboPooledDataSource.setUser("aaron");
            comboPooledDataSource.setPassword("f0rever");
        }
        catch (PropertyVetoException ex1) {
                ex1.printStackTrace();
            }
    }

    public Connection getConnection() {
            Connection con = null;
            try {
                con = comboPooledDataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return con;
    }

    static class LazyHolder {
        private static final C3P0DataSource instance = new C3P0DataSource();
    }
    public static C3P0DataSource getInstance(){
        return C3P0DataSource.LazyHolder.instance;
    }
}
