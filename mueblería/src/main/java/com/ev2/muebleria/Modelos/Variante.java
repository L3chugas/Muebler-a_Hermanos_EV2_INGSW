package com.ev2.muebleria.Modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Variante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_variante;

    private String nombre_variante;
    private Double precio_adicional;

    public Variante() {
    }
    
    // Getters y Setters
    public Long getId_variante() {
        return id_variante;
    }
    public void setId_variante(Long id_variante) {
        this.id_variante = id_variante;
    }
    public String getNombre_variante() {
        return nombre_variante;
    }
    public void setNombre_variante(String nombre_variante) {
        this.nombre_variante = nombre_variante;
    }
    public Double getPrecio_adicional() {
        return precio_adicional;
    }
    public void setPrecio_adicional(Double precio_adicional) {
        this.precio_adicional = precio_adicional;
    }


}
