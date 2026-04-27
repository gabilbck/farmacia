// Laboratorio.java
package com.farmacia.farmacia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "laboratorio")
@Getter
@Setter
@NoArgsConstructor
public class Laboratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_lab")
    private Long id;

    @Column(name = "cnpj_lab")
    private String cnpj;

    @Column(name = "razao_social_lab")
    private String razaoSocial;

    @Column(name = "nome_fantasia_lab")
    private String nomeFantasia;

    @Column(name = "status_lab")
    private Boolean status;
}