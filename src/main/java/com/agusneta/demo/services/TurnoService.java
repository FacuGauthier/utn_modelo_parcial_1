package com.agusneta.demo.services;

import com.agusneta.demo.excepciones.TurnoInvalidoException;
import com.agusneta.demo.modelos.Estado;
import com.agusneta.demo.modelos.Turno;
import com.agusneta.demo.repositorios.MascotaRepository;
import com.agusneta.demo.repositorios.TurnoRepository;
import com.agusneta.demo.repositorios.VeterinarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnoService {
    private final TurnoRepository turnoRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public TurnoService(TurnoRepository turnoRepository, MascotaRepository mascotaRepository, VeterinarioRepository veterinarioRepository) {
        this.turnoRepository = turnoRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public Turno crearTurno(Turno turno) {
        validarTurno(turno);

        try{
            turno.setId(null);
            turno.setEstado(Estado.PENDIENTE);

            return turnoRepository.save(turno);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("No se pudo registrar el turno por un error en la integridad de datos.");
        }
    }

    public List<Turno> listarTurnoPorMascota(Long idMascota) {
        if(idMascota == null) throw new TurnoInvalidoException("El ID no puede ser nulo.");

        return turnoRepository.findByMascotaId(idMascota);
    }

    public List<Turno> listarTurnoPorEstado(Estado estado) {
        if(estado == null) throw new TurnoInvalidoException("El estado no puede ser nulo.");

        return turnoRepository.findByEstado(estado);
    }

    public void finalizarTurno(Long id) {
        if(id == null) throw new TurnoInvalidoException("El ID no puede ser nulo.");

        Turno turnoFinalizar = buscarPorId(id);

        try{
            turnoFinalizar.setEstado(Estado.CANCELADO);
            turnoRepository.save(turnoFinalizar);
        } catch (DataIntegrityViolationException e) {
            throw new TurnoInvalidoException("No se pudo finalizar el turno por un error en la integridad de datos.");
        }
    }

    public Turno buscarPorId(Long id) {
        if(id == null) throw new IllegalArgumentException("El ID no puede ser nulo.");

        return turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoInvalidoException("El turno no existe."));
    }


    private void validarTurno(Turno turno) {
        mascotaRepository.findById(turno.getMascota().getId())
                .orElseThrow(() -> new TurnoInvalidoException("Mascota no encontrada"));

        veterinarioRepository.findById(turno.getVeterinario().getId())
                .orElseThrow(() -> new TurnoInvalidoException("Veterinario no encontrada"));

        if(turno.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new TurnoInvalidoException("Error: La fecha del Turno debe ser posterior a la actual.");
        }
    }
}
