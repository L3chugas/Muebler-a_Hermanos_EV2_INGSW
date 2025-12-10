package com.ev2.muebleria.Controladores;

import com.ev2.muebleria.Modelos.Mueble;
import com.ev2.muebleria.Servicios.MuebleService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/muebles")
public class MuebleController {

    @Autowired
    private MuebleService muebleService;

    @GetMapping
    public ResponseEntity<List<Mueble>> listarMuebles() {
        return ResponseEntity.ok(muebleService.listarMuebles());
    }

    @PostMapping
    public ResponseEntity<Mueble> crearMueble(@RequestBody Mueble mueble) {
        Mueble nuevoMueble = muebleService.crearMueble(mueble);
        return ResponseEntity.ok(nuevoMueble);
    }   

    @PutMapping("/{id}")
    public ResponseEntity<Mueble> actualizar(@PathVariable Long id, @RequestBody Mueble muebleEditado) {
        //Se busca el ID primero, setear los nuevos valores y guardar
        muebleEditado.setId_mueble(id); 
        return ResponseEntity.ok(muebleService.guardarMueble(muebleEditado));
    }

}
