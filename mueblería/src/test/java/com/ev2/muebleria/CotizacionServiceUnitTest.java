package com.ev2.muebleria;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ev2.muebleria.Modelos.Cotizacion;
import com.ev2.muebleria.Modelos.DetalleCotizacion;
import com.ev2.muebleria.Modelos.EstadoCotizacionEnum;
import com.ev2.muebleria.Modelos.Mueble;
import com.ev2.muebleria.Repositorios.CotizacionRepository;
import com.ev2.muebleria.Repositorios.MuebleRepository;
import com.ev2.muebleria.Servicios.CotizacionService;
import com.ev2.muebleria.Servicios.StockInsuficienteException;

@ExtendWith(MockitoExtension.class)
public class CotizacionServiceUnitTest {

    @Mock
    private CotizacionRepository cotizacionRepository;

    @Mock
    private MuebleRepository muebleRepository;

    @InjectMocks
    private CotizacionService cotizacionService;

    @Test
    void confirmarVenta_lanzaStockInsuficiente_siStockEsMenorQueCantidad() {
        // Crear mueble con stock 1
        Mueble mesa = new Mueble();
        mesa.setId_mueble(1L);
        mesa.setNombre("Mesa Mock");
        mesa.setPrecio_base(100.0);
        mesa.setStock(1);

        // Crear detalle que pide 5 unidades
        DetalleCotizacion detalle = new DetalleCotizacion();
        detalle.setMueble(mesa);
        detalle.setCantidad(5);
        detalle.setSubtotal(mesa.getPrecio_base() * detalle.getCantidad());

        // Cotizacion con estado PENDIENTE
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId_cotizacion(10L);
        cotizacion.setEstado(EstadoCotizacionEnum.PENDIENTE);
        cotizacion.setDetalles(List.of(detalle));

        // Mock: al buscar la cotizacion por id, devolvemos la cotizacion creada
        when(cotizacionRepository.findById(10L)).thenReturn(Optional.of(cotizacion));

        // Ejecutar y verificar que lanza StockInsuficienteException
        assertThrows(StockInsuficienteException.class, () -> cotizacionService.confirmarVenta(10L));

        // Verificar que no se guardó ningún mueble (porque se detuvo antes)
        verify(muebleRepository, never()).save(any());
    }

}
