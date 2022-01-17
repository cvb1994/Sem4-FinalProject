//package com.personal.config;
//
//import javax.sql.DataSource;
//
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//@Configuration
//public class DataSourceConfig {
//	
//	private static final String INSTANCE_CONNECTION_NAME = System.getenv("INSTANCE_CONNECTION_NAME");
//	private static final String DB_USER = System.getenv("DB_USER");
//	private static final String DB_PASS = System.getenv("DB_PASS");
//	private static final String DB_NAME = System.getenv("DB_NAME");
//	
//	@Bean
//    public DataSource getDataSource() {
////        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
////        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
////        dataSourceBuilder.url("jdbc:h2:mem:test");
////        dataSourceBuilder.username("SA");
////        dataSourceBuilder.password("");
////        return dataSourceBuilder.build();
//		HikariConfig config = new HikariConfig();
//		config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
//		System.out.println("Im here");
//		System.out.println(String.format("jdbc:mysql:///%s", DB_NAME));
//		config.setUsername(DB_USER);
//		config.setPassword(DB_PASS);
//
//		config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
//		config.addDataSourceProperty("cloudSqlInstance", INSTANCE_CONNECTION_NAME);
//		
//		config.addDataSourceProperty("ipTypes", "PUBLIC");
//		
//		config.setConnectionTimeout(10000);
//		config.setIdleTimeout(600000); 
//		config.setMaxLifetime(1800000);
//		
//		DataSource pool = new HikariDataSource(config);
//		return pool;
//    }
//}
