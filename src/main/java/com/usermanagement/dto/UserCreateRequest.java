package com.usermanagement.dto;

import com.usermanagement.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserCreateRequest {
    @NotBlank(message = ("Le nom est obligatoire"))
    @Size(min = 2,max = 50,message = "Le nom doit contenir entre 2 et 50 caractère")
    private String name;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
    private String email;

    @NotBlank(message = "le mot de passe est obligatoire")
    @Size(min=6,message = "Le mot de passe doit contenir au moins 6 carcteres")
    private String password;

    @NotNull(message = "Le rôle est obligatoire")
    private Role role;
    public UserCreateRequest(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
