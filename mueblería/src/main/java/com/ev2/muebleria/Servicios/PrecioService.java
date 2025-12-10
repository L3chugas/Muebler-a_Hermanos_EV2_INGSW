package com.ev2.muebleria.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ev2.muebleria.Modelos.Mueble;
import com.ev2.muebleria.Modelos.Variante;
import com.ev2.muebleria.Repositorios.MuebleRepository;
import com.ev2.muebleria.Repositorios.VarianteRepository;

@Service
public class PrecioService {

    @Autowired
    private MuebleRepository muebleRepository;

    @Autowired
    private VarianteRepository varianteRepository;

    /**
     Calcula el precio final dado el id de mueble y (opcional) id de variante.
     Si la variante no existe o es null, se devuelve solo el precio base.
     */
    public Double calcularPrecioFinal(Long idMueble, Long idVariante) {
        Mueble mueble = muebleRepository.findById(idMueble).orElse(null);
        if (mueble == null) return null;

        Double precioBase = mueble.getPrecio_base() != null ? mueble.getPrecio_base() : 0.0;

        Double adicional = 0.0;
        if (idVariante != null) {
            Variante variante = varianteRepository.findById(idVariante).orElse(null);
            if (variante != null && variante.getPrecio_adicional() != null) {
                adicional = variante.getPrecio_adicional();
            }
        }

        return precioBase + adicional;
    }

}
