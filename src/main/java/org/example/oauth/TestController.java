package org.example.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    
    @Autowired
    OauthTokenService oauthTokenService;

    // Public page
    @GetMapping("/")
    public String home() {

        
        return """
                <html>

                <body>

                    <h1>OAuth Demo</h1>

                    <a href='/dashboard'>
                        Login with Keycloak
                    </a>

                </body>

                </html>
                """;
    }

    @GetMapping("/dashboard")
    public String dashboard() {

        final Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        OidcUser oidcUser =
                (OidcUser) authentication.getPrincipal();

        String user =
                oidcUser.getAttribute("preferred_username");

        String accessToken =
                oauthTokenService.getAccessToen();

        return """
            <html>

            <body>

                <h1>You are authenticated</h1>

                <h2>Welcome %s</h2>

                <br>

                <button onclick="loadApi('/api1')">
                    Load API 1
                </button>

                <button onclick="loadApi('/api2')">
                    Load API 2
                </button>

                <button onclick="loadApi('/api3')">
                    Load API 3
                </button>

                <br><br>

                <pre id='result'></pre>

                <script>

                    const accessToken = '%s';

                    async function loadApi(url) {

                        const response = await fetch(url, {

                            method: 'GET',

                            headers: {
                                'Authorization':
                                    'Bearer ' + accessToken
                            }

                        });

                        const text =
                            await response.text();

                        document.getElementById('result')
                            .innerText = text;
                    }

                </script>

            </body>

            </html>
            """.formatted(user, accessToken);
    }

    @GetMapping("/api1")
    public String api1(Authentication authentication) {

        JwtAuthenticationToken token =
                (JwtAuthenticationToken) authentication;

        Jwt jwt = token.getToken();

        List<String> roles =
                jwt.getClaimAsStringList("groups");

        if (roles != null && roles.contains("ADMIN")) {

            return "Welcome ADMIN";
        }

        return "Access Denied";
    }

    @GetMapping("/api2")
    public String api2() {

        return "Protected API 2 called successfully";
    }

    @GetMapping("/api3")
    public String api3() {

        return "Protected API 3 called successfully";
    }
}