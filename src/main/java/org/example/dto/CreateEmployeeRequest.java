package org.example.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.mySql.model.Role;

@Getter
@Setter
public class CreateEmployeeRequest {

    @NotBlank(message = "Name is require")
    private String name;

    @NotBlank(message = "Email is require")
    @Email(message = "Email is not teue")
    private String email;

    @NotNull(message = "Role is require")
    private Role role;
}