package ng.com.codetrik.notepad;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ng.com.codetrik.notepad")
@Profile("production")
public class HibernateConfig {
    @Autowired
    private Environment env;
    String url;
    String password;
    String username;

    @Bean
    public DataSource dataSource() {

        if(env != null){
            url = env.getProperty("JDBC_DATABASE_URL");
            username = env.getProperty("JDBC_DATABASE_USERNAME");
            password = env.getProperty("JDBC_DATABASE_PASSWORD");
        }else{
            url = "jdbc:postgresql://ec2-34-227-120-94.compute-1.amazonaws.com:5432/d3feoncbee7em4?sslmode=require&user=gknivmpxchamku&password=0f1b8874989db85d558cd4bc276453bb590cae9033db96ed44371be388996263";
            username = "gknivmpxchamku";
            password = "0f1b8874989db85d558cd4bc276453bb590cae9033db96ed44371be388996263";

        }
        HikariConfig hConfig = new HikariConfig();

        hConfig.setDriverClassName("org.postgresql.Driver");
        hConfig.setJdbcUrl(url);
        hConfig.setUsername(username);
        hConfig.setPassword(password);
        hConfig.setSchema("codetrik_server");
        hConfig.setAutoCommit(true);
        hConfig.setConnectionTimeout(30000L);
        hConfig.setIdleTimeout(60000L);
        hConfig.setMaxLifetime(1800000L);
        hConfig.setMinimumIdle(10);
        hConfig.setMaximumPoolSize(10);
        hConfig.setInitializationFailTimeout(1L);

        return new HikariDataSource(hConfig);
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource);

        em.setPackagesToScan("ng.com.codetrik.notepad");

        em.setPersistenceUnitName("punit");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);

        em.setJpaProperties(additionalProperties());

        return em;
    }
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){

        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor(){

        return new PersistenceAnnotationBeanPostProcessor();
    }

    public Properties additionalProperties() {

        Properties properties = new Properties();

        properties.setProperty("hibernate.hbm2ddl.auto", "update");

        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        properties.setProperty("hibernate.format_sql", "true");

        return properties;
    }

}

