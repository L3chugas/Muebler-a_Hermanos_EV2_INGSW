package com.ev2.muebleria.Controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ev2.muebleria.Modelos.Variante;
import com.ev2.muebleria.Repositorios.VarianteRepository;

@RestController
@RequestMapping("/api/variantes")
public class VarianteController {

    @Autowired
    private VarianteRepository varianteRepository;

    @GetMapping
    public ResponseEntity<List<Variante>> listarTodas() {
        return ResponseEntity.ok(varianteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Variante> obtenerPorId(@PathVariable("id") Long id) {
        Optional<Variante> variante = varianteRepository.findById(id);
        return variante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Variante> crearVariante(@RequestBody Variante variante) {
        Variante nueva = varianteRepository.save(variante);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variante> actualizarVariante(@PathVariable("id") Long id, @RequestBody Variante variante) {
        Optional<Variante> opt = varianteRepository.findById(id);
        if (!opt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Variante existente = opt.get();
        existente.setNombre_variante(variante.getNombre_variante());
        existente.setPrecio_adicional(variante.getPrecio_adicional());
        Variante guardada = varianteRepository.save(existente);
        return ResponseEntity.ok(guardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVariante(@PathVariable("id") Long id) {
        if (!varianteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        varianteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
