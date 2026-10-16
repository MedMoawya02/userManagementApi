package com.usermanagement.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.usermanagement.repository")
public class PersistenceConfig {
    @Value("${db.driver}")
    private String driver;
    @Value("${db.url}")
    String url;
    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;
    @Value("${hibernate.show_sql:true}")
    private boolean showSql;

    @Value("${hibernate.format_sql:true}")
    private boolean formatSql;

    @Value("${hibernate.hbm2ddl.auto:update}")
    private String ddlAuto;
    @Bean
    public DataSource dataSource(){
        HikariDataSource dataSource=new HikariDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(10);
        return dataSource;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan( "com.usermanagement.entity");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties properties = new Properties();
        properties.setProperty( "hibernate.show_sql",
                String.valueOf(showSql));
        properties.setProperty(
                "hibernate.format_sql",
                String.valueOf(formatSql)
        );

        properties.setProperty(
                "hibernate.hbm2ddl.auto",
                ddlAuto
        );
        factory.setJpaProperties(properties);
        return factory;
    }
    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
