package com.ev2.muebleria.Servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<Cotizacion> listarTodas() {
        return cotizacionRepository.findAll();
    }

    public Optional<Cotizacion> obtenerPorId(Long id) {
        return cotizacionRepository.findById(id);
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
        if (cotizacion.getDetalles() == null) {
            throw new RuntimeException("La cotización no contiene detalles");
        }

        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            Mueble muebleRef = detalle.getMueble();
            if (muebleRef == null || muebleRef.getId_mueble() == null) {
                throw new RuntimeException("Detalle sin mueble asociado (id ausente)");
            }

            // Recuperar entidad gestionada desde el repositorio para evitar NPEs o datos desactualizados
            Mueble mueble = muebleRepository.findById(muebleRef.getId_mueble()).orElse(null);
            // Si no se encuentra en el repo, intentamos usar el objeto que viene en el detalle (útil para mocks/tests)
            if (mueble == null) {
                mueble = muebleRef;
            }

            Integer stockActual = mueble.getStock();
            int cantidadSolicitada = detalle.getCantidad() == null ? 0 : detalle.getCantidad();

            //Validar Stock (tratar stock nulo como 0)
            if (stockActual == null || stockActual < cantidadSolicitada) {
                throw new StockInsuficienteException("Stock insuficiente para el mueble: " + mueble.getNombre());
            }

            //Decrementar Stock 
            mueble.setStock(stockActual - cantidadSolicitada);

            //Se guarda el mueble con el nuevo stock
            muebleRepository.save(mueble);
        }

        //Cambiar estado a VENDIDA
        cotizacion.setEstado(EstadoCotizacionEnum.valueOf("PAGADA".toUpperCase()));
        
        //Guardar y retornar
        return cotizacionRepository.save(cotizacion);
    }

}
