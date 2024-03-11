package com.example.gitrepo.controller;

import com.example.gitrepo.dto.PopularityDto;
import com.example.gitrepo.service.RepoPopularityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/repos")
@RestController
public class RepoPopularityController {

    private final RepoPopularityService popularityService;

    @Operation(summary = "Get repository popularity by owner and repo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved repository popularity"),
            @ApiResponse(responseCode = "404", description = "Repository not found")
    })
    @GetMapping("/{owner}/{repo}/popularity")
    public ResponseEntity<PopularityDto> getRepoPopularity(
        @PathVariable String owner,
        @PathVariable String repo
    ) {
        log.info("Get github repo popularity info with owner: {} and repo: {}", owner, repo);
        PopularityDto popularityDto = popularityService.getGithubRepoPopularity(owner, repo);
        return ResponseEntity.ok(popularityDto);
    }
}
