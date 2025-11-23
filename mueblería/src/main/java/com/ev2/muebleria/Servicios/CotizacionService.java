package com.ev2.muebleria.Servicios;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ev2.muebleria.Modelos.Cotizacion;
import com.ev2.muebleria.Modelos.DetalleCotizacion;
import com.ev2.muebleria.Modelos.EstadoCotizacionEnum;
import com.ev2.muebleria.Repositorios.CotizacionRepository;
import com.ev2.muebleria.Repositorios.MuebleRepository;


@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private MuebleRepository muebleRepository; // Para validar stock después

    @Transactional // Importante: si falla algo, no guarda nada (Atomicidad)
    public Cotizacion crearCotizacion(Cotizacion cotizacion) {
        cotizacion.setFecha_cotizacion(LocalDateTime.now());
        cotizacion.setEstado(EstadoCotizacionEnum.valueOf("PENDIENTE".toUpperCase())); // Estado inicial
        
        double totalCalculado = 0.0;


        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            //Vinculamos el hijo al padre (Java lo necesita explícitamente)
            detalle.setCotizacion(cotizacion);
            totalCalculado += detalle.getSubtotal();
        }

        cotizacion.setTotal(totalCalculado);

        // Al guardar el Padre, se guardan los Hijos (si usaste CascadeType.ALL en la entidad)
        return cotizacionRepository.save(cotizacion);
    }

}
