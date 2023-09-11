package com.githubapiclientdemoapp.service.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repository {
    private String name;
    private Owner owner;
    private boolean fork;
    private List<Branch> branches;



    public boolean isFork() {
        return fork;
    }

}
