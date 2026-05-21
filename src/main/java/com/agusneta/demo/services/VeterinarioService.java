package com.agusneta.demo.services;

import com.agusneta.demo.modelos.Veterinario;
import com.agusneta.demo.repositorios.TurnoRepository;
import com.agusneta.demo.repositorios.VeterinarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioService {
    private final VeterinarioRepository veterinarioRepository;
    private final TurnoRepository turnoRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository,  TurnoRepository turnoRepository) {
        this.veterinarioRepository = veterinarioRepository;
        this.turnoRepository = turnoRepository;
    }

    public Veterinario crearVeterinario(Veterinario veterinario) {
        validarVeterinario(veterinario);

        try{
            veterinario.setId(null);
            return veterinarioRepository.save(veterinario);
        } catch (DataIntegrityViolationException ex){
            throw new IllegalArgumentException("Error en la integridad de datos.");
        }
    }

    public Veterinario modificarVeterinario(Long id, Veterinario datosNuevos) {
        validarVeterinario(datosNuevos);

        Veterinario veterinario = buscarPorId(id);

        veterinario.setId(id);
        veterinario.setNombre(datosNuevos.getNombre());
        veterinario.setEspecialidad(datosNuevos.getEspecialidad());
        veterinario.setMatricula(datosNuevos.getMatricula());

        try{
            return veterinarioRepository.save(veterinario);
        } catch (DataIntegrityViolationException ex){
            throw new IllegalArgumentException("Error en la integridad de datos.");
        }
    }

    public Veterinario buscarPorId(Long id) {
        if(id == null){
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        return veterinarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Veterinario no encontrado."));
    }

    public List<Veterinario> listarPorEspecialidad(String especialidad) {
        return veterinarioRepository.findByEspecialidad(especialidad);
    }

    public List<Veterinario> listarTodos() {
        return veterinarioRepository.findAll();
    }

    public void borrarVeterinario(Long id) {
        if(id == null){
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        turnoRepository.deleteByVeterinarioId(id);
        veterinarioRepository.deleteById(id);
    }


    private void validarVeterinario(Veterinario veterinario) {
        if(veterinario == null){
            throw new IllegalArgumentException("Veterinario no puede ser nulo.");
        }

        if(veterinario.getId() == null){
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        if(veterinario.getNombre() == null || veterinario.getNombre().isBlank()){
            throw new IllegalArgumentException("El Nombre es obligatorio.");
        }

        if(veterinario.getMatricula() == null || veterinario.getMatricula().isBlank()){
            throw new IllegalArgumentException("La Matricula es obligatorio.");
        }

        if(veterinario.getEspecialidad() == null || veterinario.getEspecialidad().isBlank()){
            throw new IllegalArgumentException("La Especialidad es obligatorio.");
        }
    }
}
