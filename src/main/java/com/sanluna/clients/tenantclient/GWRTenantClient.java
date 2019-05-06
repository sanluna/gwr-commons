package com.sanluna.clients.tenantclient;

import com.sanluna.multitenancy.multitenancy.model.GWRDatabaseConnectionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;

import static com.sanluna.multitenancy.communication.InterServiceCommunication.GETWithAuth;


public class GWRTenantClient {
    @Value("${gwr.tenant.url:http://localhost:18101/tenantservice/tenants/}")
    private String TENANT_URL;

    @Autowired
    private OAuth2RestOperations restTemplate;

    public GWRDatabaseConnectionInfo retrieveDatabaseByTenant(String tenantId) {
        String url = TENANT_URL + tenantId;
        return GETWithAuth(url, GWRDatabaseConnectionInfo.class, restTemplate);
    }

}
