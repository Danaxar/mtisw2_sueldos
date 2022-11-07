package com.example.sueldo_microservice.Controllers;

import com.example.sueldo_microservice.Services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/sueldo")
public class SueldoController {
    @Autowired
    DataService dataService;

    @GetMapping("/test")
    public void test(){
        System.out.println("Iniciando test");
        //dataService.getByRutAndFecha("20.675.871-6", "07-11-2022");
        // System.out.println(Integer.toString(dataService.getAll().size()) + "!!!");

        // System.out.println(Integer.toString(dataService.getByRutAndFecha("20.675.871-7","2022/08/01").size()) + "!!!");
        System.out.println(Integer.toString(dataService.getByRut("20.675.871-7").size()) + "!!!");
    }

    @GetMapping("/clear")
    public void clearScreen() throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

}
