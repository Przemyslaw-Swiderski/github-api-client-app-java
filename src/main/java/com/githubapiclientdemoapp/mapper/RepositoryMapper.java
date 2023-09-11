package com.githubapiclientdemoapp.mapper;

import com.githubapiclientdemoapp.service.model.Repository;
import com.githubapiclientdemoapp.controller.dto.BranchDto;
import com.githubapiclientdemoapp.controller.dto.RepositoryDto;
import org.springframework.stereotype.Component;


@Component
public class RepositoryMapper {

    public RepositoryDto mapRepositoryToDto(Repository gitHubRepo) {
        return new RepositoryDto(
                gitHubRepo.getName(),
                gitHubRepo.getOwner().getLogin(),
                gitHubRepo.getBranches().stream()
                        .map(b -> new BranchDto(b.getName(), b.getCommit().getSha()))
                        .toList()
        );
    }
}


