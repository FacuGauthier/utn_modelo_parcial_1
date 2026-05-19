package com.agusneta.demo.repositorios;

import com.agusneta.demo.modelos.Duenio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DuenioRepository extends JpaRepository<Duenio, Long> {
}
