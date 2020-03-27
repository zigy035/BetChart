package com.betchart.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@EnableTransactionManagement
@ComponentScan("com.betchart")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Autowired
    private Environment env;
 
    @Bean
    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        config.setJdbcUrl(env.getRequiredProperty("jdbc.url"));
        config.setUsername(env.getRequiredProperty("jdbc.username"));
        config.setPassword(env.getRequiredProperty("jdbc.password"));

        config.setPoolName("BetChartCP");
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);

        return new HikariDataSource(config);

        /*DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        return dataSource;*/
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    	return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    	return new JdbcTemplate(dataSource);
    }
    
    /*@Bean
    public ScheduledItemDAO scheduledItemDAO() {
    	return new ScheduledItemDAOImpl();
    }

    @Bean
    public LeagueDAO fscLeagueDAO() {
        return new LeagueDAOImpl();
    }

    @Bean
    public ClubDAO fscClubDAO() {
        return new ClubDAOImpl();
    }

    @Bean
    public FscPlayerDAO fscPlayerDAO() {
        return new FscPlayerDAOImpl();
    }*/
    
}
