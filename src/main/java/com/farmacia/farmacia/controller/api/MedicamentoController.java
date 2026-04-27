// MedicamentoController.java
package com.farmacia.farmacia.controller.api;

import com.farmacia.farmacia.model.Laboratorio;
import com.farmacia.farmacia.repository.LaboratorioRepository;
import com.farmacia.farmacia.model.Medicamento;
import com.farmacia.farmacia.repository.MedicamentoRepository;
import com.farmacia.farmacia.dto.MedicamentoRequestDTO;
import com.farmacia.farmacia.dto.MedicamentoResponseDTO;
import com.farmacia.farmacia.exception.RecursoNaoEncontradoException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@CrossOrigin(origins = "*")
public class MedicamentoController {

    private final MedicamentoRepository repository;
    private final LaboratorioRepository laboratorioRepository;

    public MedicamentoController(
            MedicamentoRepository repository,
            LaboratorioRepository laboratorioRepository) {
        this.repository = repository;
        this.laboratorioRepository = laboratorioRepository;
    }

    private Medicamento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Medicamento não encontrado com ID: " + id));
    }

    private Laboratorio buscarLab(Long labId) {
        if (labId == null) return null;
        return laboratorioRepository.findById(labId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Laboratório não encontrado com ID: " + labId));
    }

    /** Preenche a entidade Medicamento a partir do DTO de requisição */
    private void aplicarDTO(Medicamento med, MedicamentoRequestDTO dto) {
        if (dto.getEan() != null) med.setEan(dto.getEan());
        if (dto.getNome() != null) med.setNome(dto.getNome());
        if (dto.getDosagemValor() != null) med.setDosagemValor(dto.getDosagemValor());
        if (dto.getDosagemUM() != null) med.setDosagemUM(dto.getDosagemUM());
        if (dto.getCategoria() != null) med.setCategoria(dto.getCategoria());
        if (dto.getClasseTerapeutica() != null) med.setClasseTerapeutica(dto.getClasseTerapeutica());
        if (dto.getFormaFarmaceutica() != null) med.setFormaFarmaceutica(dto.getFormaFarmaceutica());
        if (dto.getPrescricao() != null) med.setPrescricao(dto.getPrescricao());
        if (dto.getTarja() != null) med.setTarja(dto.getTarja());
        if (dto.getAnvisaRegular() != null) med.setAnvisaRegular(dto.getAnvisaRegular());
        if (dto.getPfp() != null) med.setPfp(dto.getPfp());
        if (dto.getPrecoVenda() != null) med.setPrecoVenda(dto.getPrecoVenda());
        if (dto.getStatus() != null) med.setStatus(dto.getStatus());
        if (dto.getObservacoes() != null) med.setObservacoes(dto.getObservacoes());

        if (dto.getLaboratorioId() != null) {
            med.setLaboratorio(buscarLab(dto.getLaboratorioId()));
        }
    }

    @PostMapping({"", "/cadMed"})
    public ResponseEntity<MedicamentoResponseDTO> cadastrar(
            @Valid @RequestBody MedicamentoRequestDTO dto) {

        Medicamento med = new Medicamento();
        aplicarDTO(med, dto);
        Medicamento salvo = repository.save(med);
        return ResponseEntity.ok(new MedicamentoResponseDTO(salvo));
    }

    @PostMapping("/lote")
    public List<MedicamentoResponseDTO> cadastrarLote(
            @RequestBody List<@Valid MedicamentoRequestDTO> lista) {

        return lista.stream().map(dto -> {
            Medicamento med = new Medicamento();
            aplicarDTO(med, dto);
            return new MedicamentoResponseDTO(repository.save(med));
        }).toList();
    }

    @GetMapping({"", "/listMed"})
    public List<MedicamentoResponseDTO> listar() {
        return repository.findAllWithLaboratorio()
                .stream()
                .map(MedicamentoResponseDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(new MedicamentoResponseDTO(buscarPorId(id)));
    }

    @DeleteMapping({"/{id}", "/delMed/{id}"})
    public ResponseEntity<MedicamentoResponseDTO> deletar(
            @PathVariable Long id) {

        Medicamento med = buscarPorId(id);
        repository.delete(med);
        return ResponseEntity.ok(new MedicamentoResponseDTO(med));
    }

    @PutMapping({"/{id}", "/updMed/{id}"})
    public ResponseEntity<MedicamentoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody MedicamentoRequestDTO dto) {

        Medicamento med = buscarPorId(id);
        aplicarDTO(med, dto);
        return ResponseEntity.ok(new MedicamentoResponseDTO(repository.save(med)));
    }
}