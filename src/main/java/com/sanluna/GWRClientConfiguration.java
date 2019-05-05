package com.sanluna;

import com.sanluna.clients.tenantclient.GWRTenantClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.sanluna.clients"})
public class GWRClientConfiguration {

    @Bean
    @ConditionalOnMissingBean(GWRTenantClient.class)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public GWRTenantClient tenantClient() {
        return new GWRTenantClient();
    }

    @Value("${gwr.oauth2.access-token-uri}")
    private String tokenUrl;

    @Value("${gwr.oauth2.client-id}")
    private String clientId;

    @Value("${gwr.oauth2.client-secret}")
    private String clientSecret;

    @Bean
    protected OAuth2ProtectedResourceDetails resource() {

        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();

        resource.setAccessTokenUri(tokenUrl);
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setClientAuthenticationScheme(AuthenticationScheme.header);

        return resource;
    }

    @Bean
    @Primary
    public OAuth2RestOperations restTemplate(OAuth2ClientContext context) {
        return new OAuth2RestTemplate(resource(), context);
    }

}
