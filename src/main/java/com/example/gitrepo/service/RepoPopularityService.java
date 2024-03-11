package com.example.gitrepo.service;

import com.example.gitrepo.dto.PopularityDto;
import com.example.gitrepo.dto.RepoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RepoPopularityService {
    private final RestTemplate restTemplate;
    private final String githubApiUrl;
    private final int popularityScoreThreshold;
    private final String accessToken;

    public RepoPopularityService(RestTemplate restTemplate,
                              @Value("${github.api.url}") String githubApiUrl,
                              @Value("${popularity.score.threshold}") int popularityScoreThreshold,
                              @Value("${github.access.token}") String accessToken) {
        this.restTemplate = restTemplate;
        this.githubApiUrl = githubApiUrl;
        this.popularityScoreThreshold = popularityScoreThreshold;
        this.accessToken = accessToken;
    }

    public PopularityDto getGithubRepoPopularity(String owner, String repo) {
        log.info("Fetching repository popularity info with owner: {}: repo: {}", owner, repo);
        try {
            HttpHeaders headers = createHeaders(accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<RepoDto> response = restTemplate.exchange(
                    githubApiUrl,
                    HttpMethod.GET,
                    entity,
                    RepoDto.class,
                    owner,
                    repo
            );

            validateResponse(response);
            RepoDto popularity = response.getBody();
            log.info("Fetching repository popularity info: {}", popularity);
            int score = calculatePopularityScore(popularity);
            boolean popular = score >= popularityScoreThreshold;
            return new PopularityDto(owner, repo, score, popular);
        } catch (ResourceAccessException e) {
            log.error("Error checking repository popularity: {}", e.getMessage(), e);
            throw new ResourceAccessException("Connection timeout exception!");
        } catch (Exception e) {
            log.error("Error checking repository popularity: {}", e.getMessage(), e);
            throw new IllegalStateException("Could not able to fetch repo info!");
        }
    }

    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "application/vnd.github+json");
        headers.set("X-GitHub-Api-Version", "2022-11-28");
        return headers;
    }

    private void validateResponse(ResponseEntity<RepoDto> response) {
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            throw new IllegalStateException("GitHub API request failed with status: " + response.getStatusCode());
        }
        if (response.getBody() == null) {
            throw new IllegalStateException("GitHub API response body is null");
        }
    }

    private int calculatePopularityScore(RepoDto popularity) {
        return popularity.getStargazersCount() + popularity.getForksCount() * 2;
    }
}
