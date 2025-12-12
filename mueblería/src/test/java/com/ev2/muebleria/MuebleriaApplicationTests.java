package com.ev2.muebleria;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ev2.muebleria.Modelos.Cotizacion;
import com.ev2.muebleria.Modelos.DetalleCotizacion;
import com.ev2.muebleria.Modelos.EstadoCotizacionEnum;
import com.ev2.muebleria.Modelos.Mueble;
import com.ev2.muebleria.Repositorios.CotizacionRepository;
import com.ev2.muebleria.Repositorios.MuebleRepository;
import com.ev2.muebleria.Servicios.CotizacionService;
import com.ev2.muebleria.Servicios.StockInsuficienteException;

@SpringBootTest(classes = MuebleriaApplication.class)
class MuebleriaApplicationTests {

	@Autowired
    private CotizacionService cotizacionService;
    @Autowired
    private MuebleRepository muebleRepository;
    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Test
    @Transactional
    void testVentaConStockInsuficiente() {
        // Escenario: Mueble con stock 1, solicitamos 5 -> debe lanzar StockInsuficienteException
        Mueble mesa = new Mueble();
        mesa.setNombre("Mesa Test");
        mesa.setPrecio_base(100.0);
        mesa.setStock(1); // sólo 1 disponible
        mesa = muebleRepository.save(mesa);

        // Crear cotización solicitando 5 mesas
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setEstado(EstadoCotizacionEnum.PENDIENTE);

        DetalleCotizacion detalle = new DetalleCotizacion();
        detalle.setMueble(mesa);
        detalle.setCantidad(5); // pedimos 5, más que el stock
        // calcular subtotal para consistencia
        detalle.setSubtotal(mesa.getPrecio_base() * detalle.getCantidad());
        detalle.setCotizacion(cotizacion); // Vincular

        cotizacion.setDetalles(List.of(detalle));
        cotizacion = cotizacionRepository.save(cotizacion);

        Long idCotizacion = cotizacion.getId_cotizacion();

        // Verificar que al confirmar la venta se lanza la excepción por stock insuficiente
        assertThrows(StockInsuficienteException.class, () -> cotizacionService.confirmarVenta(idCotizacion));
    }

}
