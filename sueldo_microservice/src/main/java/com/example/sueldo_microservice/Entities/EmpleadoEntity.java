package com.example.sueldo_microservice.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(length = 12, unique = true, nullable = false)  // Considera rut con puntos y guión
    private String rut;
    private String apellidos;
    private String nombres;
    private String fecha_nac;
    private Integer categoria;
    private String fecha_in;

    public EmpleadoEntity(String rut, String apellidos, String nombres, String fecha_nac, Integer categoria, String fecha_in) {
        this.rut = rut;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.fecha_nac = fecha_nac;
        this.categoria = categoria;
        this.fecha_in = fecha_in;
    }
}
