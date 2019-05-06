package com.sanluna.multitenancy.multitenancy.model;

import java.io.Serializable;

public class GWRDatabaseConnectionInfo implements Serializable {

    private String tenant;
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "DatabaseConnectionInfo{" +
                "driverClassName='" + driverClassName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getTenant() {
        return tenant;
    }

    public GWRDatabaseConnectionInfo setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String driverClassName;
        private String url;
        private String username;
        private String password;

        public Builder withDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public GWRDatabaseConnectionInfo build() {
            final GWRDatabaseConnectionInfo dbConnection = new GWRDatabaseConnectionInfo();

            dbConnection.setDriverClassName(this.driverClassName);
            dbConnection.setPassword(this.password);
            dbConnection.setUrl(this.url);
            dbConnection.setUsername(this.username);

            return dbConnection;

        }
    }

}
