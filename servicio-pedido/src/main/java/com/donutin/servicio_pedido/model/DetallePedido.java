package com.donutin.servicio_pedido.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;
    
    //Uso de anotaciones para validar que datos no sean nulos
    @NotNull(message = "La cantidad no puede ser nulo")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private Integer cantidad;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    private Long donutId; //Referencia lógica al microservicio servicio-catalogo

    @Transient
    private Object datosDonuts; // No existe, se encuentra en otra tabla
}
