package org.example.oauth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.security.oauth2.client.registration.azure.client-id=test-client",
        "spring.security.oauth2.client.registration.azure.client-secret=test-secret",
        "spring.security.oauth2.client.provider.azure.issuer-uri=http://localhost:9000"
})
class OauthApplicationTests {

    @Test
    void contextLoads() {
    }

}
