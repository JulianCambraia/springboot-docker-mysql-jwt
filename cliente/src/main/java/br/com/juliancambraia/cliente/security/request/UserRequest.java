package br.com.juliancambraia.cliente.security.request;

import br.com.juliancambraia.cliente.security.enums.PerfilEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotEmpty(message = "Campo Email é Obrigatório.")
    private String email;

    @NotEmpty(message = "Campo Password é obrigatório.")
    private String password;

    @Enumerated(EnumType.STRING)
    private PerfilEnum perfil;
}
