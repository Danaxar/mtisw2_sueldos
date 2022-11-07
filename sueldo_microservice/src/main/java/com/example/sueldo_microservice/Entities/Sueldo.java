package com.example.sueldo_microservice.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sueldo")
public class Sueldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;
    private String rut;
    private String nombreEmpleado;
    private Integer yearsOfService;
    private Integer sueldoFijoMensual; // sueldo base
    private double bonoYearsOfService;
    // Desde aqui hacia abajo requiere de calculos en la capa servicio

    // Analizar
    private double pagoHorasExtras;
    private double montoDescuentos;

    private double sueldoBruto; // sueldo base + bonificaciones
    private double cotizacionPrevisional;
    private double cotizacionSalud;

    private Integer sueldoFinal;
}
