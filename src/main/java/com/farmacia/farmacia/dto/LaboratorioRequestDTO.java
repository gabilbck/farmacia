package com.farmacia.farmacia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LaboratorioRequestDTO {

    @NotBlank(message = "cnpj é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "cnpj deve conter somente números e exatamente 14 dígitos")
    private String cnpj;
    @NotBlank(message = "razaoSocial é obrigatória")
    private String razaoSocial;
    @NotBlank(message = "nomeFantasia é obrigatório")
    private String nomeFantasia;
    @NotNull(message = "status é obrigatório")
    private Boolean status;
}