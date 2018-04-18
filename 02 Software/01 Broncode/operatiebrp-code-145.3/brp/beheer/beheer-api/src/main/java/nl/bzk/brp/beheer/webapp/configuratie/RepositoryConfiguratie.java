/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import java.util.Properties;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie.ArchiveringRepositoriesConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie.KernRepositoriesConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie.MasterRepositoriesConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie.ProtocolleringRepositoriesConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Archivering;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Kern;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Protocollering;
import nl.bzk.brp.beheer.webapp.configuratie.jpa.CustomJpaRepositoryFactoryBean;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * Repository configuratie.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {RepositoryConfiguratie.PACKAGE_REPOSITORIES, "nl.bzk.brp.beheer.webapp.validatie", "nl.bzk.brp.beheer.webapp.view"})
@PropertySource("classpath:beheer.properties")
@Import(value = {KernRepositoriesConfiguratie.class, MasterRepositoriesConfiguratie.class, ArchiveringRepositoriesConfiguratie.class,
        ProtocolleringRepositoriesConfiguratie.class})
public class RepositoryConfiguratie {

    /**
     * Data source: voor lezen uit kern gegevens.
     */
    public static final String DATA_SOURCE_KERN = "kernDataSource";
    /**
     * Data source: voor schrijven in kern gegevens.
     */
    public static final String DATA_SOURCE_MASTER = "masterDataSource";
    /**
     * Data source: voor lezen uit archivering gegevens.
     */
    public static final String DATA_SOURCE_ARCHIEVERING = "berDataSource";
    /**
     * Data source: voor lezen uit archivering gegevens.
     */
    public static final String DATA_SOURCE_PROTOCOLLERING = "levDataSource";

    /**
     * Persistence unit: voor lezen uit kern gegevens.
     */
    public static final String PERSISTENCE_UNIT_KERN = "nl.bzk.brp.alleenlezen";
    /**
     * Persistence unit: voor schrijven in kern gegevens.
     */
    public static final String PERSISTENCE_UNIT_MASTER = "nl.bzk.brp.lezenschrijven";
    /**
     * Persistence unit: voor lezen uit archiering gegevens.
     */
    public static final String PERSISTENCE_UNIT_ARCHIVERING = "nl.bzk.brp.ber.alleenlezen";
    /**
     * Persistence unit: voor lezen uit archiering gegevens.
     */
    public static final String PERSISTENCE_UNIT_PROTOCOLLERING = "nl.bzk.brp.lev.alleenlezen";

    /**
     * Entity manager: voor lezen uit kern gegevens.
     */
    public static final String ENTITY_MANAGER_KERN = "kernEntityManager";
    /**
     * Entity manager: voor schrijven in kern gegevens.
     */
    public static final String ENTITY_MANAGER_MASTER = "masterEntityManager";
    /**
     * Entity manager: voor lezen uit archivering gegevens.
     */
    public static final String ENTITY_MANAGER_ARCHIVERING = "berEntityManager";
    /**
     * Entity manager: voor lezen uit protocollering gegevens.
     */
    public static final String ENTITY_MANAGER_PROTOCOLLERING = "levEntityManager";

    /**
     * Transaction manager.
     */
    public static final String TRANSACTION_MANAGER = "transactionManager";

    /**
     * Package met repositories.
     */
    static final String PACKAGE_REPOSITORIES = "nl.bzk.brp.beheer.webapp.repository";

    private static final String PROPERTY_NAME_DATABASE_DRIVER = "database.driver";

    private static final String PROPERTY_NAME_POOL_INITIAL_SIZE = "jdbc.pool.initialSize";
    private static final String PROPERTY_NAME_POOL_MAX_ACTIVE = "jdbc.pool.maxActive";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";

    private static final String PROPERTY_NAME_HIBERNATE_SQL_COMMENTS = "hibernate.use_sql_comments";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_JAVAX_PERSISTENCE_VALIDATION = "javax.persistence.validation.mode";

    private static final String PROPERTY_NAME_TRANSACTION_TIMEOUT = "transaction.timeout";
    private static final String PACKAGE_USERTYPES = "nl.bzk.brp.model.jpa.usertypes";

