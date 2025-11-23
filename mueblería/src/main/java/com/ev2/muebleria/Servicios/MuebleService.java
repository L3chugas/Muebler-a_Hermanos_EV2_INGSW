package com.ev2.muebleria.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ev2.muebleria.Modelos.Mueble;
import com.ev2.muebleria.Repositorios.MuebleRepository;

@Service
public class MuebleService {

    @Autowired
    private MuebleRepository muebleRepository;

    public List<Mueble> listarMuebles() {
        return muebleRepository.findAll();
    }

    @Transactional
    public Mueble crearMueble(Mueble mueble) {
        return muebleRepository.save(mueble);
    }

    @Transactional
    public Mueble guardarMueble(Mueble mueble) {
        return muebleRepository.save(mueble);
    }

    public Mueble obtenerMueble(Long id) {
        return muebleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarMueble(Long id) {
        if (muebleRepository.existsById(id)) {
            muebleRepository.deleteById(id);
        }
    }


    @Transactional
    public Mueble actualizarMueble(Long id, Mueble mueble) {
        mueble.setId_mueble(id);
        return muebleRepository.save(mueble);
    }


    public void desactivarMueble(Long id) {
        Mueble mueble = muebleRepository.findById(id).orElseThrow();
        mueble.setEstado_activo(false);
        muebleRepository.save(mueble);
    }

}
