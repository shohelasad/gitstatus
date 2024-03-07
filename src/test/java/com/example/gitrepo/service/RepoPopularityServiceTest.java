package com.example.gitrepo.service;

import com.example.gitrepo.dto.RepoPopularity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RepoPopularityServiceTest {
    @Mock
    private RestTemplate restTemplate;

    private RepoPopularityService repoPopularityService;

    private final String githubApiUrl = "https://api.github.com/repos/{owner}/{repo}";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        repoPopularityService = new RepoPopularityService(restTemplate, githubApiUrl);
    }

    @Test
    void testIsPopularRepositoryWhenPopular() {
        String owner = "test-user";
        String repo = "test-repo";
        RepoPopularity popularity = new RepoPopularity();
        popularity.setStargazersCount(100);
        popularity.setForksCount(50);
        when(restTemplate.getForObject(anyString(), eq(RepoPopularity.class), anyString(), anyString()))
                .thenReturn(popularity);

        boolean isPopular = repoPopularityService.isPopularRepository(owner, repo);
        verify(restTemplate, times(1)).
                getForObject(anyString(), eq(RepoPopularity.class), anyString(), anyString());
        assertTrue(isPopular);
    }
}
