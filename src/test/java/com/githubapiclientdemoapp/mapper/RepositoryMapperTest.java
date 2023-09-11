package com.githubapiclientdemoapp.mapper;

import com.githubapiclientdemoapp.controller.dto.BranchDto;
import com.githubapiclientdemoapp.controller.dto.RepositoryDto;
import com.githubapiclientdemoapp.service.model.Repository;
import com.githubapiclientdemoapp.service.model.Branch;
import com.githubapiclientdemoapp.service.model.Commit;
import com.githubapiclientdemoapp.service.model.Owner;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;



class RepositoryMapperTest {

    private  RepositoryMapper repositoryMapper = new RepositoryMapper();
    @Test
    void shouldMapToDto() {
        //given:
        Repository repository = getRepository();

        //when:
        RepositoryDto actual = repositoryMapper.mapRepositoryToDto(repository);

        //then:
        RepositoryDto expected = getExpected();
        Assertions.assertThat(actual).isEqualTo(expected); //record ma equalsa zimplementowanego

    }




    private static RepositoryDto getExpected() {
        return new RepositoryDto(
                "TEST_NAME",
                "TEST_LOGIN",
                List.of(
                        new BranchDto("TEST_B1", "rsfaf252151efsdfvgq"),
                        new BranchDto("TEST_B2", "safsa43"),
                        new BranchDto("TEST_B3", "puyra43")
                )
        );
    }

    private static Repository getRepository() {
        return new Repository(
                "TEST_NAME",
                new Owner("TEST_LOGIN"),
                false,
                List.of(
                        new Branch("TEST_B1", new Commit("rsfaf252151efsdfvgq")),
                        new Branch("TEST_B2", new Commit("safsa43")),
                        new Branch("TEST_B3", new Commit("puyra43"))
                )
        );
    }
}