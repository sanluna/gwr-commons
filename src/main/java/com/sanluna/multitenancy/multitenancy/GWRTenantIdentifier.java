package com.sanluna.multitenancy.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class GWRTenantIdentifier implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        if(TenantContext.getCurrentTenant() != null) {
            return TenantContext.getCurrentTenant();
        }
        return TenantContext.DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
