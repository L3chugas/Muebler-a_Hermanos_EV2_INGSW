package com.ev2.muebleria.Modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
@Entity
@Table(name = "mueble")
public class Mueble {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_mueble;

    @Column(nullable = false) // Para que no permita nulos
    private String nombre;

    private String tipo;
    private Double precio_base;
    private Integer stock;
    private Boolean estado_activo;
    private String material;

    @Enumerated(EnumType.STRING)
    private DimensionMueble dimension;

    public Mueble() {
    }

    // Getters y Setters
    public Long getId_mueble() {
        return id_mueble;
    }
    public void setId_mueble(Long id_mueble) {
        this.id_mueble = id_mueble;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getPrecio_base() {
        return precio_base;
    }
    public void setPrecio_base(String precio_base) {
        this.precio_base = precio_base;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Boolean getEstado_activo() {
        return estado_activo;
    }
    public void setEstado_activo(Boolean estado_activo) {
        this.estado_activo = estado_activo;
    }
    public String getMaterial() {
        return material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
    public DimensionMueble getDimension() {
        return dimension;
    }
    public void setDimension(DimensionMueble dimension) {
        this.dimension = dimension;
    }



    



}
