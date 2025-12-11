package com.ev2.muebleria.Repositorios;

import com.ev2.muebleria.Modelos.Variante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VarianteRepository extends JpaRepository<Variante, Long>  {
    
}
