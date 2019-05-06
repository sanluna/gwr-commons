package com.sanluna;

import com.sanluna.multitenancy.multitenancy.GWRDatasourceLookup;
import com.sanluna.multitenancy.multitenancy.GWRMultiTenantConnectionProvider;
import com.sanluna.multitenancy.multitenancy.GWRTenantIdentifier;
import com.sanluna.multitenancy.multitenancy.GWRTenantInterceptor;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration
@Configuration
@ConditionalOnProperty(havingValue = "true", name = "gwr.multitenancy")
@ComponentScan({"com.sanluna.multitenancy"})
public class GWRMultiTenancyConfiguration {

    private GWRDatasourceLookup gwrDatasourceLookup;

    @Autowired
    private Environment env;

    @Bean
    public CurrentTenantIdentifierResolver tenantIdentifierResolver() {
        return new GWRTenantIdentifier();
    }

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DataSourceLookup dataSourceLookup() {
        return new GWRDatasourceLookup();
    }

    @Bean
    @Primary
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        return new GWRMultiTenantConnectionProvider();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    @Primary
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
                                                                       CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(org.hibernate.cfg.Environment.DRIVER, env.getProperty("spring.datasource.driverClassName", "org.h2.Driver"));
        properties.put(org.hibernate.cfg.Environment.DIALECT, env.getProperty("spring.jpa.hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"));
        properties.put(org.hibernate.cfg.Environment.URL, env.getProperty("spring.datasource.url", "sa"));
        properties.put(org.hibernate.cfg.Environment.USER, env.getProperty("spring.datasource.username", "sa"));
        properties.put(org.hibernate.cfg.Environment.PASS, env.getProperty("spring.datasource.password", ""));
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, env.getProperty("spring.jpa.hibernate.ddl-auto", "create"));
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, env.getProperty("spring.jpa.show-sql", "false"));

        properties.put(org.hibernate.cfg.Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        properties.put(org.hibernate.cfg.Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
        properties.put(org.hibernate.cfg.Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.sanluna");
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Configuration
    @ConditionalOnWebApplication
    public static class TenantWebConfig implements WebMvcConfigurer {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new GWRTenantInterceptor());
        }
    }

}
