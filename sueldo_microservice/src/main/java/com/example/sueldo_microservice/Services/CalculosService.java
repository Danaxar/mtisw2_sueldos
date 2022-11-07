package com.example.sueldo_microservice.Services;

import org.springframework.stereotype.Service;

@Service
public class CalculosService {
    static Integer[] fecha_actual_int = {2022, 9, 1};  // 01/09/2022
    static String fecha_actual_str = "2022/09/01";

    static double cotizacionPrevisional = 0.1;
    static double cotizacionSalud = 0.08;

    static double descuentoXinasistencia = 0.15;

    static String horaEntrada = "08:00";
    static String horaSalida = "18:00";


    public static Integer[] fechaToArray(String fecha){
        // Manualmente por temas de tiempo
        String[] fechaSeparada = fecha.split("/");
        Integer[] salida = new Integer[3];
        salida[0] = Integer.valueOf(fechaSeparada[0]);
        salida[1] = Integer.valueOf(fechaSeparada[1]);
        salida[2] = Integer.valueOf(fechaSeparada[2]);
        return salida;
    }

    public static String fechaBarraLateralToGuion(String fecha){
        String[] aux = fecha.split("/");
        String salida = "";
        salida = salida + aux[0] + "-";
        salida = salida + aux[1] + "-";
        salida = salida + aux[2];
        return salida;
    }

    public static Integer[] horaToArray(String hora){
        // Manualmente por temas de tiempo
        String[] horaSeparada = hora.split(":");
        Integer[] salida = new Integer[2];
        salida[0] = Integer.valueOf(horaSeparada[0]);
        salida[1] = Integer.valueOf(horaSeparada[1]);
        return salida;
    }

    public static String ArrayToHora(Integer[] array){
        String salida = "";
        if(array[0] < 10){
            salida = salida + "0";
        }
        salida = salida + array[0].toString() + ":";
        if(array[1] < 10){
            salida = salida + "0";
        }
        salida = salida + array[1].toString();
        return salida;
    }

    public static String ArrayToFecha(Integer[] array){
        String salida = "";
        salida = salida + array[0].toString() + "/";
        if(array[1] < 10){
            salida = salida + "0";
        }
        salida = salida + array[1].toString() + "/";
        if(array[2] < 10){
            salida = salida + "0";
        }
        salida = salida + array[2].toString();
        return salida;
    }

    public static int calcularYearsOfService(String fecha_actual, String fecha_ini){
        // Retorna la cantidad de años que un trabajador lleva en la empresa
        Integer[] fecha_actual_transformada = CalculosService.fechaToArray(fecha_actual);
        Integer[] fecha_ini_transformada = CalculosService.fechaToArray(fecha_ini);
        return fecha_actual_transformada[0] - fecha_ini_transformada[0];
    }

    // Si resulta ser negativo entonces quiere decir que x es mayor que y
    /*
    public static Integer tiempoDiffToMinutos(String y, String x){
        // Hora2 < Hora1
        // y < x
        // Hora1 - Hora2
        Integer[] hora1 = CalculosService.horaToArray(x);
        Integer[] hora2 = CalculosService.horaToArray(y);
        if(hora1[1] < hora2[1]){
            hora1[1] = hora1[1] + 60;  // Sumo a los minutos
            hora1[0]--;  // Resto a las horas
        }
        int minutosDiff = hora1[1] - hora2[1];
        int horasDiff = hora1[0] - hora2[0];
        int minutosTotales = horasDiff * 60 + minutosDiff;
        return minutosTotales;
    }*/

    public static Integer tiempoDiffToMinutos(String x, String y){
        // x - y
        //System.out.println("\ttiempoDiffToMinutos\n\t"+x+"\n\t"+y);
        Integer[] x_int = CalculosService.horaToArray(x);
        Integer[] y_int = CalculosService.horaToArray(y);
        int x_minutos = x_int[0] * 60 + x_int[1];
        //System.out.println("\t\t\t" + Integer.toString(x_minutos));
        int y_minutos = y_int[0] * 60 + y_int[1];
        //System.out.println("\t\t\t" + Integer.toString(y_minutos));
        return x_minutos - y_minutos;
    }



    public static Integer[] copiarArray(Integer[] x){
        Integer[] salida = new Integer[x.length];
        for(int i = 0; i < x.length; i++){
            salida[i] = x[i];
        }
        return salida;
    }
}
