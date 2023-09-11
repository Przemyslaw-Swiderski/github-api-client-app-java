package com.githubapiclientdemoapp.service;

import com.githubapiclientdemoapp.controller.dto.RepositoryDto;
import com.githubapiclientdemoapp.mapper.RepositoryMapper;

import com.githubapiclientdemoapp.service.model.Branch;
import com.githubapiclientdemoapp.service.model.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubRepositoryService {

    private final WebClient webClient;

    private final RepositoryMapper repositoryMapper;

    public GitHubRepositoryService(WebClient webClient, RepositoryMapper repositoryMapper) {
        this.webClient = webClient;
        this.repositoryMapper = repositoryMapper;
    }

    public List<RepositoryDto> getRepositories(String userName, String acceptHeader) {
        List<Repository> repos = webClient.get()
                .uri("/users/{userName}/repos", userName)
                .header("X-GitHub-Api-Version", "2022-11-28")
                .header("Accept", acceptHeader)
                .retrieve()
                .bodyToFlux(Repository.class)
                .collectList()
                .block();

        repos = repos.stream()
                .filter(repo -> !repo.isFork())
                .collect(Collectors.toList());


        for (Repository repo : repos) {
            List<Branch> branches = getBranches(userName, repo.getName());
            repo.setBranches(branches);
        }

        return repos.stream()
                .map(r -> repositoryMapper.mapRepositoryToDto(r)).toList();



    }

    private List<Branch> getBranches(String userName, String repoName) {
        return webClient.get()
                .uri("/repos/{userName}/{repoName}/branches", userName, repoName)
                .retrieve()
                .bodyToFlux(Branch.class)
                .collectList()
                .block();
    }

}
