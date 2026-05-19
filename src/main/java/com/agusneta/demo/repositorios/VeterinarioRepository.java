package com.agusneta.demo.repositorios;

import com.agusneta.demo.modelos.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarioRepository extends JpaRepository<Veterinario,Long> {
    List<Veterinario> findByEspecialidad(String especialidad);
}
