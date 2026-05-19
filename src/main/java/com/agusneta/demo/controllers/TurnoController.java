package com.agusneta.demo.controllers;

import com.agusneta.demo.modelos.Estado;
import com.agusneta.demo.modelos.Turno;
import com.agusneta.demo.services.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@CrossOrigin("*")
public class TurnoController {
    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno){
        Turno turnoNew = turnoService.crearTurno(turno);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(turnoNew);
    }

    @GetMapping("/mascota/{id}")
    public List<Turno> listarTurnoPorMascota(@PathVariable Long idMascota) {
        return turnoService.listarTurnoPorMascota(idMascota);
    }

    @GetMapping("/estado/{estado}")
    public List<Turno> listarTurnoPorEstado(@PathVariable String estadoBuscar) {
        return turnoService.listarTurnoPorEstado(Estado.valueOf(estadoBuscar));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Long id) {
        Turno turno = turnoService.buscarPorId(id);

        return ResponseEntity.ok(turno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Turno> finalizarTurno(@PathVariable Long id) {
        turnoService.finalizarTurno(id);
        return ResponseEntity.noContent().build();
    }

}
