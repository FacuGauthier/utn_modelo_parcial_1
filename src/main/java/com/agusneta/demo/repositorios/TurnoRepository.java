package com.agusneta.demo.repositorios;

import com.agusneta.demo.modelos.Estado;
import com.agusneta.demo.modelos.Mascota;
import com.agusneta.demo.modelos.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno,Long> {
    List<Mascota> findByMascotaId(Long idMascota);
    List<Turno> findByEstado(Estado estado);
}
