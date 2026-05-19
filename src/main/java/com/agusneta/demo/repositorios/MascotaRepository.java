package com.agusneta.demo.repositorios;

import com.agusneta.demo.modelos.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
