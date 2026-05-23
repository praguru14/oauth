package org.example.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class OauthTokenService {

    @Autowired
    private OAuth2AuthorizedClientService auth2AuthorizedClientService;

    public String getAccessToen(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken oauth = (OAuth2AuthenticationToken) auth;
        OAuth2AuthorizedClient client = auth2AuthorizedClientService.loadAuthorizedClient(oauth.getAuthorizedClientRegistrationId(), oauth.getName());
    return client.getAccessToken().getTokenValue();
    }
}
