package com.farmacia.farmacia.controller.api;

import com.farmacia.farmacia.dto.LaboratorioRequestDTO;
import com.farmacia.farmacia.dto.LaboratorioResponseDTO;
import com.farmacia.farmacia.exception.OperacaoNaoPermitidaException;
import com.farmacia.farmacia.exception.RecursoNaoEncontradoException;
import com.farmacia.farmacia.model.Laboratorio;
import com.farmacia.farmacia.repository.LaboratorioRepository;
import com.farmacia.farmacia.repository.MedicamentoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laboratorios")
@CrossOrigin(origins = "*")
public class LaboratorioController {

    private final LaboratorioRepository repository;
    private final MedicamentoRepository medicamentoRepository;

    public LaboratorioController(
            LaboratorioRepository repository,
            MedicamentoRepository medicamentoRepository) {
        this.repository = repository;
        this.medicamentoRepository = medicamentoRepository;
    }

    private Laboratorio buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Laboratório não encontrado com ID: " + id));
    }

    private void aplicarDTO(Laboratorio lab, LaboratorioRequestDTO dto) {
        if (dto.getCnpj() != null) lab.setCnpj(dto.getCnpj());
        if (dto.getRazaoSocial() != null) lab.setRazaoSocial(dto.getRazaoSocial());
        if (dto.getNomeFantasia() != null) lab.setNomeFantasia(dto.getNomeFantasia());
        if (dto.getStatus() != null) lab.setStatus(dto.getStatus());
    }

    @PostMapping
    public ResponseEntity<LaboratorioResponseDTO> cadastrar(
            @Valid @RequestBody LaboratorioRequestDTO dto) {

        Laboratorio lab = new Laboratorio();
        aplicarDTO(lab, dto);

        return ResponseEntity.ok(
                new LaboratorioResponseDTO(repository.save(lab))
        );
    }

    @PostMapping("/lote")
    public List<LaboratorioResponseDTO> cadastrarLote(
            @RequestBody List<@Valid LaboratorioRequestDTO> lista) {

        return lista.stream().map(dto -> {
            Laboratorio lab = new Laboratorio();
            aplicarDTO(lab, dto);
            return new LaboratorioResponseDTO(repository.save(lab));
        }).toList();
    }

    @GetMapping
    public List<LaboratorioResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(LaboratorioResponseDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratorioResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                new LaboratorioResponseDTO(buscarPorId(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaboratorioResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody LaboratorioRequestDTO dto) {

        Laboratorio lab = buscarPorId(id);
        aplicarDTO(lab, dto);

        return ResponseEntity.ok(
                new LaboratorioResponseDTO(repository.save(lab))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Laboratorio lab = buscarPorId(id);
        if (medicamentoRepository.existsByLaboratorio_Id(id)) {
            throw new OperacaoNaoPermitidaException(
                    "Não é possível excluir o laboratório: existem medicamentos vinculados a ele. "
                            + "Exclua ou altere esses medicamentos antes de remover o laboratório.");
        }
        repository.delete(lab);
        return ResponseEntity.noContent().build();
    }
}