package com.terekhin.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.core.env.Environment;

import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource({"classpath:db.properties"})
@ComponentScan(basePackages = {"com.terekhin.database"})
@EnableTransactionManagement
public class DBContextConfig {


    private final Environment env;

    @Autowired
    public DBContextConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver_class"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public Properties hibernateProps()
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect",env.getProperty("db.hibernate.dialect"));
        properties.put("hibernate.show_sql",env.getProperty("db.hibernate.show_sql"));
        properties.put("hibernate.format_sql",env.getProperty("db.hibernate.format_sql"));
        properties.put("hibernate.hdm2ddl.auto",env.getProperty("db.hibernate.hdm2ddl.auto"));

        return properties;
    }

   /* @Bean
    public SessionFactory sessionFactory() throws IOException
    {

        LocalSessionFactoryBean _session = new LocalSessionFactoryBean();
        _session.setDataSource(dataSource());
        _session.setPackagesToScan(env.getProperty("db.hibernate.packagesToScan"));
        _session.setHibernateProperties(hibernateProps());
        _session.afterPropertiesSet();

        return _session.getObject();
    }

    @Bean(name="hibernate_TX")
    @Autowired
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory)
    {
        return new HibernateTransactionManager(sessionFactory);
    }*/


    @Bean(name="jpa_emf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(env.getProperty("db.hibernate.packagesToScan"));

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(hibernateProps());

        return entityManagerFactoryBean;
    }

    @Bean(name="jpa_tx")
    @Autowired
    @Qualifier("jpa_emf")
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
