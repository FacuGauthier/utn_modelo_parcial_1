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
        return veterinarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Veterinario buscarPorId(@PathVariable Long id){
        return veterinarioRepository.findById(id).orElseThrow(() -> new VeterinarioInvalidoException("El veterinario no existe."));
    }

    @PostMapping
    public String guardar(@RequestBody Veterinario veterinario){
        if(veterinario == null) {
            throw new VeterinarioInvalidoException("El veterinario no puede ser nulo.");
        }

        veterinario.setId(null);
        veterinarioRepository.save(veterinario);

        return "Veterinario guardado";
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Long id, @RequestBody Veterinario veterinario){
        if(id == null) {
            throw new VeterinarioInvalidoException("El ID no puede ser nulo.");
        }

        if(veterinario == null) {
            throw new VeterinarioInvalidoException("El veterinario no puede ser nulo.");
        }

        veterinario.setId(id);
        veterinarioRepository.save(veterinario);

        return "Veterinario actualizado";
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
