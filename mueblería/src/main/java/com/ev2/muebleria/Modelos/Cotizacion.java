package com.ev2.muebleria.Modelos;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;

@Entity
@Table(name = "cotizacion")
public class Cotizacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Long id_cotizacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cotizacion")
    private EstadoCotizacionEnum estado;

    @Column(name = "fecha_cotizacion")
    private LocalDateTime fecha_cotizacion;

    @Column(name = "calculo_total")
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
        if (detalles != null) {
            for (DetalleCotizacion detalle : detalles) {
                detalle.setCotizacion(this);
            }
        }
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

    @PrePersist
    public void prePersist() {
        if (this.fecha_cotizacion == null) {
            this.fecha_cotizacion = LocalDateTime.now();
        }
    }

}
