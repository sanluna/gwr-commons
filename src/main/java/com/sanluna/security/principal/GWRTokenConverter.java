package com.sanluna.security.principal;

import com.sanluna.security.GWRTokenHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GWRTokenConverter extends JwtAccessTokenConverter {

    public static final String GWR_ID = "GWR-id";
    public static final String GWR_USERNAME = "GWR-username";
    public static final String GWR_TENANT = "GWR-tenant";
    public static final String GWR_ROLES = "GWR-ROLES";

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        final OAuth2Authentication authentication = super.extractAuthentication(map);
        if (map.containsKey(GWR_USERNAME)) {
            String id = (String) map.get(GWR_ID);
            String username = (String) map.get(GWR_USERNAME);
            String tenant = (String) map.get(GWR_TENANT);
            ArrayList<String> roles = (ArrayList<String>) map.get(GWR_ROLES);
            StringBuilder buildRoles = new StringBuilder();
            roles.forEach(buildRoles::append);
            GWRPrincipal principal = new GWRPrincipal(id, username, buildRoles.toString(), tenant);
            authentication.setDetails(principal);
            GWRTokenHelper.setAdditionalInformation(map);
        }
        return authentication;
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return super.extractAccessToken(value, map);
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if ((accessToken instanceof DefaultOAuth2AccessToken) && (authentication.getPrincipal() instanceof GWRPrincipal)) {
            Map<String, Object> info = accessToken.getAdditionalInformation();
            if (info == null || info.isEmpty()) {
                info = new HashMap<>();
            }
            final GWRPrincipal principal = (GWRPrincipal) authentication.getPrincipal();
            info.put(GWR_ID, principal.getId());
            info.put(GWR_USERNAME, principal.getUsername());
            info.put(GWR_TENANT, principal.getTenant());
            info.put(GWR_ROLES, authentication.getUserAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        }
        return super.enhance(accessToken, authentication);
    }

}