package com.agusneta.demo.controllers;

import com.agusneta.demo.excepciones.TurnoInvalidoException;
import com.agusneta.demo.modelos.Estado;
import com.agusneta.demo.modelos.Mascota;
import com.agusneta.demo.modelos.Turno;
import com.agusneta.demo.modelos.Veterinario;
import com.agusneta.demo.repositorios.MascotaRepository;
import com.agusneta.demo.repositorios.TurnoRepository;
import com.agusneta.demo.repositorios.VeterinarioRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@CrossOrigin("*")
public class TurnoController {
    private final TurnoRepository turnoRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public TurnoController(TurnoRepository turnoRepository, MascotaRepository mascotaRepository, VeterinarioRepository veterinarioRepository) {
        this.turnoRepository = turnoRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @PostMapping
    public String guardar(@RequestBody Turno turno){
        turno.setId(null);
        Mascota mascota = mascotaRepository.findById(turno.getMascota().getId())
                .orElseThrow(() -> new TurnoInvalidoException("La mascota no existe."));
        Veterinario veterinario = veterinarioRepository.findById(turno.getVeterinario().getId())
                .orElseThrow(() -> new TurnoInvalidoException("El veterinario no existe."));

        if(turno.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new TurnoInvalidoException("Error: La fecha del Turno debe ser posterior a la actual.");
        }
        turno.setEstado(Estado.PENDIENTE);

        turnoRepository.save(turno);
        return "Turno guardado.";
    }

    @GetMapping("/{id}")
    public List<Mascota> listarTurnoPorIdMascota(@PathVariable Long idMascota) {
        return turnoRepository.findByMascotaId(idMascota);
    }

    @GetMapping("/{estado}")
    public List<Turno> listarTurnoPorEstado(@PathVariable String estadoBuscar) {
        return turnoRepository.findByEstado(Estado.valueOf(estadoBuscar));
    }

    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoInvalidoException("El turno no existe."));

        if(turno.getEstado() != Estado.PENDIENTE){
            throw new TurnoInvalidoException("El turno no esta en estado Pendiente.");
        }

        turno.setEstado(Estado.CANCELADO);
        turnoRepository.save(turno);
        return "Se ha finalizado el turno.";
    }

}
