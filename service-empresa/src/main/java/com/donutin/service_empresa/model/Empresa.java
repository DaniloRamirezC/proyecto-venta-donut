package com.donutin.service_empresa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empresa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpresa;
    @Column(nullable = false, unique = true)
    private String rutEmpresa;

    @Column(nullable = false, length = 100)
    private String razonSocial;

    @Column(nullable = false, length = 50)
    private String giro;

    @Column(nullable = false, length = 100)
    private String direccionEmpresa;
}
