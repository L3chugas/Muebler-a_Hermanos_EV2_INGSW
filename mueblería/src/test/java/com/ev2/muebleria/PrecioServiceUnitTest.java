package com.ev2.muebleria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ev2.muebleria.Modelos.Mueble;
import com.ev2.muebleria.Modelos.Variante;
import com.ev2.muebleria.Repositorios.MuebleRepository;
import com.ev2.muebleria.Repositorios.VarianteRepository;
import com.ev2.muebleria.Servicios.PrecioService;

@ExtendWith(MockitoExtension.class)
public class PrecioServiceUnitTest {

    @Mock
    private MuebleRepository muebleRepository;

    @Mock
    private VarianteRepository varianteRepository;

    @InjectMocks
    private PrecioService precioService;

    @Test
    void calculaPrecioConVarianteExistente() {
        Mueble m = new Mueble();
        m.setId_mueble(1L);
        m.setPrecio_base(200.0);

        Variante v = new Variante();
        v.setId_variante(10L);
        v.setPrecio_adicional(50.0);

        when(muebleRepository.findById(1L)).thenReturn(Optional.of(m));
        when(varianteRepository.findById(10L)).thenReturn(Optional.of(v));

        Double total = precioService.calcularPrecioFinal(1L, 10L);
        assertEquals(250.0, total);
    }

    @Test
    void calculaPrecioSinVarianteDevuelveBase() {
        Mueble m = new Mueble();
        m.setId_mueble(2L);
        m.setPrecio_base(120.0);

        when(muebleRepository.findById(2L)).thenReturn(Optional.of(m));

        Double total = precioService.calcularPrecioFinal(2L, null);
        assertEquals(120.0, total);
    }

    @Test
    void calculaPrecioConVarianteNoEncontradaDevuelveBase() {
        Mueble m = new Mueble();
        m.setId_mueble(3L);
        m.setPrecio_base(80.0);

        when(muebleRepository.findById(3L)).thenReturn(Optional.of(m));
        when(varianteRepository.findById(99L)).thenReturn(Optional.empty());

        Double total = precioService.calcularPrecioFinal(3L, 99L);
        assertEquals(80.0, total);
    }

    @Test
    void calculaPrecioMuebleNoEncontradoDevuelveNull() {
        when(muebleRepository.findById(100L)).thenReturn(Optional.empty());

        Double total = precioService.calcularPrecioFinal(100L, null);
        assertNull(total);
    }

}
