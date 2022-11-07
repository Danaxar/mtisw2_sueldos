package com.example.sueldo_microservice.Services;

import com.example.sueldo_microservice.Models.DataModel;
import com.example.sueldo_microservice.Models.JustificativoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataService {

    @Autowired
    RestTemplate restTemplate;

    public List<DataModel> getAll(){
        System.out.println("Iniciando getAll");
        String url = "http://localhost:8084/data/all";
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class);
        System.out.println("request terminado.");
        Object[] records = response.getBody();
        if(records == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Aqui.");
        return Arrays.stream(records)
                .map(data -> mapper.convertValue(data, DataModel.class))
                .collect(Collectors.toList());
    }

    public List<DataModel> getByRutAndFecha(String rut, String fecha){
        // Solución: formatear la fecha a guiones
        System.out.println("Iniciando getByRutAndFecha...");
        // String url_base = "http://data-microservice/get-by-rut-and-fecha/";
        String url_base = "http://localhost:8084/data/get-by-rut-and-fecha/";
        String url_request = url_base + rut + "/(" + fecha + ")";
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url_request, Object[].class);
        Object[] records = response.getBody();
        System.out.println(records);
        if(records == null){
            System.out.println("getByRutAndFecha: null");
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(records).
                map(data -> mapper.convertValue(data, DataModel.class))
                .collect(Collectors.toList());
    }
    // Funciona!!
    public List<DataModel> getByRut(String rut){
        System.out.println("Iniciando getByRut...");
        String url_base = "http://localhost:8084/data/get-by-rut/";
        String url_request = url_base + rut;
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url_request, Object[].class);
        Object[] records = response.getBody();
        System.out.println(records);
        if(records == null){
            System.out.println("getByRut: null");
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(records).
                map(data -> mapper.convertValue(data, DataModel.class))
                .collect(Collectors.toList());
    }


    public boolean asistioEmpleadoDia(String rut, String fecha){
        List<DataModel> obj = this.getByRutAndFecha(rut, fecha);
        if(obj.size() == 2){
            return true;
        }
        return false;
    }
}
