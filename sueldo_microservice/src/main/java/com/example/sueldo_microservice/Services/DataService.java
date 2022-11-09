package com.example.sueldo_microservice.Services;

import com.example.sueldo_microservice.Models.DataModel;
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

    @Autowired
    CalculosService calculosService;

    public List<DataModel> getAll(){
        System.out.println("Iniciando getAll");
        // String url = "http://localhost:8084/data/all/";
        String url = "http://data-microservice/data/all/";
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
    // Testeando...
    public List<DataModel> getByRutAndFecha(String rut, String fecha){
        // System.out.println("123123");
        // Solución: formatear la fecha a guiones
        // // System.out.println("Data Service: Iniciando getByRutAndFecha...");
        String url_base = "http://data-microservice/data/get-by-rut-and-fecha/";
        // String url_base = "http://localhost:8084/data/get-by-rut-and-fecha/";
        String url_request = url_base + rut + "/" + calculosService.reformatFecha(fecha);
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url_request, Object[].class);
        Object[] records = response.getBody();

        // System.out.println(records);
        
        if(records == null){
            // System.out.println("getByRutAndFecha: null");
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        List<DataModel> x = Arrays.stream(records)
                                  .map(data -> mapper.convertValue(data, DataModel.class))
                                .collect(Collectors.toList());
                                // System.out.println("Aqui.XD");
        return x;
    }
    // Funciona!!
    public List<DataModel> getByRut(String rut){
        // System.out.println("Iniciando getByRut...");
        String url_base = "http://data-microservice/data/get-by-rut/";
        // String url_base = "http://localhost:8084/data/get-by-rut/";
        String url_request = url_base + rut;
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url_request, Object[].class);
        Object[] records = response.getBody();
        System.out.println(records);
        if(records == null){
            // System.out.println("getByRut: null");
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(records).
                map(data -> mapper.convertValue(data, DataModel.class))
                .collect(Collectors.toList());
    }


    public boolean asistioEmpleadoDia(String rut, String fecha){
        // System.out.println("Data service: asistioEmpleadoDia...");
        List<DataModel> obj = this.getByRutAndFecha(rut, fecha);
        if(obj == null){
            //System.out.println("Data Service: asistioEmpleadoDia = null");
            return false;
        }
        if(obj.size() == 2){
            return true;
        }
        return false;
    }
}
