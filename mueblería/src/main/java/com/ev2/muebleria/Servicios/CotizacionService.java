package com.ev2.muebleria.Servicios;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ev2.muebleria.Modelos.Cotizacion;
import com.ev2.muebleria.Modelos.DetalleCotizacion;
import com.ev2.muebleria.Modelos.EstadoCotizacionEnum;
import com.ev2.muebleria.Modelos.Mueble;
import com.ev2.muebleria.Repositorios.CotizacionRepository;
import com.ev2.muebleria.Repositorios.MuebleRepository;


@Service
public class CotizacionService {

    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private MuebleRepository muebleRepository; // Para validar stock después

    @Transactional 
    public Cotizacion crearCotizacion(Cotizacion cotizacion) {
        cotizacion.setFecha_cotizacion(LocalDateTime.now());
        cotizacion.setEstado(EstadoCotizacionEnum.valueOf("PENDIENTE".toUpperCase())); // Estado inicial
        
        double totalCalculado = 0.0;


        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            detalle.setCotizacion(cotizacion);
            totalCalculado += detalle.getSubtotal();
        }

        cotizacion.setTotal(totalCalculado);
        return cotizacionRepository.save(cotizacion);
    }

    @Transactional //Si salta un error a mitad, deshace los cambios en BD
    public Cotizacion confirmarVenta(Long cotizacionId) {
        
        //Buscar la cotización
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        //Validar Estado (Patrón State simplificado)
        //Si ya está vendida, no permitir confirmar de nuevo
        if (EstadoCotizacionEnum.valueOf("PAGADA".toUpperCase()).equals(cotizacion.getEstado())) {
            throw new RuntimeException("Esta cotización ya fue procesada como venta.");
        }

        //Recorrer los detalles para validar y descontar stock
        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            Mueble mueble = detalle.getMueble();
            int cantidadSolicitada = detalle.getCantidad();

            //Validar Stock 
            if (mueble.getStock() < cantidadSolicitada) {
                // Esto detiene el proceso y envía el mensaje de error exacto
                throw new StockInsuficienteException("Stock insuficiente para el mueble: " + mueble.getNombre());
            }

            //Decrementar Stock 
            mueble.setStock(mueble.getStock() - cantidadSolicitada);
            
            //Se guarda el mueble con el nuevo stock
            muebleRepository.save(mueble);
        }

        //Cambiar estado a VENDIDA
        cotizacion.setEstado(EstadoCotizacionEnum.valueOf("PAGADA".toUpperCase()));
        
        //Guardar y retornar
        return cotizacionRepository.save(cotizacion);
    }

}
