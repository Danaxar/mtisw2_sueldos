package com.example.sueldo_microservice.Entities;

public class CategoriaEntity {
    public static Integer categoria_a = 1700000;
    public static Integer categoria_b = 1200000;
    public static Integer categoria_c = 800000;

    public static double categoria_a_extra = 25000.0;
    public static double categoria_b_extra = 20000.0;
    public static double categoria_c_extra = 10000.0;

    public static Integer sueldoXcategoria(Integer categoria){
        switch (categoria){
            case 1:{
                return CategoriaEntity.categoria_a;}
            case 2:{
                return CategoriaEntity.categoria_b;}
            case 3:{
                return CategoriaEntity.categoria_c;}
        }

        return -1;
    }

    public static double horaExtraXcategoria(Integer categoria){
        switch (categoria){
            case 1:{
                return CategoriaEntity.categoria_a_extra;}
            case 2:{
                return CategoriaEntity.categoria_b_extra;}
            case 3:{
                return CategoriaEntity.categoria_c_extra;}
        }

        return -1;
    }
}
