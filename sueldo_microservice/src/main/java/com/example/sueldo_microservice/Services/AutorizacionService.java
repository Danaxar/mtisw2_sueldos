package com.example.sueldo_microservice.Services;

import com.example.sueldo_microservice.Models.AutorizacionModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorizacionService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CalculosService calculosService;

    public List<AutorizacionModel> getAll(){
        String url = "http://autorizacion-microservice/autorizacion/listar";

        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class);
        Object[] records = response.getBody(); // Obtener el contenido
        if(records == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(records).
                map(autorizacion -> mapper.convertValue(autorizacion, AutorizacionModel.class))
                .collect(Collectors.toList());
    }

    public Boolean existeAutorizacion(String rut, String fecha){
        String url = "http://localhost:8082/get-by-rut-and-fecha/";
        url += rut + "/" + calculosService.reformatFecha(fecha);
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        Object record = response.getBody();
        if(record == null){
            return false;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(record, Boolean.class);    
    }
}
