package com.example.gitrepo.controller;

import com.example.gitrepo.dto.PopularityDto;
import com.example.gitrepo.service.RepoPopularityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/repos")
@RestController
public class RepoPopularityController {

    private final RepoPopularityService popularityService;

    @GetMapping("/{owner}/{repo}/popularity")
    public ResponseEntity<PopularityDto> getRepoPopularity(@PathVariable String owner, @PathVariable String repo) {
        PopularityDto popularityDto = popularityService.getGithubRepoPopularity(owner, repo);
        return ResponseEntity.ok(popularityDto);
    }
}
