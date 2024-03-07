package com.example.gitrepo.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RepoPopularityServiceTest {

    @Mock
    private RepoPopularityService repoPopularityService;


    @Test
    void testIsPopularRepositoryWhenPopular() {
        String owner = "test-user";
        String repo = "test-repo";
        boolean isPopular = repoPopularityService.isPopularRepository(owner, repo);
        assertTrue(isPopular);
    }

}
