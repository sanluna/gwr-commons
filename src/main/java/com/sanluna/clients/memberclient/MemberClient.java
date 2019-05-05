package com.sanluna.clients.memberclient;

import com.sanluna.clients.memberclient.model.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;

import java.util.Arrays;
import java.util.List;

import static com.sanluna.multitenancy.communication.InterServiceCommunication.GETWithAuth;


public class MemberClient {
    @Value("${gwr.member.url:http://localhost:18101/memberservice/members/}")
    private String MEMBER_URL;

    @Autowired
    private OAuth2RestOperations restTemplate;

    public List<MemberDTO> getMembers(String tenantId) {
        String url = MEMBER_URL;
        return Arrays.asList(GETWithAuth(url, MemberDTO[].class, restTemplate));
    }

    public MemberDTO getMember(String username) {
        return GETWithAuth(MEMBER_URL + "login/" + username, MemberDTO.class, restTemplate);
    }


}
