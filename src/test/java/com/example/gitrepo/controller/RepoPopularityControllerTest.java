package com.example.gitrepo.controller;

import com.example.gitrepo.dto.PopularityDto;
import com.example.gitrepo.service.RepoPopularityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@WebMvcTest(controllers = RepoPopularityController.class)
class RepoPopularityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepoPopularityService repoPopularityService;

    @Test
    void testGetRepoPopularity() throws Exception {
        String owner = "test-user";
        String repo = "test-repo";
        PopularityDto popularityDto = new PopularityDto(owner, repo, 500, true);
        when(repoPopularityService.getGithubRepoPopularity(anyString(), anyString())).thenReturn(popularityDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/repos/{owner}/{repo}/popularity", owner, repo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(owner))
                .andExpect(MockMvcResultMatchers.jsonPath("$.repo").value(repo));
    }
}
