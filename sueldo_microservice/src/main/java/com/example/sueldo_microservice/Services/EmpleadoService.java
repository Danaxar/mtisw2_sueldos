package com.example.sueldo_microservice.Services;

import com.example.sueldo_microservice.Entities.EmpleadoEntity;
import com.example.sueldo_microservice.Repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    public ArrayList<EmpleadoEntity> obtenerEmpleados(){
        return (ArrayList<EmpleadoEntity>) empleadoRepository.findAll();
    }

    public EmpleadoEntity obtenerPorRut(String rut){
        return empleadoRepository.findByRut(rut);
    }
}
