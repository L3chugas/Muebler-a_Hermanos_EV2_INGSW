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
    private MuebleRepository muebleRepository; //Para validar stock después
    @Autowired
    private PrecioService precioService;

    @Transactional 
    public Cotizacion crearCotizacion(Cotizacion cotizacion) {
        cotizacion.setFecha_cotizacion(LocalDateTime.now());
        cotizacion.setEstado(EstadoCotizacionEnum.valueOf("PENDIENTE".toUpperCase())); //Estado inicial
        
        double totalCalculado = 0.0;

        for (DetalleCotizacion detalle : cotizacion.getDetalles()) {
            detalle.setCotizacion(cotizacion);

            // Intentar recuperar precio final (mueble + variante), usando PrecioService
            Long idMueble = detalle.getMueble() != null ? detalle.getMueble().getId_mueble() : null;
            Long idVariante = detalle.getVariante() != null ? detalle.getVariante().getId_variante() : null;
            Double precioFinal = null;
            if (idMueble != null) {
                precioFinal = precioService.calcularPrecioFinal(idMueble, idVariante);
            }
            if (precioFinal == null) precioFinal = 0.0;

            int cantidad = detalle.getCantidad() == null ? 0 : detalle.getCantidad();
            double subtotal = precioFinal * cantidad;
            detalle.setSubtotal(subtotal);
            totalCalculado += subtotal;
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

        //Validar Estado
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

            //Recupera  la entidad gestionada desde el repositorio para evitar datos desactualizados
            Mueble mueble = muebleRepository.findById(muebleRef.getId_mueble()).orElse(null);
            //Si no se encuentra en el repo, intenta usar el objeto que viene en el detalle
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

    @Transactional
    public Cotizacion cancelarCotizacion(Long cotizacionId) {
        Cotizacion cotizacion = cotizacionRepository.findById(cotizacionId)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));
        
        // Validar si ya está cancelada para evitar errores o redundancia
        if (EstadoCotizacionEnum.valueOf("CANCELADA".toUpperCase()).equals(cotizacion.getEstado())) {
            throw new RuntimeException("La cotización ya se encuentra cancelada.");
        }
        cotizacion.setEstado(EstadoCotizacionEnum.valueOf("CANCELADA".toUpperCase()));
        return cotizacionRepository.save(cotizacion);
    }


}
