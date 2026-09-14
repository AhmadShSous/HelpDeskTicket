package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.mySql.model.Role;

@Getter
@Setter
public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private boolean active;
}