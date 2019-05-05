package com.sanluna.security;

import com.sanluna.security.principal.GWRPrincipal;
import com.sanluna.security.principal.GWRTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

public class GWRTokenHelper {

    @Value("${security.oauth2.resource.jwt.key-value}")
    private String key;

    @Autowired
    private OAuth2ClientContext context;

    private static ThreadLocal<Map<String, ?>> additionalInformation = new ThreadLocal<>();

    private static JwtAccessTokenConverter tokenConverter = new GWRTokenConverter();

    public GWRTokenHelper() {
        tokenConverter = new GWRTokenConverter();
        tokenConverter.setVerifierKey(key);
    }

    private static OAuth2AccessToken getToken() {
        return tokenConverter.extractAccessToken(getDetails().getTokenValue(), additionalInformation.get());
    }

    private static OAuth2AuthenticationDetails getDetails() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Authentication oauth = SecurityContextHolder.getContext().getAuthentication();
            return (OAuth2AuthenticationDetails) oauth.getDetails();
        }
        throw new AuthenticationCredentialsNotFoundException("unable to retrieve principal!");
    }

    public static GWRPrincipal getPrincipal() {
        return (GWRPrincipal) getDetails().getDecodedDetails();
    }

    public static OAuth2AccessToken getAccessToken(OAuth2RestOperations restTemplate) {
        OAuth2AccessToken token = null;
        try {
            token = getToken();
        } catch (Exception e) {
            System.out.println("no authentication found. getting internal auth");
        }
        if (token == null || token.isExpired()) {
            token = restTemplate.getAccessToken();
        }
        return token;
    }


    public static String currentUserId() {
        return getPrincipal().getId();
    }

    public static String currentUsername() {
        return getPrincipal().getUsername();
    }

    public static String currentTenant() {
        return getPrincipal().getTenant();
    }

    public static ThreadLocal<Map<String, ?>> getAdditionalInformation() {
        return additionalInformation;
    }

    public static void setAdditionalInformation(Map<String, ?> info) {
        additionalInformation.set(info);
    }
}
