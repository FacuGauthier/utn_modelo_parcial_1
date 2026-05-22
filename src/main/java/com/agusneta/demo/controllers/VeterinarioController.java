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
    public ResponseEntity<List<Veterinario>> listarTodas() {
        List<Veterinario> veterinarios = veterinarioService.listarTodos();
        if(veterinarios.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(veterinarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinario> buscarPorId(@PathVariable Long id){
        Veterinario veterinario = veterinarioService.buscarPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(veterinario);
    }

    @PostMapping
    public ResponseEntity<Veterinario> guardar(@RequestBody Veterinario veterinario){
        Veterinario veterinarioNew = veterinarioService.crearVeterinario(veterinario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(veterinarioNew);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veterinario> actualizar(@PathVariable Long id, @RequestBody Veterinario veterinario){
        Veterinario veterinarioMod = veterinarioService.modificarVeterinario(id, veterinario);

        return ResponseEntity.status(HttpStatus.OK).body(veterinarioMod);
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<Veterinario>> buscarPorEspecialidad(@PathVariable String especialidad){
        List<Veterinario> veterinariosEspecialidad = veterinarioService.listarPorEspecialidad(especialidad);
        if(veterinariosEspecialidad.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(veterinariosEspecialidad);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id){
        veterinarioService.borrarVeterinario(id);
        return ResponseEntity.noContent().build();
    }
}
