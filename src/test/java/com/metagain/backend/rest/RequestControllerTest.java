package com.metagain.backend.rest;

import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Request;
import com.metagain.backend.model.types.RequestType;
import com.metagain.backend.repository.ProfileRepository;
import com.metagain.backend.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import static com.metagain.backend.model.types.RequestType.MEET;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RequestControllerTest {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RequestRepository requestRepository;

    @BeforeEach
    void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void shouldAcceptRequest() {
        // given: Two profiles and a meeting request
        Profile fromProfile = createProfile("foo@example.com", "foo", "Garfield", "Yolo", createDelegatingPasswordEncoder().encode("xxx"));
        Profile toProfile = createProfile("bla@example.com", "bla", "Papa", "Schlumpf", createDelegatingPasswordEncoder().encode("yyy"));
        Profile persistedFromProfile = profileRepository.save(fromProfile);
        Profile persistedToProfile = profileRepository.save(toProfile);
        Request request = createRequest(persistedFromProfile, persistedToProfile, MEET);
        requestRepository.save(request);

        // when: Accept Meeting Request
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("bla", "yyy")
                .exchange("http://localhost:" + port + "/metagain/requests/{id}", HttpMethod.PATCH, null, String.class, request.getId());

        // then: No Exception - all good
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    private static Request createRequest(Profile fromProfile, Profile toProfile, RequestType requestType) {
        Request request = new Request();
        request.setFromProfile(fromProfile);
        request.setToProfile(toProfile);
        request.setRequestType(requestType);
        return request;
    }

    private static Profile createProfile(String email, String username, String firstName, String lastName, String passwordHash) {
        Profile profile1 = new Profile();
        profile1.setEmail(email);
        profile1.setUsername(username);
        profile1.setFirstName(firstName);
        profile1.setLastName(lastName);
        profile1.setPasswordHash(passwordHash);
        return profile1;
    }

}