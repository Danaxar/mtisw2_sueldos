package com.example.sueldo_microservice.Controllers;

import com.example.sueldo_microservice.Services.AutorizacionService;
import com.example.sueldo_microservice.Services.DataService;
import com.example.sueldo_microservice.Services.SueldoService;
import com.example.sueldo_microservice.Entities.Sueldo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/sueldo")
public class SueldoController {
    @Autowired
    DataService dataService;

    @Autowired
    SueldoService sueldoService;

    @Autowired
    AutorizacionService autorizacionService;

    @GetMapping("/test")
    public void test(){
        System.out.println("Iniciando test");
        //dataService.getByRutAndFecha("20.675.871-6", "07-11-2022");
        //System.out.println(Integer.toString(dataService.getAll().size()) + "!!!");
        //System.out.println(Integer.toString(dataService.getByRutAndFecha("20.675.871-7","2022/08/01").size()) + "!!!");
        //System.out.println(Integer.toString(dataService.getByRut("20.675.871-7").size()) + "!!!");
        //autorizacionService.existeAutorizacion("20.675.871-6", "08/11/2022");
        //dataService.getByRutAndFecha("20.675.871-6", "08/11/2022");
    }

    @GetMapping("/clear")
    public void clearScreen() throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    @GetMapping("/calcular")
    public void calcular(){
        System.out.println("Sueldo controller: calculando sueldos...");
        sueldoService.calcularSueldos();
    }

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Sueldo>> getAll(){
        System.out.println("Sueldo controller: obteniendo sueldos...");
        ArrayList<Sueldo> sueldos = sueldoService.listarTodos();
        if(sueldos.isEmpty()){
            System.out.println("Sueldo controller: No hay sueldos calculados");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sueldos);
    }
}
