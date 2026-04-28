package com.farmacia.farmacia.dto;

import com.farmacia.farmacia.enums.Categoria;
import com.farmacia.farmacia.enums.Tarja;
import com.farmacia.farmacia.enums.FormaFarmaceutica;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Requisições de dados
@Getter
@Setter
@NoArgsConstructor
public class MedicamentoRequestDTO {

    @NotBlank(message = "ean é obrigatório")
    @Size(min = 8, max = 14, message = "ean deve ter entre 8 e 14 caracteres")
    @Pattern(regexp = "^\\d{8,14}$", message = "ean deve conter apenas números (8 a 14 dígitos)")
    private String ean;

    @NotBlank(message = "nome é obrigatório")
    private String nome;

    @NotBlank(message = "dosagemValor é obrigatório")
    @Pattern(regexp = "^\\d+(?:[\\.,]\\d+)?$", message = "dosagemValor deve conter apenas números")
    private String dosagemValor;

    @NotBlank(message = "dosagemUM é obrigatório")
    private String dosagemUM;

    @NotNull(message = "categoria é obrigatória")
    private Categoria categoria;

    @NotBlank(message = "classeTerapeutica é obrigatória")
    private String classeTerapeutica;

    @NotNull(message = "formaFarmaceutica é obrigatória")
    private FormaFarmaceutica formaFarmaceutica;

    @NotNull(message = "prescricao é obrigatória")
    private Boolean prescricao;

    @NotNull(message = "tarja é obrigatória")
    private Tarja tarja;

    @NotNull(message = "anvisaRegular é obrigatório")
    private Boolean anvisaRegular;

    @NotNull(message = "pfp é obrigatório")
    private Boolean pfp;

    @NotNull(message = "precoVenda é obrigatório")
    @PositiveOrZero(message = "precoVenda deve ser maior ou igual a zero")
    private Double precoVenda;

    @NotNull(message = "status é obrigatório")
    private Boolean status;

    private String observacoes;

    /** Opcional: omita ou use null para gravar sem laboratório. O banco só é consultado se o valor for maior que zero. */
    private Long laboratorioId;

}