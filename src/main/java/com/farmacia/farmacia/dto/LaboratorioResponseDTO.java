package com.farmacia.farmacia.dto;

import com.farmacia.farmacia.model.Laboratorio;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LaboratorioResponseDTO {

    private Long id;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private Boolean status;

    public LaboratorioResponseDTO(Laboratorio lab) {
        this.id = lab.getId();
        this.cnpj = lab.getCnpj();
        this.razaoSocial = lab.getRazaoSocial();
        this.nomeFantasia = lab.getNomeFantasia();
        this.status = lab.getStatus();
    }
}