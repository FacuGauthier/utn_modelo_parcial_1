package com.agusneta.demo.controllers;

import com.agusneta.demo.modelos.Veterinario;
import com.agusneta.demo.services.VeterinarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
@CrossOrigin("*")
public class VeterinarioController {
    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping
    public List<Veterinario> listarTodas() {
        return veterinarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinario> buscarPorId(@PathVariable Long id){
        Veterinario veterinario = veterinarioService.buscarPorId(id);

        return ResponseEntity.ok().body(veterinario);
    }

    @PostMapping
    public ResponseEntity<Veterinario> guardar(@RequestBody Veterinario veterinario){
        Veterinario veterinarioNew = veterinarioService.crearVeterinario(veterinario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(veterinarioNew);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Long id, @RequestBody Veterinario veterinario){
        if(id == null) {
            throw new VeterinarioInvalidoException("El ID no puede ser nulo.");
        }

        if(veterinario == null) {
            throw new VeterinarioInvalidoException("El veterinario no puede ser nulo.");
        }

        return ResponseEntity.ok(veterinarioMod);
    }

    @GetMapping("/especialidad/{especialidad}")
    public List<Veterinario> buscarPorEspecialidad(@PathVariable String especialidad){
        if(especialidad == null) {
            throw new VeterinarioInvalidoException("La especialidad no puede ser nula.");
        }

        return veterinarioRepository.findByEspecialidad(especialidad);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id){
        try{
            veterinarioRepository.deleteById(id);
            return "Veterinario eliminado.";
        }catch(Exception e){
            return "Error al eliminar el Veterinario.";
        }
    }
}
