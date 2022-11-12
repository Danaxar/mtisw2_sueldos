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

    public void printSueldo(){
        String salida = Integer.toString(this.id) + " - " + this.rut + "\n";
        salida = salida + "\tNombres: " + this.nombreEmpleado+ "\n";
        salida = salida + "\tAnos de servicio: " + Integer.toString(this.yearsOfService)+ "\n";
        salida = salida + "\tSueldo fijo mensual: " + Integer.toString(this.sueldoFijoMensual)+ "\n";
        salida = salida + "\tBono anos de servicio: " + String.valueOf(this.bonoYearsOfService)+ "\n";
        salida = salida + "\tPago de horas extras: " + String.valueOf(this.pagoHorasExtras)+ "\n";
        salida = salida + "\tMonto descuentos: " + String.valueOf(this.montoDescuentos)+ "\n";
        salida = salida + "\tSueldo Bruto: " + String.valueOf(this.sueldoBruto)+ "\n";
        salida = salida + "\tCotizacion Previsional: " + String.valueOf(this.cotizacionPrevisional)+ "\n";
        salida = salida + "\tCotizacion salud: " + String.valueOf(this.cotizacionSalud)+ "\n";
        salida = salida + "\tSueldo Final: " + Integer.toString(this.sueldoFinal)+ "\n";
        System.out.println(salida);
    }
}
