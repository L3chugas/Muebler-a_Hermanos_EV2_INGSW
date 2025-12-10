package com.ev2.muebleria.Modelos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;

@Entity
@Table(name = "detalle_cotizacion")
public class DetalleCotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_cotiz")
    private Long id_detalle;

    private Integer cantidad;
    private Double subtotal;

    // Relación: Muchos detalles pertenecen a una cotización
    @ManyToOne
    @JoinColumn(name = "cotizacion_id") // Nombre de la columna FK en MySQL
    @JsonIgnore
    private Cotizacion cotizacion;

    // Relación: Muchos detalles pueden apuntar a un mueble
    @ManyToOne
    @JoinColumn(name = "mueble_id")
    private Mueble mueble;

    // Relación: Muchos detalles pueden tener una variante
    @ManyToOne
    @JoinColumn(name = "variante_id")
    private Variante variante;

    public DetalleCotizacion() {
    }

    // Getters y Setters
    public Long getId_detalle() {
        return id_detalle;
    }
    public void setId_detalle(Long id_detalle) {
        this.id_detalle = id_detalle;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    public Cotizacion getCotizacion() {
        return cotizacion;
    }
    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }
    public Mueble getMueble() {
        return mueble;
    }
    public void setMueble(Mueble mueble) {
        this.mueble = mueble;
    }
    public Variante getVariante() {
        return variante;
    }
    public void setVariante(Variante variante) {
        this.variante = variante;
    }

}
