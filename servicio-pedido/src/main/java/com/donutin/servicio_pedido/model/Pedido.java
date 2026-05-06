package com.donutin.servicio_pedido.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    private LocalDate fechaPedido;

    private Long clienteId; //Referencia lógica al otro microservicio (servicio-cliente)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL) // Relacion 1:N
    private List<DetallePedido> detallePedido;
}
