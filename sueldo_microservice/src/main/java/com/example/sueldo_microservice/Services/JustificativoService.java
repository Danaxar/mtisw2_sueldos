package com.example.sueldo_microservice.Services;

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
public class JustificativoService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CalculosService calculosService;

    public List<JustificativoModel> getAll(){
        String url = "http://justificativo-microservice/justificativo/listar";

        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class);
        Object[] records = response.getBody(); // Obtener el contenido
        if(records == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(records).
                map(justificativo -> mapper.convertValue(justificativo, JustificativoModel.class))
                .collect(Collectors.toList());
    }

    public List<JustificativoModel> getByRutAndFecha(String rut, String fecha){
        // System.out.println("Justificativo Service: Iniciando getByRutAndFecha...");
        // String url_base = "http://data-microservice/get-by-rut-and-fecha/";
        String url_base = "http://localhost:8084/data/get-by-rut-and-fecha/";
        String url_request = url_base + rut + "/" + calculosService.reformatFecha(fecha);
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url_request, Object[].class);
        Object[] records = response.getBody();
        if(records == null){
            // System.out.println("Justificativo Service: getByRutAndFecha: null");
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(records).
                map(data -> mapper.convertValue(data, JustificativoModel.class))
                .collect(Collectors.toList());
    }

    public Boolean existeJustificativo(String rut, String fecha){
        // System.out.println("Justificativo service: existeJustificativo...");
        List<JustificativoModel> obj = this.getByRutAndFecha(rut, fecha);
        if(obj == null){
            // System.out.println("Justificativo Service: existeJustificativo = null");
            return false;
        }
        if(obj.size() == 1){
            return true;
        }
        return false;   
    }
}
