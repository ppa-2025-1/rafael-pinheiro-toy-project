package br.edu.riogrande.tads.ppa.model;

import java.util.List;
import jakarta.validation.constraints.NotNull;

public record NewUser(
        String name,
        String handle,
        @NotNull(message = "O e-mail é obrigatorio")
        @NotBlank(message = "O e-mail não deve estar vazio")
        String email,
        @NotNull(message = "A senha é obrigatorio")
        @NotBlank(message = "A senha não deve estar vazio")
        String password,
        String company,
        Profile.AccountType type,
        List<String> roles
)  {

}
