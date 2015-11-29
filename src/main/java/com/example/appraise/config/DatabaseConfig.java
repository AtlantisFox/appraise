package com.example.appraise.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接配置
 */
@Configuration
@EnableTransactionManagement
@PropertySources(@PropertySource("classpath:jdbc.properties"))
public class DatabaseConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        // 创建一个LocalSessionFactoryBean
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        // 配置数据源
        sessionFactory.setDataSource(dataSource());
        // 指定需要扫描的包，通过类(模型)的注解(annotation)，使类(模型)与数据库绑定。
        sessionFactory.setPackagesToScan("com.example.appraise.model");
        // sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        // 从jdbc.properties配置数据源
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        ds.setUrl(env.getProperty("jdbc.url"));
        ds.setUsername(env.getProperty("jdbc.username"));
        ds.setPassword(env.getProperty("jdbc.password"));
        return ds;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        // 创建一个HibernateTransactionManager
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    /*
    Properties hibernateProperties() {
        // using hibernate.properties instead
        return new Properties() {
            {
                // setProperty("hibernate.hbm2ddl.auto", "true");
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                setProperty("hibernate.show_sql", "true");
            }
        };
    }
    */
}
