package br.edu.riogrande.tads.ppa.model;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewUser(
        @NotNull(message = "Nome não pode ser vazio") 
        String name,

        String handle,

        @NotNull(message = "Email não pode ser vazio") 
        @Email(message = "Email inválido") 
        String email,

        @NotNull(message = "Senha não pode ser vazia") 
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres") 
        String password,

        String company,
        Profile.AccountType type,
        List<String> roles
)  {

}
