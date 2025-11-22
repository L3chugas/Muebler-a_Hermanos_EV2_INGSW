package com.ev2.muebleria.Repositorios;

import com.ev2.muebleria.Modelos.DetalleCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Long>{

   
}
