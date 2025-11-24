package com.ev2.muebleria.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ev2.muebleria.Servicios.CotizacionService;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.Collections;
import com.ev2.muebleria.Modelos.Cotizacion;
import com.ev2.muebleria.Servicios.StockInsuficienteException;

@RestController
@RequestMapping("/api/cotizaciones")
public class CotizacionController {

    @Autowired
    private CotizacionService cotizacionService;

    @GetMapping
    public ResponseEntity<List<Cotizacion>> listarTodas() {
        return ResponseEntity.ok(cotizacionService.listarTodas());
    }

    //Obtener una cotización específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cotizacion> obtenerPorId(@PathVariable("id") Long id) {
        Optional<Cotizacion> cotizacion = cotizacionService.obtenerPorId(id);
        
        //Si existe, retorna 200 OK con el objeto. Si no, 404 Not Found.
        return cotizacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cotizacion> crearCotizacion(@RequestBody Cotizacion cotizacion) {
        try {
            Cotizacion nuevaCotizacion = cotizacionService.crearCotizacion(cotizacion);
            return new ResponseEntity<>(nuevaCotizacion, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    @PostMapping("/{id}/confirmar")
public ResponseEntity<?> confirmarVenta(@PathVariable("id") Long id) {
    try {
        Cotizacion ventaConfirmada = cotizacionService.confirmarVenta(id);
        return ResponseEntity.ok(ventaConfirmada);
        
    } catch (StockInsuficienteException e) {
        // SOLUCIÓN: Crear un mapa para que salga como JSON { "error": "mensaje..." }
        Map<String, String> respuestaError = Collections.singletonMap("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuestaError);
        
    } catch (Exception e) {
        e.printStackTrace(); // Para ver el error en consola si es grave
        Map<String, String> respuestaError = Collections.singletonMap("error", "Error interno: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError);
    }
}
}