    private static final String PACKAGE_STAMGEGEVEN_ENTITY = "nl.bzk.algemeenbrp.dal.domein.brp.entity";

    private static final String PACKAGE_BEHEER_VIEW = "nl.bzk.brp.beheer.webapp.view";

    private Environment environment;

    /**
     * Default constructor.
     * @param environment Environment via constructor injection
     */
    @Inject
    public RepositoryConfiguratie(final Environment environment) {
        this.environment = environment;
    }

    /**
     * @return datasource
     */
    private BasicDataSource basicDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();

        // Driver
        dataSource.setDriverClassName(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));

        // Pool
        dataSource.setInitialSize(Integer.parseInt(environment.getRequiredProperty(PROPERTY_NAME_POOL_INITIAL_SIZE)));
        dataSource.setMaxActive(Integer.parseInt(environment.getRequiredProperty(PROPERTY_NAME_POOL_MAX_ACTIVE)));

        return dataSource;
    }

    /**
     * @return datasource
     */
    @Bean(name = DATA_SOURCE_KERN)
    public DataSource kernDataSource() {
        final BasicDataSource dataSource = basicDataSource();

        // Connection
        dataSource.setUrl(environment.getRequiredProperty("database.kern.url"));
        dataSource.setUsername(environment.getRequiredProperty("database.kern.username"));
        dataSource.setPassword(environment.getRequiredProperty("database.kern.password"));

        return dataSource;
    }

    /**
     * @return datasource
     */
    @Bean(name = DATA_SOURCE_MASTER)
    public DataSource masterDataSource() {
        final BasicDataSource dataSource = basicDataSource();

        // Connection
        dataSource.setUrl(environment.getRequiredProperty("database.master.url"));
        dataSource.setUsername(environment.getRequiredProperty("database.master.username"));
        dataSource.setPassword(environment.getRequiredProperty("database.master.password"));

        return dataSource;
    }

    /**
     * @return datasource
     */
    @Bean(name = DATA_SOURCE_ARCHIEVERING)
    public DataSource archiveringDataSource() {
        final BasicDataSource dataSource = basicDataSource();

        // Connection
        dataSource.setUrl(environment.getRequiredProperty("database.archivering.url"));
        dataSource.setUsername(environment.getRequiredProperty("database.archivering.username"));
        dataSource.setPassword(environment.getRequiredProperty("database.archivering.password"));

        return dataSource;
    }

    /**
     * @return datasource
     */
    @Bean(name = DATA_SOURCE_PROTOCOLLERING)
    public DataSource protocolleringDataSource() {
        final BasicDataSource dataSource = basicDataSource();

        // Connection
        dataSource.setUrl(environment.getRequiredProperty("database.protocollering.url"));
        dataSource.setUsername(environment.getRequiredProperty("database.protocollering.username"));
        dataSource.setPassword(environment.getRequiredProperty("database.protocollering.password"));

        return dataSource;
    }

    /**
     * @return entity manager factory (kern; alleen lezen)
     */
    @Bean(name = ENTITY_MANAGER_KERN)
    @DependsOn(TRANSACTION_MANAGER)
    public LocalContainerEntityManagerFactoryBean kernEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(kernDataSource());
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_KERN);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(PACKAGE_USERTYPES, PACKAGE_STAMGEGEVEN_ENTITY, PACKAGE_BEHEER_VIEW);

        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        return entityManagerFactoryBean;
    }

    /**
     * @return entity manager factory (kern; schrijven)
     */
    @Bean(name = ENTITY_MANAGER_MASTER)
    @DependsOn(TRANSACTION_MANAGER)
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(masterDataSource());
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_MASTER);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(PACKAGE_USERTYPES, PACKAGE_STAMGEGEVEN_ENTITY, PACKAGE_BEHEER_VIEW);
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        return entityManagerFactoryBean;
    }

    /**
     * @return entity manager factory (archivering; alleen lezen)
     */
    @Bean(name = ENTITY_MANAGER_ARCHIVERING)
    @DependsOn(TRANSACTION_MANAGER)
    public LocalContainerEntityManagerFactoryBean archiveringEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(archiveringDataSource());
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_ARCHIVERING);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(PACKAGE_USERTYPES, PACKAGE_STAMGEGEVEN_ENTITY, PACKAGE_BEHEER_VIEW);

        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        return entityManagerFactoryBean;
    }

    /**
     * @return entity manager factory (protocollering; alleen lezen)
     */
    @Bean(name = ENTITY_MANAGER_PROTOCOLLERING)
    @DependsOn(TRANSACTION_MANAGER)
    public LocalContainerEntityManagerFactoryBean protocolleringEntityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(protocolleringDataSource());
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_PROTOCOLLERING);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(PACKAGE_STAMGEGEVEN_ENTITY, PACKAGE_BEHEER_VIEW);

        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        return entityManagerFactoryBean;
    }

    private Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

        properties.put(PROPERTY_NAME_HIBERNATE_SQL_COMMENTS, environment.getProperty(PROPERTY_NAME_HIBERNATE_SQL_COMMENTS));
        properties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));

        properties.put(PROPERTY_NAME_JAVAX_PERSISTENCE_VALIDATION, environment.getProperty(PROPERTY_NAME_JAVAX_PERSISTENCE_VALIDATION));
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", Boolean.FALSE);
        properties.put("hibernate.cache.use_second_level_cache", Boolean.TRUE);
        properties.put("hibernate.cache.use_query_cache", Boolean.FALSE);
        properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");

        properties.put("hibernate.transaction.jta.platform", new SpringJtaPlatform(transactionManager()));

        return properties;
    }

    /**
     * Atokmikos user transaction.
     * @return Atokmikos user transaction
     */
    @Bean
    public UserTransaction atomikosUserTransaction() {
        final UserTransactionImp userTransactionImp = new UserTransactionImp();
        try {
            userTransactionImp.setTransactionTimeout(environment.getRequiredProperty(PROPERTY_NAME_TRANSACTION_TIMEOUT, Integer.class));
        } catch (final SystemException e) {
            throw new IllegalArgumentException("Kan timeout niet zetten", e);
        }
        return userTransactionImp;
    }

    /**
     * Atomikos transaction manager.
     * @return atomikos transaction manager
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager() {
        final UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    /**
     * JTA transaction manager.
     * @return JTA transaction manager
     */
    @Bean(name = TRANSACTION_MANAGER)
    public JtaTransactionManager transactionManager() {
        return new JtaTransactionManager(atomikosUserTransaction(), atomikosTransactionManager());
    }

    /**
     * Kern repositories.
     */
    @Configuration
    @EnableJpaRepositories(value = PACKAGE_REPOSITORIES, includeFilters = @Filter(type = FilterType.ANNOTATION, value = Kern.class),
            entityManagerFactoryRef = ENTITY_MANAGER_KERN, transactionManagerRef = TRANSACTION_MANAGER,
            repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
    public static class KernRepositoriesConfiguratie {

    }

    /**
     * Master repositories.
     */
    @Configuration
    @EnableJpaRepositories(value = PACKAGE_REPOSITORIES, includeFilters = @Filter(type = FilterType.ANNOTATION, value = Master.class),
            entityManagerFactoryRef = ENTITY_MANAGER_MASTER, transactionManagerRef = TRANSACTION_MANAGER,
            repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
    public static class MasterRepositoriesConfiguratie {

    }

    /**
     * Archivering repositories.
     */
    @Configuration
    @EnableJpaRepositories(value = PACKAGE_REPOSITORIES, includeFilters = @Filter(type = FilterType.ANNOTATION, value = Archivering.class),
            entityManagerFactoryRef = ENTITY_MANAGER_ARCHIVERING, transactionManagerRef = TRANSACTION_MANAGER,
            repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
    public static class ArchiveringRepositoriesConfiguratie {

    }

    /**
     * Archivering repositories.
     */
    @Configuration
    @EnableJpaRepositories(value = PACKAGE_REPOSITORIES, includeFilters = @Filter(type = FilterType.ANNOTATION, value = Protocollering.class),
            entityManagerFactoryRef = ENTITY_MANAGER_PROTOCOLLERING, transactionManagerRef = TRANSACTION_MANAGER,
            repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
    public static class ProtocolleringRepositoriesConfiguratie {

    }

}
