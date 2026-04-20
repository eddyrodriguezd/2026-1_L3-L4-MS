package pe.edu.pucp.microservices.accounts.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pe.edu.pucp.microservices.accounts.dto.UserDto;
import tools.jackson.databind.ObjectMapper;

@Repository
public class UserClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${user.api.url}")
    private String usersApiUrl;
    @Value("${user.api.key}")
    private String usersApiKey;

    public UserClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public UserDto getUser(String userId) {
        String url = usersApiUrl + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", usersApiKey);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        String responseBody = response.getBody();
        return objectMapper.readValue(responseBody, UserDto.class);
    }
}
