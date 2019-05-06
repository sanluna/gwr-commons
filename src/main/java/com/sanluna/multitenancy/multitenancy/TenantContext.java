package com.sanluna.multitenancy.multitenancy;

public class TenantContext {

    public final static String DEFAULT_TENANT = "gwr-default-tenant";

    public final static String TENANT_HEADER = "tenantId";

    private static ThreadLocal<String> currentTenant = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }

}
