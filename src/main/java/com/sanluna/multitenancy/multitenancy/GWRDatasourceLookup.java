package com.sanluna.multitenancy.multitenancy;

import com.sanluna.multitenancy.multitenancy.model.GWRDatabaseConnectionInfo;
import com.sanluna.clients.tenantclient.GWRTenantClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;

import javax.sql.DataSource;

public class GWRDatasourceLookup extends MapDataSourceLookup {


    @Autowired
    private GWRTenantClient tenantClient;

    public GWRDatasourceLookup() {
        super();
    }

    @Override
    public DataSource getDataSource(String dataSourceName) {
        DataSource ds;

        try {
            ds = super.getDataSource(dataSourceName);
            return ds;
        } catch (DataSourceLookupFailureException e) {
        }

        DataSourceProperties props = getConfig(dataSourceName);
        DataSourceBuilder builder = props.initializeDataSourceBuilder();
        ds = builder.build();
        addDataSource(dataSourceName, ds);

        return ds;
    }

    private DataSourceProperties getConfig(String tenantId) {

        GWRDatabaseConnectionInfo databaseConnectionInfo = tenantClient.retrieveDatabaseByTenant(tenantId);
        DataSourceProperties dataSourceProps = new DataSourceProperties();

        dataSourceProps.setDriverClassName(databaseConnectionInfo.getDriverClassName());
        dataSourceProps.setUrl(databaseConnectionInfo.getUrl());
        dataSourceProps.setUsername(databaseConnectionInfo.getUsername());
        dataSourceProps.setPassword(databaseConnectionInfo.getPassword());

        return dataSourceProps;
    }
}
