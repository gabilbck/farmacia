package com.farmacia.farmacia.repository;

import com.farmacia.farmacia.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    @Query("select m from Medicamento m left join fetch m.laboratorio")
    List<Medicamento> findAllWithLaboratorio();

    boolean existsByLaboratorio_Id(Long laboratorioId);
}
