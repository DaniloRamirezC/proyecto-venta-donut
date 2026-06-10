package com.donutin.servicio_pedido.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa la gestión de los pedidos")
public class Pedido 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    @NotNull(message = "La fecha del pedido es obligatoria")
    @PastOrPresent(message = "La fecha del pedido no puede ser futura")
    private LocalDate fechaPedido;

    @Schema(description = "Id del cliente (referencia al microservicio servicio-cliente)")
    private Long clienteId; //Referencia lógica al otro microservicio (servicio-cliente)
    @JsonManagedReference
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL) // Relacion 1:N
    private List<DetallePedido> detallePedido;
}
