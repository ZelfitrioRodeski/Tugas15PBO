package com.java.minimarket.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionUtil {

    private static HikariDataSource datasource;
    static{
         HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driber");
        config.setJdbcUrl( "jdbc:mysql://localhost:3306/crud_jdbc");
        config.setUsername("root");
        config.setPassword("");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10*60_000);

        datasource = new HikariDataSource(config);
    }
    public static HikariDataSource getDataSource(){
        return datasource;
    }
}

