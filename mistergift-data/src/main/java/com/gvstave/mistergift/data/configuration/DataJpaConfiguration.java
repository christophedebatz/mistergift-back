package com.gvstave.mistergift.data.configuration;

import com.gvstave.mistergift.data.domain.jpa.DataDomainJpa;
import com.gvstave.mistergift.data.persistence.jpa.DataPersistenceJpa;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * .
 */
@Configuration
@ComponentScan(basePackageClasses = { DataPersistenceJpa.class, DataDomainJpa.class })
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackageClasses = { DataPersistenceJpa.class, DataDomainJpa.class })
public class DataJpaConfiguration {

    @Inject
    private Environment env;

    @Bean
    public DataSource dataSource () {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setUrl(env.getProperty("spring.datasource.url"));
        driver.setDriverClassName(env.getProperty("spring.datasource.driver"));
        driver.setUsername(env.getProperty("spring.datasource.username"));
        driver.setPassword(env.getProperty("spring.datasource.password"));
        return driver;
    }

    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = MySQLTemplates.builder().build(); //change to your Templates
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        return configuration;
    }

    @Bean
    public SQLQueryFactory queryFactory() {
        Provider<Connection> provider = new SpringConnectionProvider(dataSource());
        return new SQLQueryFactory(querydslConfiguration(), provider);
    }


    @Bean(name = "entityManager")
    public EntityManager entityManager () {
        return entityManagerFactory().createEntityManager();
    }

    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory () {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(Boolean.valueOf(env.getProperty("hibernate.ddl")));
        vendorAdapter.setShowSql(Boolean.valueOf(env.getProperty("hibernate.show_sql")));
        vendorAdapter.setDatabasePlatform(env.getProperty("hibernate.dialect"));

        Properties jpaProps = new Properties();
        jpaProps.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        jpaProps.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.gvstave.mistergift.data.domain.jpa");
        factory.setDataSource(dataSource());
        factory.setJpaProperties(jpaProps);
        factory.setPersistenceUnitName("mistergift_pu");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

}