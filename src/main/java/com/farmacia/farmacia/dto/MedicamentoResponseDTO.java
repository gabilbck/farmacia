// MedicamentoResponseDTO.java
package com.farmacia.farmacia.dto;

import com.farmacia.farmacia.enums.Categoria;
import com.farmacia.farmacia.enums.Tarja;
import com.farmacia.farmacia.enums.FormaFarmaceutica;
import com.farmacia.farmacia.model.Medicamento;
import lombok.Getter;

@Getter
public class MedicamentoResponseDTO {

    private Long        id;
    private String ean;
    private String nome;
    private String dosagemValor;
    private String dosagemUM;
    private Categoria categoria;
    private String classeTerapeutica;
    private Boolean prescricao;
    private Tarja tarja;
    private FormaFarmaceutica formaFarmaceutica;
    private Boolean anvisaRegular;
    private Boolean pfp;
    private Double precoVenda;
    private Boolean status;
    private String observacoes;

    // Dados achatados do laboratório — sem expor a entidade inteira
    private Long laboratorioId;
    private String laboratorioRazaoSocial;
    private String laboratorioNomeFantasia;

    // Construtor que converte Medicamento → DTO
    public MedicamentoResponseDTO(Medicamento m) {
        this.id = m.getId();
        this.ean = m.getEan();
        this.nome = m.getNome();
        this.dosagemValor = m.getDosagemValor();
        this.dosagemUM = m.getDosagemUM();
        this.categoria = m.getCategoria();
        this.classeTerapeutica = m.getClasseTerapeutica();
        this.prescricao = m.getPrescricao();
        this.tarja = m.getTarja();
        this.formaFarmaceutica = m.getFormaFarmaceutica();
        this.anvisaRegular = m.getAnvisaRegular();
        this.pfp = m.getPfp();
        this.precoVenda = m.getPrecoVenda();
        this.status = m.getStatus();
        this.observacoes = m.getObservacoes();

        try {
            if (m.getLaboratorio() != null) {
                this.laboratorioId = m.getLaboratorio().getId();
                this.laboratorioRazaoSocial = m.getLaboratorio().getRazaoSocial();
                this.laboratorioNomeFantasia = m.getLaboratorio().getNomeFantasia();
            }
        } catch (Exception ignored) {
            // Evita quebrar a listagem inteira se houver FK inconsistente ou proxy inválido.
            this.laboratorioId = null;
            this.laboratorioRazaoSocial = null;
            this.laboratorioNomeFantasia = null;
        }
    }
}
