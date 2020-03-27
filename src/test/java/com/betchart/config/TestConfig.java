package com.betchart.config;

import com.betchart.dao.ScheduledItemDAO;
import com.betchart.dao.ScheduledItemParamDAO;
import com.betchart.dao.impl.ScheduledItemDAOImpl;
import com.betchart.dao.impl.ScheduledItemParamDAOImpl;
import com.betchart.service.ScheduledItemService;
import com.betchart.service.impl.ScheduledItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
//@ComponentScan("com.betchart")
@PropertySource("classpath:application.properties")
public class TestConfig {

    @Autowired
    private Environment env;
 
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource testDataSource = new DriverManagerDataSource();
        testDataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        testDataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        testDataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        testDataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        return testDataSource;
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    	return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    	return new JdbcTemplate(dataSource);
    }

    @Bean
    public ScheduledItemDAO scheduledItemDAO() {
        return new ScheduledItemDAOImpl();
    }

    @Bean
    public ScheduledItemParamDAO scheduledItemParamDAO() {
        return new ScheduledItemParamDAOImpl();
    }

    @Bean
    public ScheduledItemService scheduledItemService() {
        return new ScheduledItemServiceImpl();
    }
    
}
