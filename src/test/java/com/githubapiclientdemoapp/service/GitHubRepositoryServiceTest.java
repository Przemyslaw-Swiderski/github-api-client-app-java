package com.githubapiclientdemoapp.service;

import com.githubapiclientdemoapp.controller.dto.BranchDto;
import com.githubapiclientdemoapp.controller.dto.RepositoryDto;
import com.githubapiclientdemoapp.mapper.RepositoryMapper;
import com.githubapiclientdemoapp.service.model.Repository;
import com.githubapiclientdemoapp.service.model.Branch;
import com.githubapiclientdemoapp.service.model.Commit;
import com.githubapiclientdemoapp.service.model.Owner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


class GitHubRepositoryServiceTest {

    private static final String TEST_REPO_NAME = "test-repo";
    private static final String TEST_USER_NAME = "test-user";
    private static final String TEST_BRANCH_NAME = "test-branch";
    private static final String TEST_COMMIT_SHA = "test-sha";

    private final RepositoryMapper repositoryMapper = Mockito.mock(RepositoryMapper.class);
    private final ExchangeFunction exchangeFunction = Mockito.mock(ExchangeFunction.class);
    private final WebClient webClient = WebClient.builder().exchangeFunction(exchangeFunction).build();

    private final GitHubRepositoryService sut = new GitHubRepositoryService(webClient, repositoryMapper);

    @Test
    void shouldReturnUserRepositories() {
        //given
        Mockito.when(exchangeFunction.exchange(Mockito.any(ClientRequest.class)))
                .thenReturn(mockRepositoryHttpClientResponse(), mockBranchHttpClientResponse());
        Mockito.when(repositoryMapper.mapRepositoryToDto(getGitHubRepository()))
                .thenReturn(getExpectedRepositoryDto());

        //when
        List<RepositoryDto> actual = sut.getRepositories(TEST_USER_NAME, "application/json");

        //then
        Assertions.assertThat(actual).isEqualTo(List.of(getExpectedRepositoryDto()));
    }

    private static Mono<ClientResponse> mockRepositoryHttpClientResponse() {
        return Mono.just(ClientResponse.create(HttpStatus.OK)
                .header("content-type", "application/json")
                .body("""
                           [
                             {
                               "name": "%s",
                               "owner": {
                               "login": "%s"
                             },
                             "fork": false
                            }
                          ]
                        """.formatted(TEST_REPO_NAME, TEST_USER_NAME))
                .build());
    }

    private static Mono<ClientResponse> mockBranchHttpClientResponse() {
        return Mono.just(ClientResponse.create(HttpStatus.OK)
                .header("content-type", "application/json")
                .body("""
                          [
                            {
                              "name": "%s",
                              "commit": {
                                "sha": "%s"
                              }
                            }
                          ]
                        """.formatted(TEST_BRANCH_NAME, TEST_COMMIT_SHA))
                .build());
    }

    private static Repository getGitHubRepository() {
        return new Repository(
                TEST_REPO_NAME,
                new Owner(TEST_USER_NAME),
                false,
                List.of(
                        new Branch(TEST_BRANCH_NAME, new Commit(TEST_COMMIT_SHA))
                )
        );
    }

    private static RepositoryDto getExpectedRepositoryDto() {
        return new RepositoryDto(
                TEST_REPO_NAME,
                TEST_USER_NAME,
                List.of(
                        new BranchDto(TEST_BRANCH_NAME, TEST_COMMIT_SHA)
                )
        );
    }
}