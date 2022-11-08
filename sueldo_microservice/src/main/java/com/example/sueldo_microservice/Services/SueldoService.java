
package com.example.sueldo_microservice.Services;

import com.example.sueldo_microservice.Entities.CategoriaEntity;
import com.example.sueldo_microservice.Entities.EmpleadoEntity;
import com.example.sueldo_microservice.Entities.Sueldo;
import com.example.sueldo_microservice.Models.DataModel;
import com.example.sueldo_microservice.Repositories.SueldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SueldoService {
    @Autowired
    SueldoRepository sueldoRepository;

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    DataService dataService;
    @Autowired
    JustificativoService justificativoService;
    
    @Autowired
    AutorizacionService autorizacionService;


    @Autowired
    MainService mainService;

    // Métodos
    public ArrayList<Sueldo> listarTodos() {  // Cuando los sueldos estén calculados se muestran con esto
        return (ArrayList<Sueldo>) sueldoRepository.findAll();
    }

    public Sueldo crearSueldo(EmpleadoEntity empleadoActual) {
        Sueldo sueldo = new Sueldo();
        sueldo.setRut(empleadoActual.getRut());
        sueldo.setNombreEmpleado(empleadoActual.getApellidos() + " " + empleadoActual.getNombres());
        sueldo.setYearsOfService(CalculosService.calcularYearsOfService(
                CalculosService.fecha_actual_str,
                empleadoActual.getFecha_in()));
        sueldo.setSueldoFijoMensual(CategoriaEntity.sueldoXcategoria(empleadoActual.getCategoria()));
        sueldo.setBonoYearsOfService(
                this.calcularBonoYearsOfService(empleadoActual)
                        * sueldo.getSueldoFijoMensual()
        );
        sueldo.setPagoHorasExtras(0.0);
        sueldo.setMontoDescuentos(0.0);
        sueldo.setSueldoBruto(0.0);
        sueldo.setCotizacionPrevisional(0.0);
        sueldo.setCotizacionSalud(0.0);
        return sueldo;
    }

    public double calcularBonoYearsOfService(EmpleadoEntity empleado) {
        int years = CalculosService.calcularYearsOfService(CalculosService.fecha_actual_str, empleado.getFecha_in());
        if (years < 5) {
            return 0.0;
        } else if (years >= 5 && years < 10) {
            return 0.05;
        } else if (years >= 10 && years < 15) {
            return 0.08;
        } else if (years >= 15 && years < 20) {
            return 0.11;
        } else if (years >= 20 && years < 25) {
            return 0.14;
        } else if (years >= 25) {
            return 0.17;
        }
        System.out.println("Error calculando years of service");
        return -1;
    }

    public void calcularSueldos() {
        // Se debería borrar las tuplas existentes de sueldos
        borrarTodosSueldos();
        mainService.agregarEmpleadosPorDefecto();
        // System.out.println("Sueldo service: Calculando sueldos...");
        ArrayList<EmpleadoEntity> empleados = empleadoService.obtenerEmpleados();
        for(int i = 0; i < empleados.size(); i++) {  // Recorrer los empleados
            EmpleadoEntity empleadoActual = empleados.get(i);
            Sueldo sueldo = crearSueldo(empleadoActual);
            Integer[] fechaDeTrabajo = CalculosService.copiarArray(CalculosService.fecha_actual_int);
            fechaDeTrabajo[1]--;  // Retroceder un mes = {2022, 8, 1};
            for (int j = 0; j < 30; j++) {  // Recorrer días del mes: Descuentos y Horas Extras
                String fechaActual = CalculosService.ArrayToFecha(fechaDeTrabajo);
                if (!dataService.asistioEmpleadoDia(empleadoActual.getRut(), fechaActual)) {  // Fue a trabajar?
                    // Tiene justificativo?
                    if (!justificativoService.existeJustificativo(empleadoActual.getRut(), fechaActual)){
                        agregarDescuento(sueldo, CalculosService.descuentoXinasistencia);  // -15%
                    }
                } else { // Asistió a trabajar
                    procesarHoraLlegada(empleadoActual, fechaActual, CalculosService.horaEntrada, sueldo);
                    procesarHoraSalida(empleadoActual, fechaActual, CalculosService.horaSalida, sueldo);
                }
                fechaDeTrabajo[2]++;  // Ir al día siguiente
            }
            sueldoBruto(sueldo);
            sueldoFinal(sueldo);
            sueldoRepository.save(sueldo);
            System.out.println("Sueldo de " + 
                empleadoActual.getNombres().split(" ")[0] + " " +
                empleadoActual.getApellidos().split(" ")[0] + "calculado exitosamente.");
        }
        System.out.println("Sueldos calculados exitosamente.");
    }

    public void sueldoBruto(Sueldo sueldo) {
        // Sueldo base - descuentos por atraso/falta + bonoYearsOfService + horas extras
        Integer sueldoBase = sueldo.getSueldoFijoMensual();
        double descuentos = sueldo.getMontoDescuentos();
        double bonoYearsOfService = sueldo.getBonoYearsOfService();
        double montoHorasExtras = sueldo.getPagoHorasExtras();
        double sueldoBruto = sueldoBase + bonoYearsOfService - descuentos + montoHorasExtras;
        sueldo.setSueldoBruto(sueldoBruto);
    }

    public void sueldoFinal(Sueldo sueldo) {
        // Sueldo bruto - cotizaciones
        double sueldoBruto = sueldo.getSueldoBruto();
        double cotizacionPrevisional = sueldoBruto * CalculosService.cotizacionPrevisional;
        sueldo.setCotizacionPrevisional(cotizacionPrevisional);

        double cotizacionSalud = sueldoBruto * CalculosService.cotizacionSalud;
        sueldo.setCotizacionSalud(cotizacionSalud);

        Double sueldoFinal = sueldoBruto - (cotizacionPrevisional + cotizacionSalud);
        if(sueldoFinal < 0.0){
            sueldo.setSueldoFinal(0);
        }else{
            sueldo.setSueldoFinal(sueldoFinal.intValue());
        }
    }

    public void procesarHoraLlegada(EmpleadoEntity empleado, String fecha, String hora_programada, Sueldo sueldo) {
        // System.out.println("Sueldo service: procesarHoraLlegada");
        // Aplica un descuento a un empleado a un día específico según una hora programada al objeto sueldo
        String rutConsultar = empleado.getRut();
        List<DataModel> obj = (List<DataModel>) dataService.getByRutAndFecha(rutConsultar, fecha);
        DataModel registro = obj.get(0); // Obtener el primer ingreso, el de la mañana
        int minutosAtraso = CalculosService.tiempoDiffToMinutos(registro.getHora(), hora_programada);
        if(minutosAtraso > 10 && minutosAtraso < 25){
            agregarDescuento(sueldo, 0.01);
        }else if(minutosAtraso >= 25 && minutosAtraso < 45){
            agregarDescuento(sueldo, 0.03);
        }else if(minutosAtraso >= 45 && minutosAtraso < 70){
            agregarDescuento(sueldo, 0.06);
        }else if(minutosAtraso >= 70){ // Se considera inasistencia
            if (!justificativoService.existeJustificativo(fecha, empleado.getRut())) {
                agregarDescuento(sueldo, CalculosService.descuentoXinasistencia);
            }
        }
    }

    public void procesarHoraSalida(EmpleadoEntity empleado, String fecha, String hora_programada, Sueldo sueldo){
        // System.out.println("Sueldo service: procesarHoraSalida");
        // Aplica un descuento a un empleado a un día específico según una hora programada al objeto sueldo
        String rutConsultar = empleado.getRut();
        List<DataModel> obj = (List<DataModel>) dataService.getByRutAndFecha(rutConsultar, fecha);
        DataModel registro = obj.get(1); // Obtener el segundo ingreso, el de la tarde
        int minutosExtras = CalculosService.tiempoDiffToMinutos(registro.getHora(), hora_programada);
        if(minutosExtras >= 60){
            if(autorizacionService.existeAutorizacion(fecha, empleado.getRut())){ // Agregar pago
                double montoHorasExtrasActual = sueldo.getPagoHorasExtras();  // monto acumulado
                double pago = CategoriaEntity.horaExtraXcategoria(empleado.getCategoria()) * (minutosExtras / 60);
                sueldo.setPagoHorasExtras(montoHorasExtrasActual + pago);
            }
        }
    }

    public void agregarDescuento(Sueldo sueldo, double porcentaje) {
        double montoDescuentoActual = sueldo.getMontoDescuentos();  // Obtener la cantidad actual
        double descuento = porcentaje * sueldo.getSueldoFijoMensual();
        sueldo.setMontoDescuentos(montoDescuentoActual + descuento);
    }

    public void borrarTodosSueldos(){
        System.out.println("Borrando registros de tabla sueldos...");
        sueldoRepository.deleteAll();
    }
}
