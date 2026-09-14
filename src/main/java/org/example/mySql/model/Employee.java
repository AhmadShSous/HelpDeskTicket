package org.example.mySql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message  = "Emp name is requiered")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "email is required")
    @Email(message = "email not true")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
