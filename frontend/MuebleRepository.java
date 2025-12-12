package com.ev2.muebleria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ev2.muebleria.Modelos.Mueble;

@Repository
public interface MuebleRepository extends JpaRepository<Mueble, Long> {
}
