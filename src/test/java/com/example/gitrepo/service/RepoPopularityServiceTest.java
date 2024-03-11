package com.example.gitrepo.service;

import com.example.gitrepo.dto.PopularityDto;
import com.example.gitrepo.dto.RepoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class RepoPopularityServiceTest {
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
    void testGetRepoPopularityWhenPopular() {
        String owner = "test-user";
        String repo = "test-repo";
        RepoDto popularity = new RepoDto();
        popularity.setStargazersCount(100);
        popularity.setForksCount(200);
        ResponseEntity<RepoDto> response = ResponseEntity.ok().body(popularity);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(RepoDto.class), anyString(), anyString()))
                .thenReturn(response);

        PopularityDto popularityDto = repoPopularityService.getGithubRepoPopularity(owner, repo);
        verify(restTemplate, times(1)).
                exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(RepoDto.class), anyString(), anyString());
        assertTrue(popularityDto.popular());
    }

    @Test
    void testGetRepoPopularityWhenNotPopular() {
        String owner = "test-user";
        String repo = "test-repo";
        RepoDto popularity = new RepoDto();
        popularity.setStargazersCount(10);
        popularity.setForksCount(20);
        ResponseEntity<RepoDto> response = ResponseEntity.ok().body(popularity);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(RepoDto.class),
                anyString(),
                anyString())).thenReturn(response);

        PopularityDto popularityDto = repoPopularityService.getGithubRepoPopularity(owner, repo);
        verify(restTemplate, times(1)).
                exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(RepoDto.class), anyString(), anyString());
        assertFalse(popularityDto.popular());
    }

    @Test
    void testTimeoutException() throws Exception {
        String owner = "someOwner";
        String repo = "someRepo";

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(RepoDto.class),
                anyString(),
                anyString())).thenThrow(new ResourceAccessException("Connection timeout exception!"));

        assertThrows(ResourceAccessException.class, () -> {
            repoPopularityService.getGithubRepoPopularity(owner, repo);
        }, "Connection timeout exception!");
    }
}
