package com.ev2.muebleria;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        @Mock
        private com.ev2.muebleria.Servicios.PrecioService precioService;

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

    @Test
    void crearCotizacion_calculaSubtotalYTotal() {
        // Preparar mueble y variante
        Mueble silla = new Mueble();
        silla.setId_mueble(1L);
        silla.setNombre("Silla Mock");
        silla.setPrecio_base(100.0);

        // Variente (sólo necesitamos id)
        com.ev2.muebleria.Modelos.Variante variante = new com.ev2.muebleria.Modelos.Variante();
        variante.setId_variante(1L);
        variante.setNombre_variante("Premium");
        variante.setPrecio_adicional(25.0);

        // Detalle: cantidad 2
        DetalleCotizacion detalle = new DetalleCotizacion();
        detalle.setMueble(silla);
        detalle.setCantidad(2);
        detalle.setVariante(variante);

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setDetalles(List.of(detalle));

        // Mocks: precioService devuelve 125.0 (100 + 25)
        when(precioService.calcularPrecioFinal(1L, 1L)).thenReturn(125.0);
        when(cotizacionRepository.save(any(Cotizacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutar
        Cotizacion resultado = cotizacionService.crearCotizacion(cotizacion);

        // Validaciones: subtotal 125*2 = 250; total 250
        assertEquals(250.0, resultado.getDetalles().get(0).getSubtotal());
        assertEquals(250.0, resultado.getCalculoTotal());
    }

}
