package com.int20h2021.testtask.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
@Profile("prod")
public class ProdDataConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    @SneakyThrows
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // fixes error on connecting to Heroku Postgres
        databaseUrl += "?useUnicode=true&amp;characterEncoding=UTF-8";
        URI dbUri = new URI(databaseUrl);
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String url = "jdbc:postgresql://"
                + dbUri.getHost()
                + ':'
                + dbUri.getPort()
                + dbUri.getPath();

        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }
}
