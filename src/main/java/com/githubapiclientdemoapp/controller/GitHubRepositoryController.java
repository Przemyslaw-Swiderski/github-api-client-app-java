package com.githubapiclientdemoapp.controller;

import com.githubapiclientdemoapp.service.GitHubRepositoryService;
import com.githubapiclientdemoapp.controller.dto.RepositoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v3/github/users/{userName}/repos")
public class GitHubRepositoryController {


    private final GitHubRepositoryService gitHubRepositoryService;

    public GitHubRepositoryController(GitHubRepositoryService gitHubRepositoryService) {
        this.gitHubRepositoryService = gitHubRepositoryService;
    }


    @GetMapping
    public List<RepositoryDto> getRepos(@PathVariable String userName,
                                        @RequestHeader("Accept") String acceptHeader) {
        return gitHubRepositoryService.getRepositories(userName, acceptHeader);
    }

    @RestControllerAdvice
    public static class GitHubControllerAdvice {

        @ExceptionHandler(WebClientException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public HashMap<String, String> handleGithubRepoNotFound(WebClientException e) {
            log.warn("Requested GitHub user not found - returning HTTP 404", e);
            return buildErrorResponse(HttpStatus.NOT_FOUND, "Requested user not found in GitHub");
        }

        private HashMap<String, String> buildErrorResponse(HttpStatus httpStatus, String msg) {
            HashMap<String, String> response = new HashMap<>();
            response.put("message", msg);
            response.put("status", httpStatus.toString());
            return response;
        }
    }
}
