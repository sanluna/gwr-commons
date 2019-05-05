package com.sanluna.multitenancy.communication;

import com.sanluna.multitenancy.multitenancy.TenantContext;
import com.sanluna.security.GWRTokenHelper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class InterServiceCommunication {

    public static <T> T GETWithAuth(String url, Class<T> classInfo, OAuth2RestOperations restTemplate) {
        OAuth2AccessToken token = GWRTokenHelper.getAccessToken(restTemplate);
        return sendGETRequest(url, authHeaders(token), classInfo, restTemplate);
    }

    public static <T> T GETNoAuth(String url, Class<T> classInfo, OAuth2RestOperations restTemplate) {
        return sendGETRequest(url, getBaseHeaders(), classInfo, restTemplate);
    }

    private static <T> T sendGETRequest(String url, HttpHeaders headers, Class<T> classInfo, OAuth2RestOperations restTemplate) {
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(headers), classInfo).getBody();
    }

    private static HttpHeaders getBaseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        headers.set("tenantId", TenantContext.getCurrentTenant());
        return headers;
    }

    private static HttpHeaders authHeaders(OAuth2AccessToken token) {
        HttpHeaders headers = getBaseHeaders();
        headers.set("Authorization", "bearer " + token.getValue());
        return headers;
    }

}
