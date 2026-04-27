package com.farmacia.farmacia.model;

import com.farmacia.farmacia.enums.Categoria;
import com.farmacia.farmacia.enums.Tarja;
import com.farmacia.farmacia.enums.FormaFarmaceutica;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medicamento")
@Getter
@Setter
@NoArgsConstructor
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_med")
    private Long id;

    @Column(name = "ean_med")
    private String ean;

    @Column(name = "nome_med")
    private String nome;

    @Column(name = "dosagem_valor_med")
    private String dosagemValor;

    @Column(name = "dosagem_um_med")            //ex: mg, ml...
    private String dosagemUM;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_med")             //ex: genérico, similar, referência
    private Categoria categoria;

    @Column(name = "classe_terapeutica_med")    //ex: anti-inflamatório, analgésico...
    private String classeTerapeutica;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_farmaceutica_med")    //ex: comprimido, cápsula...
    private FormaFarmaceutica formaFarmaceutica;

    @Column(name = "prescricao_med")
    private Boolean prescricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tarja_med")                 //ex: sem tarja, tarja preta...
    private Tarja tarja;

    @Column(name = "anvisa_regular_med")
    private Boolean anvisaRegular;

    @Column(name = "pfp_med")
    private Boolean pfp;

    @Column(name = "preco_venda_med")
    private Double precoVenda;

    @Column(name = "status_med")
    private Boolean status;

    @Column(name = "observacoes_med")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_lab")               // FK que já existe no banco
    private Laboratorio laboratorio;

}