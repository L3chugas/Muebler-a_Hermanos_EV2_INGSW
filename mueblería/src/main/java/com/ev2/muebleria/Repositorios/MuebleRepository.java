package com.ev2.muebleria.Repositorios;

import com.ev2.muebleria.Modelos.Mueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuebleRepository extends JpaRepository<Mueble, Long>{

   
}
