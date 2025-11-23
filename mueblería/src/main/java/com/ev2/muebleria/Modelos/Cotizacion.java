package com.ev2.muebleria.Modelos;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
public class Cotizacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cotizacion;
    
    @Enumerated(EnumType.STRING)
    private EstadoCotizacionEnum estado;
    private LocalDateTime fecha_cotizacion;
    private Double calculoTotal;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL)
    private List<DetalleCotizacion> detalles;

    public Cotizacion() {
    }

    // Getters y Setters
    public Long getId_cotizacion() {
        return id_cotizacion;
    }
    public void setId_cotizacion(Long id_cotizacion) {
        this.id_cotizacion = id_cotizacion;
    }
    public LocalDateTime getFecha_cotizacion() {
        return fecha_cotizacion;
    }
    public void setFecha_cotizacion(LocalDateTime fecha_cotizacion) {
        this.fecha_cotizacion = fecha_cotizacion;
    }

    public EstadoCotizacionEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCotizacionEnum estado) {
        this.estado = estado;
    }

    public List<DetalleCotizacion> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleCotizacion> detalles) {
        this.detalles = detalles;
    }

    public void calcularTotal() {
        double total = 0.0;
        if (detalles != null) {
            for (DetalleCotizacion detalle : detalles) {
                total += detalle.getSubtotal();
            }
        }
        setTotal(total);
    }

    public void setTotal(Double total) {
            this.calculoTotal = total;
    }

    public Double getCalculoTotal() {
        return calculoTotal;
    }

}
