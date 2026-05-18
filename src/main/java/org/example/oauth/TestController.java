package org.example.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

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

    // Protected page
    @GetMapping("/dashboard")
    public String dashboard(

    ) {
final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object username =
                authentication.getPrincipal();

        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();

        String user = oidcUser.getAttribute("preferred_username");

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

                        async function loadApi(url) {

                            const response =
                                await fetch(url);

                            const text =
                                await response.text();

                            document.getElementById('result')
                                .innerText = text;
                        }

                    </script>

                </body>

                </html>
                """.formatted(user);
    }

    @GetMapping("/api1")
    public String api1() {

        return "Protected API 1 called successfully";
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