package com.ev2.muebleria.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ev2.muebleria.Modelos.Cotizacion;
import com.ev2.muebleria.Modelos.EstadoCotizacionEnum;
import com.ev2.muebleria.Repository.CotizacionRepository;

@RestController
@RequestMapping("/api/cotizaciones")
@CrossOrigin(origins = "*") 
public class CotizacionController {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmarCotizacion(@PathVariable("id") Long id) {
        Optional<Cotizacion> cotizacionOpt = cotizacionRepository.findById(id);
        
        if (cotizacionOpt.isPresent()) {
            Cotizacion cotizacion = cotizacionOpt.get();
            cotizacion.setEstado(EstadoCotizacionEnum.CONFIRMADA);
            cotizacionRepository.save(cotizacion);
            return ResponseEntity.ok("Cotización confirmada exitosamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}