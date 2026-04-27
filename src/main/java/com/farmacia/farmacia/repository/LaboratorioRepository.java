// LaboratorioRepository.java
package com.farmacia.farmacia.repository;

import com.farmacia.farmacia.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {
}