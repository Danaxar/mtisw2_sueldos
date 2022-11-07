package com.example.sueldo_microservice.Services;

import com.example.sueldo_microservice.Entities.EmpleadoEntity;
import com.example.sueldo_microservice.Repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    @Autowired
    EmpleadoRepository empleadoRepository;
    private boolean empleadosAgregados = false;
    boolean sueldosCalculados = false;

    public void agregarEmpleadosPorDefecto() {
        if (!this.empleadosAgregados) {
            empleadoRepository.save(new EmpleadoEntity("20.675.871-6", "CATALAN VALENZUELA",
                    "DANIEL ESTEBAN", "2001/01/25", 1, "2010/04/14"));
            empleadoRepository.save(new EmpleadoEntity("9.678.460-0", "VALENZUELA MUÑOZ",
                    "PAULA ELENA", "1969/02/12", 1, "2014/09/23"));
            this.empleadosAgregados = true;
            System.out.println("Empleados agregados.");
        }else {System.out.println("Ya existen empleados registrados.");}
    }
}
