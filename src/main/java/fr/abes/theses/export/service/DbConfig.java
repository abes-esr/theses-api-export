package fr.abes.theses.export.service;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Bean
    @ConfigurationProperties("spring.theses.datasource")
    public DataSourceProperties dataSourceThesesProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource dataSourceTheses() {

        return DataSourceBuilder.create().url(dataSourceThesesProperties().getUrl())
                .username(dataSourceThesesProperties().getUsername()).password(dataSourceThesesProperties().getPassword())
                .type(OracleDataSource.class).build();
    }


    @Bean
    @ConfigurationProperties("spring.basexml.datasource")
    public DataSourceProperties dataSourceBaseXmlProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSourceBaseXml() {

        return DataSourceBuilder.create().url(dataSourceBaseXmlProperties().getUrl())
                .username(dataSourceBaseXmlProperties().getUsername()).password(dataSourceBaseXmlProperties().getPassword())
                .type(OracleDataSource.class).build();
    }

    @Bean
    public JdbcTemplate jdbcTemplateTheses() {
        return new JdbcTemplate(dataSourceTheses());
    }

    @Bean
    public JdbcTemplate jdbcTemplateBaseXml() {
        return new JdbcTemplate(dataSourceBaseXml());
    }

}
