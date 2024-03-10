package com.example.gitrepo.service;

import com.example.gitrepo.dto.RepoPopularity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RepoPopularityServiceTest {
    private RepoPopularityService repoPopularityService;
    @Mock
    private RestTemplate restTemplate;
    private final String githubApiUrl = "https://api.github.com/repos/{owner}/{repo}";
    private int popularityScoreThreshold = 500;
    private String accessToken = "demoAccessToken";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        repoPopularityService =
                new RepoPopularityService(restTemplate, githubApiUrl, popularityScoreThreshold, accessToken);
    }

    @Test
    void testIsPopularRepositoryWhenPopular() {
        String owner = "test-user";
        String repo = "test-repo";
        RepoPopularity popularity = new RepoPopularity();
        popularity.setStargazersCount(100);
        popularity.setForksCount(200);
        ResponseEntity<RepoPopularity> response = ResponseEntity.ok().body(popularity);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(RepoPopularity.class), anyString(), anyString()))
                .thenReturn(response);

        boolean isPopular = repoPopularityService.isPopularRepository(owner, repo);
        verify(restTemplate, times(1)).
                exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(RepoPopularity.class), anyString(), anyString());
        assertTrue(isPopular);
    }

    @Test
    void testIsPopularRepositoryWhenNotPopular() {
        String owner = "test-user";
        String repo = "test-repo";
        RepoPopularity popularity = new RepoPopularity();
        popularity.setStargazersCount(10);
        popularity.setForksCount(20);
        ResponseEntity<RepoPopularity> response = ResponseEntity.ok().body(popularity);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(RepoPopularity.class), anyString(), anyString()))
                .thenReturn(response);

        boolean isPopular = repoPopularityService.isPopularRepository(owner, repo);
        verify(restTemplate, times(1)).
                exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(RepoPopularity.class), anyString(), anyString());
        assertFalse(isPopular);
    }
}
