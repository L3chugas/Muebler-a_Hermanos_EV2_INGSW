package com.ev2.muebleria.Repositorios;

import com.ev2.muebleria.Modelos.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long>{
    
}
