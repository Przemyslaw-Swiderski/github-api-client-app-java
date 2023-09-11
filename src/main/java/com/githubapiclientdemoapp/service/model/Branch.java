package com.githubapiclientdemoapp.service.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    private String name;
    private Commit commit;
}