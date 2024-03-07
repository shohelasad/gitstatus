package com.example.gitrepo.service;

import com.example.gitrepo.dto.RepoPopularity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RepoPopularityService {
    @Value("${popularity.score.threshold}")
    private int popularityScoreThreshold;
    private final RestTemplate restTemplate;
    private final String githubApiUrl;

    public RepoPopularityService(RestTemplate restTemplate, @Value("${github.api.url}") String githubApiUrl) {
        this.restTemplate = restTemplate;
        this.githubApiUrl = githubApiUrl;
    }

    public boolean isPopularRepository(String owner, String repo) {
        RepoPopularity repository = fetchRepoPopularity( owner,  repo);
        int score = repository.getStargazersCount() + repository.getForksCount() * 2;
        return score >= popularityScoreThreshold;
    }

    private RepoPopularity fetchRepoPopularity(String owner, String repo) {
        return restTemplate.getForObject(githubApiUrl, RepoPopularity.class, owner, repo);
    }
}
