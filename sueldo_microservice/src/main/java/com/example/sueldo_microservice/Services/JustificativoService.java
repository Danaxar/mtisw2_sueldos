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
}
